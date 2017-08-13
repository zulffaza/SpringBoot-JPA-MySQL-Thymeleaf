package com.example.demo.dao;

import com.example.demo.model.Data;
import org.springframework.data.repository.CrudRepository;

public interface DataDAO extends CrudRepository<Data, Integer> {
}
