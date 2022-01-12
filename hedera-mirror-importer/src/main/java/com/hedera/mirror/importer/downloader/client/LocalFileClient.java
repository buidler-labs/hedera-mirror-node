package com.hedera.mirror.importer.downloader.client;

import com.hedera.mirror.importer.domain.StreamFilename;
import com.hedera.mirror.importer.downloader.DownloaderProperties;
import com.hedera.mirror.importer.downloader.PendingDownload;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class LocalFileClient extends FileClientWithProperties {
    @RequiredArgsConstructor
    public static class Builder implements FileClientWithProperties.Builder {
        @Override
        public FileClientWithProperties buildFor(DownloaderProperties downloaderProperties) {
            return new LocalFileClient(downloaderProperties);
        }
    }

    protected final Logger log = LogManager.getLogger(getClass());

    protected LocalFileClient(DownloaderProperties downloaderProperties) {
        super(downloaderProperties);
    }

    @Override
    public PendingDownload download(String nodeAccountId, StreamFilename streamFile) {
        return null;
    }

    @Override
    public List<PendingDownload> download(String nodeAccountId, List<String> filePaths) {
        return new ArrayList<>();
    }

    @Override
    protected List<String> list(String nodeAccountId, String lastFilename, int maxCount)
            throws ExecutionException, InterruptedException {
        File localBucketDir = new File(rootPath() + "/" + pathPrefixFor(nodeAccountId));
        String[] fileNames = localBucketDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return lastFilename.compareTo(name) > 0;
            }
        });

        return fileNames == null ? Collections.emptyList() :
                Arrays.stream(fileNames)
                        .limit(maxCount)
                        .collect(Collectors.toList());
    }
}
