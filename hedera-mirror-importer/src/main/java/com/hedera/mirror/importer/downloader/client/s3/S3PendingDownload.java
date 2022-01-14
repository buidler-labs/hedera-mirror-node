package com.hedera.mirror.importer.downloader.client.s3;

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

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.hedera.mirror.importer.downloader.client.PendingDownload;
import com.hedera.mirror.importer.downloader.client.DownloadResult;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import com.hedera.mirror.importer.domain.StreamFilename;

@Log4j2
public class S3PendingDownload extends PendingDownload<ResponseBytes<GetObjectResponse>> {
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class S3DownloadResult implements DownloadResult {
        private final ResponseBytes<GetObjectResponse> result;

        @Override
        public byte[] getBytes() throws ExecutionException, InterruptedException {
            return result.asByteArrayUnsafe();
        }

        @Override
        public Instant getLastModified() {
            return result.response().lastModified();
        }
    }


    public S3PendingDownload(CompletableFuture<ResponseBytes<GetObjectResponse>> future, StreamFilename streamFile, String s3key) {
        super(future, streamFile, s3key);
    }

    @Override
    protected DownloadResult mapResult(ResponseBytes<GetObjectResponse> resolvedFuture) {
        return new S3DownloadResult(resolvedFuture);
    }
}
