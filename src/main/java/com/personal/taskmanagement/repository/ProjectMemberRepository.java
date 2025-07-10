package com.personal.taskmanagement.repository;

import com.personal.taskmanagement.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing ProjectMember entities.
 */
@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

}
