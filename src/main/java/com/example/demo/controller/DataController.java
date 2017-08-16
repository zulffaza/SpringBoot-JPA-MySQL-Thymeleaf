package com.example.demo.controller;

import com.example.demo.model.Data;
import com.example.demo.model.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class DataController {

    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;

    @Autowired
    public DataController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/")
    public String index(ModelMap modelMap) {
        String uri = "http://localhost:8080/getData";
        JsonNode response = restTemplate.getForObject(uri, JsonNode.class);

        try {
            Response<ArrayList<Data>> data = objectMapper.readValue(objectMapper.treeAsTokens(response), new TypeReference<Response<ArrayList<Data>>>(){});
            modelMap.addAttribute("data", data.getData());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

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

        String uri = "http://localhost:8080/saveData";
        JsonNode response = restTemplate.postForObject(uri, data, JsonNode.class);

        try {
            Response<Data> responseData = objectMapper.readValue(objectMapper.treeAsTokens(response), new TypeReference<Response<Data>>(){});
            modelMap.addAttribute("data", responseData.getData());

            if (responseData.getData() == null)
                modelMap.addAttribute("messages", "Data failed saved");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
        return "add-response";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable int id, ModelMap modelMap) {
        String uri = "http://localhost:8080/getData/" + id;
        JsonNode response = restTemplate.getForObject(uri, JsonNode.class);

        try {
            Response<Data> data = objectMapper.readValue(objectMapper.treeAsTokens(response), new TypeReference<Response<Data>>(){});
            modelMap.addAttribute("data", data.getData());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return "add";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        String uri = "http://localhost:8080/deleteData/" + id ;
        restTemplate.delete(uri);

        return "redirect:/";
    }
}
