package com.hedera.mirror.importer.downloader.client;

import com.hedera.mirror.importer.domain.StreamFilename;
import com.hedera.mirror.importer.downloader.DownloaderProperties;
import com.hedera.mirror.importer.downloader.PendingDownload;

import com.hedera.mirror.importer.exception.InvalidStreamFileException;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.RequestPayer;
import software.amazon.awssdk.services.s3.model.S3Object;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.hedera.mirror.importer.domain.StreamFilename.FileType.SIGNATURE;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

public class S3FileClient extends FileClientWithProperties {
    @RequiredArgsConstructor
    public static class Builder implements FileClientWithProperties.Builder {
        private final S3AsyncClient s3Client;

        @Override
        public FileClientWithProperties buildFor(DownloaderProperties downloaderProperties) {
            return new S3FileClient(downloaderProperties, s3Client);
        }
    }

    protected final Logger log = LogManager.getLogger(getClass());
    private final S3AsyncClient s3Client;

    protected S3FileClient(DownloaderProperties downloaderProperties, S3AsyncClient s3Client) {
        super(downloaderProperties);
        this.s3Client = s3Client;
    }

    @Override
    public List<PendingDownload> download(String nodeAccountId, List<String> filePaths) {
        // group the signature filenames by its instant
        Map<Instant, Optional<StreamFilename>> signatureFilenamesByInstant = filePaths.stream()
                .map(filePath -> filePath.substring(filePath.lastIndexOf('/') + 1))
                .map(filename -> {
                    try {
                        return new StreamFilename(filename);
                    } catch (InvalidStreamFileException e) {
                        log.error(e);
                        return null;
                    }
                })
                .filter(s -> s != null && s.getFileType() == SIGNATURE)
                .collect(groupingBy(StreamFilename::getInstant, maxBy(StreamFilename.EXTENSION_COMPARATOR)));

        return signatureFilenamesByInstant.values()
                .stream()
                .filter(Optional::isPresent)
                .map(s -> download(nodeAccountId, s.get()))
                .collect(Collectors.toList());
    }

    @Override
    public PendingDownload download(String nodeAccountId, StreamFilename streamFile) {
        String s3Key = pathPrefixFor(nodeAccountId) + streamFile.getFilename();
        var request = GetObjectRequest.builder()
                .bucket(rootPath())
                .key(s3Key)
                .requestPayer(RequestPayer.REQUESTER)
                .build();
        var future = s3Client.getObject(request, AsyncResponseTransformer.toBytes());
        return new PendingDownload(future, streamFile, s3Key);
    }

    @Override
    protected List<String> list(String nodeAccountId, String lastFilename, int maxCount) throws ExecutionException, InterruptedException {
        String s3Prefix = pathPrefixFor(nodeAccountId);
        // Not using ListObjectsV2Request because it does not work with GCP.
        ListObjectsRequest listRequest = ListObjectsRequest.builder()
                .bucket(rootPath())
                .prefix(s3Prefix)
                .delimiter("/")
                .marker(s3Prefix + lastFilename)
                .maxKeys(maxCount)
                .requestPayer(RequestPayer.REQUESTER)
                .build();

        return s3Client.listObjects(listRequest).get().contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }
}
