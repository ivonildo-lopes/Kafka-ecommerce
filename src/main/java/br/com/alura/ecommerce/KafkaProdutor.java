package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Closeable;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

class KafkaProdutor implements Closeable {
    private final KafkaProducer<String, String> producer;

     KafkaProdutor() {
        this.producer = new KafkaProducer<>(getProperties());
    }

    public void send(String topic, String key, String value) throws ExecutionException, InterruptedException {
        var record = new ProducerRecord<>(topic,key, value);
        producer.send(record, getCallback()).get();
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

    @Override
    public void close()  {
        producer.close();
    }
}
