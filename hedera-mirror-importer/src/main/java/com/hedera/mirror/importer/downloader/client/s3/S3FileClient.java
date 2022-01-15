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

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.hedera.mirror.importer.downloader.client.ParameterizedFileClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.RequestPayer;
import software.amazon.awssdk.services.s3.model.S3Object;

import com.hedera.mirror.importer.domain.StreamFilename;
import com.hedera.mirror.importer.downloader.DownloaderProperties;
import com.hedera.mirror.importer.downloader.client.PendingDownload;

@Log4j2
public class S3FileClient extends ParameterizedFileClient {
    @RequiredArgsConstructor
    public static class Builder implements ParameterizedFileClient.Builder {
        private final S3AsyncClient s3Client;

        @Override
        public ParameterizedFileClient buildFor(DownloaderProperties downloaderProperties) {
            return new S3FileClient(downloaderProperties, s3Client);
        }
    }

    private final S3AsyncClient s3Client;

    protected S3FileClient(DownloaderProperties downloaderProperties, S3AsyncClient s3Client) {
        super(downloaderProperties);
        this.s3Client = s3Client;
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
        return new S3PendingDownload(future, streamFile, s3Key);
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
