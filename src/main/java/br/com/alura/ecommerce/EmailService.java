package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * This Class represent CONSUMER
 */
public class EmailService {

    public static void main(String[] args) throws InterruptedException {
        var emailService = new EmailService();
        var service = new KafkaService("ECOMMERCE_SEND_EMAIL", emailService::parse);
        service.run();



    }

    private void parse(ConsumerRecord<String, String> record) throws InterruptedException {
        System.out.println("===========SEND EMAIL=================");
        System.out.println("OFFISET: " + record.offset());
        System.out.println("PARTITION: " +record.partition());
        System.out.println("TIME: " + record.timestamp());
        System.out.println("KEY: " + record.key());
        System.out.println("VALUE: " + record.key());
        System.out.println("=================================================");
        Thread.sleep(3000);
        System.out.println("Order Processed");
    }


}
