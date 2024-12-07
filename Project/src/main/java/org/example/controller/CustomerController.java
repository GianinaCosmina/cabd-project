package org.example.controller;

import org.example.model.Customer;
import org.example.dao.CustomerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerDAO customerDAO;

    @Autowired
    public CustomerController(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
    @GetMapping()
    public ResponseEntity<List<Customer>> getTestData() {

        try {
            return new ResponseEntity<>(customerDAO.getAllCustomers(), HttpStatus.OK);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody Customer item){
        try {
            customerDAO.createCustomer(item);
            return ResponseEntity.status(201).build();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCustomer(@PathVariable int id, @RequestBody Customer updatedCustomer) throws SQLException {
        updatedCustomer.setId(id);
        customerDAO.updateCustomer(updatedCustomer);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) throws SQLException {
        customerDAO.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}