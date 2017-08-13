package com.example.demo.service;

import com.example.demo.dao.DataDAO;
import com.example.demo.model.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DataService {

    private DataDAO dataDAO;

    public DataService(DataDAO dataDAO) {
        this.dataDAO = dataDAO;
    }

    public ArrayList<Data> getAllData(){
        ArrayList<Data> data = new ArrayList<>();
        dataDAO.findAll().forEach(data::add);

        return data;
    }

    public Data getData(int id) {
        return dataDAO.findOne(id);
    }

    public Data saveData(Data data) {
        return dataDAO.save(data);
    }

    public void deleteData(int id) {
        dataDAO.delete(id);
    }
}
