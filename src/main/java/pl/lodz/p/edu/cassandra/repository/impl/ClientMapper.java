package pl.lodz.p.edu.cassandra.repository.impl;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.DaoTable;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

@Mapper
public interface ClientMapper {

    @DaoFactory
    ClientDao clientDao();

    @DaoFactory
    ClientDao clientDao(@DaoKeyspace String keyspace, @DaoTable String daoTable);
    //
}
