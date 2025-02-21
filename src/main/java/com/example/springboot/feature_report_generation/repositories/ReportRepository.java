package com.example.springboot.feature_report_generation.repositories;

import com.example.springboot.feature_report_generation.entities.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {}
