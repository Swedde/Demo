package com.example.demo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class EventKafkaProducer {

    private final Producer<String, String> producer;
    private final String topic = "topic";

    public EventKafkaProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092"); // Your Kafka broker
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
    }

    public void sendEvent(String type) {
        producer.send(new ProducerRecord<>(topic, type));
    }

}
