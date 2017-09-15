package com.srikanth.springboot.db;

import org.springframework.data.cassandra.repository.CassandraRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends CassandraRepository<Record>{

}
