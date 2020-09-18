package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * THIS CLASS REPRESENT PRODUCER
 */
public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(getProperties());

        for (var i = 0; i < 100; i++){

            var key = UUID.randomUUID().toString();
            var value = "teste";

            var record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER",key, value);
            producer.send(record, getCallback()).get();

            var email = "Thanks you for your order! We are processing your order ";
            var emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL",key, email);
            producer.send(emailRecord, getCallback()).get();
        }

    }

    private static Callback getCallback() {
        return (data, ex) -> {
            if (Objects.nonNull(ex)) {
                ex.printStackTrace();
                return;
            }
            System.out.println("===========SUCCESS NEW ORDER=====================");
            System.out.println("TOPIC: " + data.topic() +
                    " <> " + "OFFISET: " + data.offset() +
                    " <> " + "PARTITION: " + data.partition() +
                    " <> " + "Timestamp: " + data.timestamp());
            System.out.println("==================================================");
        };
    }

    private static Properties getProperties() {
        var properties = new Properties();
        /*informando onde o producer esta rodando*/
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        /*informando o Serializador da chave para bytes*/
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        /*informando o Serializador da chave para bytes*/
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
