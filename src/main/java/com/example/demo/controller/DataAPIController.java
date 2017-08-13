package com.example.demo.controller;

import com.example.demo.model.Data;
import com.example.demo.service.DataService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class DataAPIController {

    private DataService dataService;

    public DataAPIController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/getData")
    public ArrayList<Data> getData() {
        return dataService.getAllData();
    }

    @GetMapping("/getData/{id}")
    public Data getData(@PathVariable int id) {
        return dataService.getData(id);
    }

    @PostMapping("/saveData")
    public Data saveData(@RequestBody Data data) {
        return dataService.saveData(data);
    }

    @GetMapping("/deleteData/{id}")
    public void deleteData(@PathVariable int id) {
        dataService.deleteData(id);
    }
}
