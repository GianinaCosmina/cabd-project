package org.example.controller;

import org.example.model.Item;
import org.example.dao.ItemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/items")
public class ItemsController {

    private final ItemDAO itemDAO;

    @Autowired
    public ItemsController(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }
    @GetMapping()
    public ResponseEntity<List<Item>> getTestData() {

        try {
            return new ResponseEntity<>(itemDAO.getAllItems(), HttpStatus.OK);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity createItem(@RequestBody Item item){
        try {
            itemDAO.createItem(item);
            return ResponseEntity.status(201).build();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateItem(@PathVariable int id, @RequestBody Item updatedItem) throws SQLException {
        updatedItem.setId(id);
        itemDAO.updateItem(updatedItem);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable int id) throws SQLException {
        itemDAO.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}