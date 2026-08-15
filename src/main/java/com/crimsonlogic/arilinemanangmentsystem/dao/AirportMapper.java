package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AirportMapper {

    @Results(id = "AirportResultMap", value = {
            @Result(property = "airportCode", column = "airport_code"),
            @Result(property = "airportName", column = "airport_name"),
            @Result(property = "city", column = "city")
    })
    @Select("SELECT airport_code, airport_name, city FROM airport WHERE is_deleted = 0")
    List<Airport> findAllAirport();

    @Results(id = "AirportDetailResultMap", value = {
            @Result(property = "airportCode", column = "airport_code"),
            @Result(property = "airportName", column = "airport_name"),
            @Result(property = "city", column = "city")
    })
    @Select("SELECT airport_code, airport_name, city FROM airport WHERE airport_code = #{airportCode} AND is_deleted = 0")
    Airport findById(String airportCode);
}