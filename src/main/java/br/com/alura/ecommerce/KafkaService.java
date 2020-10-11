package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

class KafkaService implements Closeable {

    private final KafkaConsumer<String, String> consumer;
    private final ConsumerFunction parse;

    private KafkaService(ConsumerFunction parse, String groupId){
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(getProperties(groupId));
    }


     KafkaService(String groupId, String topic, ConsumerFunction parse) throws InterruptedException {
        this(parse,groupId);
        /*informando qual TOPIC o consumer irá escutar*/
        consumer.subscribe(Collections.singleton(topic));

    }

    KafkaService(String groupId, Pattern topic, ConsumerFunction parse) {
        this(parse,groupId);
        /*informando qual TOPIC o consumer irá escutar*/
        consumer.subscribe(topic);
    }

    void run() throws InterruptedException {
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

    private static Properties getProperties(String groupId) {
        var properties = new Properties();
        /*informando onde o producer esta rodando*/
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        /*informando o Deserealizador da chave */
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        /*informando o Deserealizador do valor */
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        /*informando o grupo do consumer*/
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId );

        /*informando o nome do consumidor*/
//        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG , clazz.getSimpleName() + " - " + UUID.randomUUID().toString());
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG , UUID.randomUUID().toString());

        /*informando o maximo de record(registros) que quer consumir - obs.: consome uma msg e comita*/
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG , "1");
        return properties;
    }

    @Override
    public void close()  {
        consumer.close();
    }
}

