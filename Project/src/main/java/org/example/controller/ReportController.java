package org.example.controller;

import org.example.dao.ReportDAO;
import org.example.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportDAO reportDAO;

    @Autowired
    public ReportController(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

    @GetMapping("/current-state/{id}")
    public ResponseEntity<Item> getCurrentState(@PathVariable int id) {
        try {
            return new ResponseEntity<>(reportDAO.getCurrentItemState(id), HttpStatus.OK);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/longest-price-period")
    public ResponseEntity<List<PeriodReport>> getLongestPricePeriod() {
        try {
            return new ResponseEntity<>(reportDAO.getLongestPricePeriod(), HttpStatus.OK);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/price-difference-report")
    public ResponseEntity<List<PriceDifferenceReport>> getPriceDifferenceReport() {
        try {
            return new ResponseEntity<>(reportDAO.getPriceDifferences(), HttpStatus.OK);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/state-at-the-moment")
    public ResponseEntity<List<ItemHistoryRecord>> getStateAtTheMoment(@RequestParam(required = false) String timestamp) {
        try {
            return new ResponseEntity<>(reportDAO.getStateAtMoment(Timestamp.valueOf(timestamp)), HttpStatus.OK);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}