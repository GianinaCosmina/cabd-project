package org.example.controller;

import org.example.dao.OrderDAO;
import org.example.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderDAO orderDAO;

    @Autowired
    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }
    @GetMapping()
    public ResponseEntity<List<Order>> getTestData() {

        try {
            return new ResponseEntity<>(orderDAO.getAllOrders(), HttpStatus.OK);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity createOrder(@RequestBody Order item){
        try {
            orderDAO.createOrder(item);
            return ResponseEntity.status(201).build();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @PutMapping()
    public ResponseEntity updateOrder(@RequestBody Order updatedOrder) throws SQLException {
        orderDAO.updateOrder(updatedOrder);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) throws SQLException {
        orderDAO.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}