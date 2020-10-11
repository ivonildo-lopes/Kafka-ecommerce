package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import java.time.Instant;

/**
 * This Class represent CONSUMER
 */
public class FraudDectectorService {

    public static void main(String[] args) throws InterruptedException {
        var fraudService = new FraudDectectorService();
        try(var service = new KafkaService(FraudDectectorService.class.getSimpleName(), 
                "ECOMMERCE_NEW_ORDER",
                fraudService::parse)){
            service.run();
        }

    }

    private void parse(ConsumerRecord<String, String> record) throws InterruptedException {
        System.out.println("===========Processing NEW ORDER=================");
        System.out.println("OFFISET: " + record.offset());
        System.out.println("PARTITION: " +record.partition());
        System.out.println("TIME: " + Instant.ofEpochMilli(record.timestamp()));
        System.out.println("KEY: " + record.key());
        System.out.println("VALUE: " + record.value());
        System.out.println("=================================================");
        Thread.sleep(3000);
        System.out.println("Order Processed");
    }

}
