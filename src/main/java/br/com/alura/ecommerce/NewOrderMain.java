package br.com.alura.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * THIS CLASS REPRESENT PRODUCER
 */
public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try(var productorOrder = new KafkaProdutor<Order>()){
            try(var productorEmail = new KafkaProdutor<String>()){
                for (var i = 0; i < 10; i++){

                    var userId = UUID.randomUUID().toString();
                    var orderId = UUID.randomUUID().toString();
                    var order = new Order(userId,orderId, new BigDecimal(Math.random() * 5000 + 1));

                    productorOrder.send("ECOMMERCE_NEW_ORDER",userId, order);

                    var email = "Thanks you for your order! We are processing your order 2";
                    productorEmail.send("ECOMMERCE_SEND_EMAIL",userId, email);
                }
            }
        }
   }
}
