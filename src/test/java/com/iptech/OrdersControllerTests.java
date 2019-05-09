package com.iptech;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iptech.domain.Orders;
import com.iptech.web.OrdersController;
import org.junit.Assert;
import org.junit.Before;
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

import static org.springframework.http.HttpStatus.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdersControllerTests {

    @Autowired
    private OrdersController ordersController;

    @Before
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

    @Test
    public void testUpdateVotes() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Orders>> typeReference = new TypeReference<List<Orders>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/votes.json");
        List<Orders> ordersList = mapper.readValue(inputStream,typeReference);
        for(Orders orders : ordersList) {
            ResponseEntity<Orders> updatedOrder = ordersController.updateVotes(orders.getId(), orders);
            Assert.assertEquals("Error Updating Order" + orders.toString(), HttpStatus.OK, updatedOrder.getStatusCode());
        }
    }

    @Test
    public void testListOrdersReverseByVotes() throws Exception {
        testUpdateVotes();
        Assert.assertEquals("Error in listing Orders Reverse By Votes", true, ordersController.listOrdersReverseByVotes());
    }
}
