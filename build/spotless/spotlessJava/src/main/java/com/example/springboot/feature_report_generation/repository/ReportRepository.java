package com.example.springboot.feature_report_generation.repository;

import com.example.springboot.feature_report_generation.entity.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {}
