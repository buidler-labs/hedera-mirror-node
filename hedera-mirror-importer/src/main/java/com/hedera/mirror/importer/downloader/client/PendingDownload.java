package com.hedera.mirror.importer.downloader.client;

/*-
 * ‌
 * Hedera Mirror Node
 * ​
 * Copyright (C) 2019 - 2021 Hedera Hashgraph, LLC
 * ​
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ‍
 */

import com.google.common.base.Stopwatch;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.log4j.Log4j2;

import com.hedera.mirror.importer.domain.StreamFilename;

/**
 * The results of a pending download from the AWS TransferManager. Call waitForCompletion() to wait for the transfer to
 * complete and get the status of whether it was successful or not.
 */
@Log4j2
@NonFinal
@Value
public abstract class PendingDownload<I> {
    public static class SimpleResultForwarder extends PendingDownload<DownloadResult> {
        public SimpleResultForwarder(CompletableFuture<DownloadResult> future, StreamFilename localFilename, String remotePath) {
            super(future, localFilename, remotePath);
        }

        @Override
        protected DownloadResult mapResult(DownloadResult resolvedFuture) {
            return resolvedFuture;
        }
    }

    protected CompletableFuture<I> future;
    StreamFilename localFilename;
    String remotePath;
    Stopwatch stopwatch = Stopwatch.createStarted();

    @NonFinal
    boolean alreadyWaited = false; // has waitForCompletion been called

    @NonFinal
    boolean downloadSuccessful = false;

    protected PendingDownload(CompletableFuture<I> future, StreamFilename localFilename, String remotePath) {
        this.future = future;
        this.localFilename = localFilename;
        this.remotePath = remotePath;
    }

    public DownloadResult getResult() throws ExecutionException, InterruptedException {
        return mapResult(this.future.get());
    }
    protected abstract DownloadResult mapResult(I resolvedFuture);

    /**
     * @return true if the download was successful.
     */
    public boolean waitForCompletion() throws InterruptedException {
        if (alreadyWaited) {
            return downloadSuccessful;
        }
        alreadyWaited = true;
        try {
            future.get();
            log.debug("Finished downloading {} in {}", remotePath, stopwatch);
            downloadSuccessful = true;
        } catch (InterruptedException e) {
            log.warn("Failed downloading {} after {}", remotePath, stopwatch, e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException ex) {
            log.warn("Failed downloading {} after {}: {}", remotePath, stopwatch, ex.getMessage());
        } catch (Exception ex) {
            log.warn("Failed downloading {} after {}", remotePath, stopwatch, ex);
        }
        return downloadSuccessful;
    }
}
