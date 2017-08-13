package com.example.demo.controller;

import com.example.demo.model.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Controller
public class DataController {

    private RestTemplate restTemplate;

    public DataController() {
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/")
    public String index(ModelMap modelMap) {
        final String uri = "http://localhost:8080/getData";
        ArrayList<Data> response = restTemplate.getForObject(uri, ArrayList.class);

        modelMap.addAttribute("data", response);
        modelMap.addAttribute("messages", "Hello Spring Boot");

        return "index";
    }

    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        modelMap.addAttribute("data", new Data());

        return "add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Data data, BindingResult result, ModelMap modelMap) {
        if (data.getId() != 0)
            modelMap.addAttribute("messages", "Data success edited");
        else
            modelMap.addAttribute("messages", "Data success saved");

        if (result.hasErrors())
            return "add";

        final String uri = "http://localhost:8080/saveData";
        Data response = restTemplate.postForObject(uri, data, Data.class);

        modelMap.addAttribute("data", response);

        return "add-response";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable int id, ModelMap modelMap) {
        final String uri = "http://localhost:8080/getData/" + id ;
        Data response = restTemplate.getForObject(uri, Data.class);

        modelMap.addAttribute("data", response);

        return "add";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        final String uri = "http://localhost:8080/deleteData/" + id ;
        Void response = restTemplate.getForObject(uri, Void.class);

        return "redirect:/";
    }
}
