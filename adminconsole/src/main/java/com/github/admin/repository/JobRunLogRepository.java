package com.github.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.admin.model.JobRunLog;

public interface JobRunLogRepository extends JpaRepository<JobRunLog, Integer>{

}
