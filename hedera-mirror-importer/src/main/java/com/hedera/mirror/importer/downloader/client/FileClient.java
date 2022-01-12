package com.hedera.mirror.importer.downloader.client;

import com.hedera.mirror.importer.domain.StreamFilename;
import com.hedera.mirror.importer.downloader.PendingDownload;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FileClient {
    interface Builder<I, O extends FileClient> {
        O buildFor(I dependency);
    }

    /**
     * Returns a PendingDownload for which the caller can waitForCompletion() to wait for the download to complete. This
     * either queues or begins the download (depending on the AWS TransferManager).
     */
    PendingDownload download(String nodeAccountId, StreamFilename streamFile);
    List<PendingDownload> download(String nodeAccountId, List<String> filePaths);

    List<String> list(String nodeAccountId, String lastFileName) throws ExecutionException, InterruptedException;
}
