package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static java.lang.System.getProperties;

public class KafkaService {

    private final KafkaConsumer<String, String> consumer;
    private final ConsumerFunction parse;

    public KafkaService(String topic, ConsumerFunction parse) throws InterruptedException {
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(getProperties());
        /*informando qual TOPIC o consumer irá escutar*/
        consumer.subscribe(Collections.singleton(topic));

    }

    public void run() throws InterruptedException {
        while (true){
            var records = consumer.poll(Duration.ofMillis(100));

            if(!records.isEmpty()){
                System.out.println(">>>> Encontrei: " + records.count() + " registros");
                for (var record : records){
                    parse.consume(record);
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
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, EmailService.class.getSimpleName());
        return properties;
    }
}

