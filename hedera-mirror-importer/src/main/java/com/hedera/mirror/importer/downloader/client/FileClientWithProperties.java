package com.hedera.mirror.importer.downloader.client;

import com.hedera.mirror.importer.downloader.DownloaderProperties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class FileClientWithProperties implements FileClient {
    public interface Builder extends FileClient.Builder<DownloaderProperties, FileClientWithProperties> { }

    private final DownloaderProperties downloaderProperties;

    @Override
    public List<String> list(String nodeAccountId, String lastFilename) throws ExecutionException,
            InterruptedException {
        // batchSize (number of items we plan do download in a single batch) times 2 for file + sig.
        int listSize = (downloaderProperties.getBatchSize() * 2);

        return list(nodeAccountId, lastFilename, listSize);
    }

    protected abstract List<String> list(String nodeAccountId, String lastFilename, int maxCount)
            throws ExecutionException, InterruptedException;

    protected String pathPrefixFor(String nodeAccountId) {
        return downloaderProperties.getPrefix() + nodeAccountId + "/";
    }

    protected String rootPath() {
        return downloaderProperties.getCommon().getBucketName();
    }
}
