package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * This Class represent CONSUMER
 */
public class LogService {

    public static void main(String[] args) throws InterruptedException {
        var logService = new LogService();
        try(var consumer = new KafkaService(LogService.class.getSimpleName(),
                Pattern.compile("ECOMMERCE.*"),
                logService::parse)){
            consumer.run();
        }
    }

    private void parse(ConsumerRecord<String, String> record) throws InterruptedException {
        System.out.println("===========LOG=================");
        System.out.println("TOPIC: " + record.topic());
        System.out.println("OFFISET: " + record.offset());
        System.out.println("PARTITION: " +record.partition());
        System.out.println("TIME: " + Instant.ofEpochMilli(record.timestamp()));
        System.out.println("KEY: " + record.key());
        System.out.println("VALUE: " + record.value());
        System.out.println("=================================================");

        System.out.println("Log Processed");
    }

}
