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

import com.hedera.mirror.importer.domain.StreamFilename;

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

    List<String> list(String nodeAccountId, String lastFileName) throws ExecutionException, InterruptedException;
}
