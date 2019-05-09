package com.iptech;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iptech.domain.Orders;
import com.iptech.service.OrdersService;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdersServiceTests {

    @Autowired
    private OrdersService ordersService;

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
    public void testFindOne() throws Exception {
        long id = 1;
        Optional<Orders> optionalOrder = ordersService.findOne(id);
        Assert.assertTrue("Unable to find Order with id: " + id, optionalOrder!= null);
    }

    @Test
    public void testList() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Orders>> typeReference = new TypeReference<List<Orders>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/orders.json");
        List<Orders> expectedOrdersList = mapper.readValue(inputStream,typeReference);
        List<Orders> actualOrdersList = (List<Orders>) ordersService.list();
        Boolean flag = false;
        for (Orders obj2 : expectedOrdersList) {
            for (Orders obj1 : actualOrdersList) {
                flag = false;
                if (obj1.getId() == obj2.getId() && obj1.getName().equals(obj2.getName()) && obj1.getQuote() == (obj2.getQuote()) && obj1.getVotes() == (obj2.getVotes())) {
                    flag = true;
                    break;
                }
            }
            Assert.assertTrue("Object not found : " + obj2.toString(), flag);
        }
    }

    @Test
    public void testSave() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Orders>> typeReference = new TypeReference<List<Orders>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/testorders.json");
        List<Orders> ordersList = mapper.readValue(inputStream,typeReference);
        Orders order = ordersService.save(ordersList.get(0));
        Assert.assertTrue("Error in OrdersService Save() functionality... " ,order.equals(ordersList.get(0)));
    }
}
