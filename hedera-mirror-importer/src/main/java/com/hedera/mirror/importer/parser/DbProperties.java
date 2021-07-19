package com.hedera.mirror.importer.parser;

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

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class DbProperties {

    @DurationMin(seconds = 10)
    @DurationUnit(ChronoUnit.SECONDS)
    @NotNull
    private Duration connectionNetworkTimeout = Duration.ofSeconds(10);

    @DurationMin(seconds = 30)
    @DurationUnit(ChronoUnit.SECONDS)
    @NotNull
    private Duration transactionTimeout = Duration.ofSeconds(30);
}