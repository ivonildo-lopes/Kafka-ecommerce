package br.com.alura.ecommerce;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * THIS CLASS REPRESENT PRODUCER
 */
public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try(var productor = new KafkaProdutor()){
            for (var i = 0; i < 10; i++){

                var key = UUID.randomUUID().toString();

                var value = "teste";
                productor.send("ECOMMERCE_NEW_ORDER",key, value);

                var email = "Thanks you for your order! We are processing your order 2";
                productor.send("ECOMMERCE_SEND_EMAIL",key, email);
            }
        }
    }
}
