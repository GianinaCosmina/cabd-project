package org.example.controller;

import org.example.dao.ReportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportDAO reportDAO;

    @Autowired
    public ReportController(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }

}