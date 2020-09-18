package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.time.Instant;

/**
 * This Class represent CONSUMER
 */
public class EmailService {

    public static void main(String[] args) throws InterruptedException {
        var emailService = new EmailService();
        try(var service = new KafkaService(EmailService.class.getSimpleName(),
                "ECOMMERCE_SEND_EMAIL",
                emailService::parse)){
            service.run();
        }

    }

    private void parse(ConsumerRecord<String, String> record) throws InterruptedException {
        System.out.println("===========SEND EMAIL=================");
        System.out.println("OFFISET: " + record.offset());
        System.out.println("PARTITION: " +record.partition());
        System.out.println("TIME: " + Instant.ofEpochMilli(record.timestamp()));
        System.out.println("KEY: " + record.key());
        System.out.println("VALUE: " + record.value());
        System.out.println("=================================================");
        Thread.sleep(500);
        System.out.println("Order Processed");
    }


}
