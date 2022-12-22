package pl.lodz.p.edu.cassandra.repository.impl;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

@Mapper
public interface RentMapper {

    @DaoFactory
    RentDao rentDao();
}