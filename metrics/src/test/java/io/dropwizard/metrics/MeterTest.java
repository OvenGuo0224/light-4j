/*
 * Copyright 2010-2013 Coda Hale and Yammer, Inc., 2014-2017 Dropwizard Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.dropwizard.metrics;

import org.junit.Before;
import org.junit.Test;

import io.dropwizard.metrics.Clock;
import io.dropwizard.metrics.Meter;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MeterTest {
    private final Clock clock = mock(Clock.class);
    private final Meter meter = new Meter(clock);

    @Before
    public void setUp() throws Exception {
        when(clock.getTick()).thenReturn(0L, TimeUnit.SECONDS.toNanos(10));

    }

    @Test
    public void startsOutWithNoRatesOrCount() throws Exception {
        assertThat(meter.getCount())
                .isZero();

        assertThat(meter.getMeanRate())
                .isEqualTo(0.0, offset(0.001));

        assertThat(meter.getOneMinuteRate())
                .isEqualTo(0.0, offset(0.001));

        assertThat(meter.getFiveMinuteRate())
                .isEqualTo(0.0, offset(0.001));

        assertThat(meter.getFifteenMinuteRate())
                .isEqualTo(0.0, offset(0.001));
    }

    @Test
    public void marksEventsAndUpdatesRatesAndCount() throws Exception {
        meter.mark();
        meter.mark(2);

        assertThat(meter.getMeanRate())
                .isEqualTo(0.3, offset(0.001));

        assertThat(meter.getOneMinuteRate())
                .isEqualTo(0.1840, offset(0.001));

        assertThat(meter.getFiveMinuteRate())
                .isEqualTo(0.1966, offset(0.001));

        assertThat(meter.getFifteenMinuteRate())
                .isEqualTo(0.1988, offset(0.001));
    }
}
