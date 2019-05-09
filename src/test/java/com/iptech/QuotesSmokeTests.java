package com.iptech;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iptech.domain.Orders;
import com.iptech.web.OrdersController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuotesSmokeTests {

    @Autowired
    private OrdersController ordersController;

    @Test
    public void testCreateOrder() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Orders>> typeReference = new TypeReference<List<Orders>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/orders.json");
        List<Orders> ordersList = mapper.readValue(inputStream,typeReference);
        for(Orders orders : ordersList) {
            ResponseEntity<Orders> savedOrder = ordersController.createOrder(orders);
            Assert.assertEquals("Error Creating Order" + orders.toString(), HttpStatus.OK, savedOrder.getStatusCode());
        }
    }

}
