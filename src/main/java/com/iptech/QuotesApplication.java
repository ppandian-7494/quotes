package com.iptech;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iptech.web.OrdersController;
import com.iptech.domain.Orders;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class QuotesApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotesApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(OrdersController ordersController){
        return args -> {
//            read JSON and load json
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Orders>> typeReference = new TypeReference<List<Orders>>(){};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/orders.json");
            try {
                List<Orders> ordersList = mapper.readValue(inputStream,typeReference);
                for(Orders orders : ordersList) {
                    ordersController.createOrder(orders);
                }
            } catch (IOException e){
                System.out.println("Unable to save orders: " + e.getMessage());
            }

//            update votes and load json
            mapper = new ObjectMapper();
            typeReference = new TypeReference<List<Orders>>(){};
            inputStream = TypeReference.class.getResourceAsStream("/json/votes.json");
            try {
                List<Orders> ordersList = mapper.readValue(inputStream,typeReference);
                for(Orders orders : ordersList) {
                    ordersController.updateVotes(orders.getId(), orders);
                }
            } catch (IOException e){
                System.out.println("Unable to update votes: " + e.getMessage());
            }

            ordersController.listOrdersReverseByVotes();

        };
    }

}
