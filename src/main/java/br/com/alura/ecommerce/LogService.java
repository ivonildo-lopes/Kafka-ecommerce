package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * This Class represent CONSUMER
 */
public class LogService {

    public static void main(String[] args) throws InterruptedException {
        var consumer = new KafkaConsumer<String, String>(getProperties());
        /*informando qual TOPIC o consumer irá escutar*/
        consumer.subscribe(Pattern.compile("ECOMMERCE.*"));
        while (true){
            var records = consumer.poll(Duration.ofMillis(100));

            if(!records.isEmpty()){
                System.out.println(">>>> Encontrei: " + records.count() + " registros");
                for (var record : records){
                    System.out.println("===========LOOGER=================");
                    System.out.println("OFFISET: " + record.offset());
                    System.out.println("PARTITION: " +record.partition());
                    System.out.println("TIME: " + record.timestamp());
                    System.out.println("KEY: " + record.key());
                    System.out.println("VALUE: " + record.key());
                    System.out.println("=================================================");

                    System.out.println("Log Processed");
                }

            }
        }

    }

    private static Properties getProperties() {
        var properties = new Properties();
        /*informando onde o producer esta rodando*/
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        /*informando o Deserealizador da chave */
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        /*informando o Deserealizador do valor */
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        /*informando o grupo do consumer*/
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, LogService.class.getSimpleName());
        return properties;
    }
}
