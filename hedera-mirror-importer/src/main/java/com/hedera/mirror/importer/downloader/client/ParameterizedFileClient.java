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

import java.util.List;
import java.util.concurrent.ExecutionException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import com.hedera.mirror.importer.downloader.DownloaderProperties;

@Getter
@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ParameterizedFileClient extends MultiFileClient {
    public interface Builder extends FileClient.Builder<DownloaderProperties, ParameterizedFileClient> { }

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
