package com.iptech.web;

import com.iptech.domain.Orders;
import com.iptech.service.OrdersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class OrdersController {
    private OrdersService ordersService;

    @Autowired
    public void setOrdersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/createorders")
    @RequestMapping(value="", method= RequestMethod.POST)
    public ResponseEntity<Orders> createOrder(@RequestBody Orders  order) {
        Orders savedOrder = ordersService.save(order);
        return new ResponseEntity<Orders> (savedOrder, HttpStatus.OK);
    }

    @PostMapping("/updatevotes")
    @RequestMapping(value="{id}", method=RequestMethod.PUT)
    public ResponseEntity<Orders> updateVotes (@PathVariable Long id, @RequestBody Orders orders)
    {
        Optional<Orders> optionalOrder = ordersService.findOne(id);
        if (optionalOrder == null)
        {
            return new ResponseEntity<Orders>(HttpStatus.NO_CONTENT);
        }
        BeanUtils.copyProperties(orders, optionalOrder, "id");
        Orders savedOrder = ordersService.save(orders);
        return new ResponseEntity<Orders>(savedOrder, HttpStatus.OK);
    }

    // READ
    @GetMapping("/readquotes")
    @RequestMapping(value="", method=RequestMethod.GET)
    public void listOrdersReverseByVotes()
    {
        List<Orders> ordersList = (List<Orders>) ordersService.list();
        ordersList.sort(Comparator.comparing(Orders::getVotes)
                .reversed()
        );
        ordersList.forEach(System.out::println);

    }

}
