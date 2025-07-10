package com.personal.taskmanagement.repository;

import com.personal.taskmanagement.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Project entities.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
