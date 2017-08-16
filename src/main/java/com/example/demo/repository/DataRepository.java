package com.example.demo.repository;

import com.example.demo.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<Data, Integer> {

    public Data findByNrp(int nrp);
}
