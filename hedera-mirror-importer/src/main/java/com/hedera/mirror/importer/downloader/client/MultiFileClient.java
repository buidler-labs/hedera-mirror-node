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

import static com.hedera.mirror.importer.domain.StreamFilename.FileType.SIGNATURE;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;

import com.hedera.mirror.importer.domain.StreamFilename;
import com.hedera.mirror.importer.exception.InvalidStreamFileException;

@Log4j2
public abstract class MultiFileClient implements FileClient {
    private static final Predicate<StreamFilename> SIGNATURE_FILES_FILTER = s -> s != null && s.getFileType() == SIGNATURE;

    private List<PendingDownload> download(String nodeAccountId, List<String> filePaths, Predicate<? super StreamFilename> filter) {
        if (filePaths.isEmpty()) {
            return Collections.emptyList();
        }

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
                .filter(filter)
                .collect(groupingBy(StreamFilename::getInstant, maxBy(StreamFilename.EXTENSION_COMPARATOR)));

        return signatureFilenamesByInstant.values()
                .stream()
                .filter(Optional::isPresent)
                .map(s -> download(nodeAccountId, s.get()))
                .collect(Collectors.toList());
    }

    public List<PendingDownload> downloadSignatureFiles(String nodeAccountId, List<String> filePaths) {
        return download(nodeAccountId, filePaths, SIGNATURE_FILES_FILTER);
    }
}
