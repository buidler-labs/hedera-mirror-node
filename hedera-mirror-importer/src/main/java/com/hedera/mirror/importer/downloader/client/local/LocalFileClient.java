package com.hedera.mirror.importer.downloader.client.local;

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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.hedera.mirror.importer.downloader.client.FileClientWithProperties;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import com.hedera.mirror.importer.domain.StreamFilename;
import com.hedera.mirror.importer.downloader.client.DownloadResult;
import com.hedera.mirror.importer.downloader.DownloaderProperties;
import com.hedera.mirror.importer.downloader.client.PendingDownload;

@Log4j2
public class LocalFileClient extends FileClientWithProperties {
    @RequiredArgsConstructor
    public static class Builder implements FileClientWithProperties.Builder {
        @Override
        public FileClientWithProperties buildFor(DownloaderProperties downloaderProperties) {
            return new LocalFileClient(downloaderProperties);
        }
    }

    protected LocalFileClient(DownloaderProperties downloaderProperties) {
        super(downloaderProperties);
    }

    @SneakyThrows
    @Override
    public PendingDownload download(String nodeAccountId, StreamFilename streamFile) {
        Path filePath = Paths.get(rootPath(), pathPrefixFor(nodeAccountId), streamFile.getFilename());
        CompletableFuture futureLocalDownload = CompletableFuture.supplyAsync(() -> new DownloadResult() {
            @SneakyThrows
            @Override
            public byte[] getBytes() {
                return Files.readAllBytes(filePath);
            }

            @SneakyThrows
            @Override
            public Instant getLastModified() {
                BasicFileAttributes fileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);
                return fileAttributes.lastModifiedTime().toInstant();
            }
        });

        return new PendingDownload.SimpleResultForwarder(futureLocalDownload, streamFile, filePath.toString());
    }

    @Override
    protected List<String> list(String nodeAccountId, String lastFilename, int maxCount)
            throws ExecutionException, InterruptedException {
        File localBucketDir = new File(rootPath() + "/" + pathPrefixFor(nodeAccountId));
        String[] fileNames = localBucketDir.list((dir, name) -> lastFilename.compareTo(name) < 0);

        return fileNames == null ? Collections.emptyList() :
                Arrays.stream(fileNames)
                        .limit(maxCount)
                        .collect(Collectors.toList());
    }
}
