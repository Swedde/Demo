package com.example.demo.kafka;

import com.example.demo.repositories.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

//@Component
@Slf4j
public class EventKafkaConsumer {
    private final Consumer<String, String> consumer;
    private final EventRepository eventRepository;
    private final String topic = "topic";

    public EventKafkaConsumer(EventRepository eventRepository) {
        this.eventRepository = eventRepository;

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "my-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
        new Thread(this::process).start();
    }

    public void process() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            log.info("Records size: {}", records.count());
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("Consumed record from topic %s, partition %d, offset %d: key=%s, value=%s%n",
                    record.topic(), record.partition(), record.offset(), record.key(), record.value());
                if (record.value() == null) {
                    log.error("Null value");
                    continue;
                }
                eventRepository.upsert(record.value());
            }
            consumer.commitSync();
        }
    }
}
