package com.example.demo.configurations;

import ru.yoomoney.tech.dbqueue.settings.ExtSettings;
import ru.yoomoney.tech.dbqueue.settings.FailRetryType;
import ru.yoomoney.tech.dbqueue.settings.FailureSettings;
import ru.yoomoney.tech.dbqueue.settings.PollSettings;
import ru.yoomoney.tech.dbqueue.settings.ProcessingMode;
import ru.yoomoney.tech.dbqueue.settings.ProcessingSettings;
import ru.yoomoney.tech.dbqueue.settings.QueueConfig;
import ru.yoomoney.tech.dbqueue.settings.QueueId;
import ru.yoomoney.tech.dbqueue.settings.QueueLocation;
import ru.yoomoney.tech.dbqueue.settings.QueueSettings;
import ru.yoomoney.tech.dbqueue.settings.ReenqueueRetryType;
import ru.yoomoney.tech.dbqueue.settings.ReenqueueSettings;

import java.time.Duration;
import java.util.LinkedHashMap;

//@Configuration
public class DbQueueConfig {

    public QueueConfig queueConfig() {
        QueueId queueId = new QueueId("QueueId");
        QueueLocation queueLocation = QueueLocation.builder()
            .withQueueId(queueId)
            .withTableName("queue_tasks")
            .build();
        QueueSettings queueSettings = QueueSettings.builder()
            .withProcessingSettings(ProcessingSettings.builder()
                .withProcessingMode(ProcessingMode.SEPARATE_TRANSACTIONS)
                .withThreadCount(1).build())
            .withPollSettings(PollSettings.builder()
                .withBetweenTaskTimeout(Duration.ofMillis(100))
                .withNoTaskTimeout(Duration.ofMillis(100))
                .withFatalCrashTimeout(Duration.ofSeconds(1)).build())
            .withFailureSettings(FailureSettings.builder()
                .withRetryType(FailRetryType.GEOMETRIC_BACKOFF)
                .withRetryInterval(Duration.ofMinutes(1)).build())
            .withReenqueueSettings(ReenqueueSettings.builder()
                .withRetryType(ReenqueueRetryType.MANUAL).build())
            .withExtSettings(ExtSettings.builder().withSettings(new LinkedHashMap<>()).build())
            .build();
        return new QueueConfig(queueLocation, queueSettings);
    }
}
