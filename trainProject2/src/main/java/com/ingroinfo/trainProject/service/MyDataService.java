package com.ingroinfo.trainProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ingroinfo.trainProject.entities.MyOrder;
import com.ingroinfo.trainProject.entities.PassengerDetails;

@Service
public class MyDataService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<PassengerDetails> getMyData1() {
        return jdbcTemplate.query("SELECT * FROM passenger_details", new BeanPropertyRowMapper<>(PassengerDetails.class));
    }

    public List<MyOrder> getMyData2() {
        return jdbcTemplate.query("SELECT * FROM booking_details", new BeanPropertyRowMapper<>(MyOrder.class));
    }
}
