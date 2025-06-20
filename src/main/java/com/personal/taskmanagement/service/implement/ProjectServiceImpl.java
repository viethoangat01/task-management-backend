package com.personal.taskmanagement.service.implement;

import com.personal.taskmanagement.entity.Project;
import com.personal.taskmanagement.model.dto.request.CreateProjectRequest;
import com.personal.taskmanagement.model.dto.response.CreateProjectResponse;
import com.personal.taskmanagement.repository.ProjectRepository;
import com.personal.taskmanagement.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepo;

  @Override
  public CreateProjectResponse createProject(CreateProjectRequest project) {
    // create entity
    Project newProject = new Project(project.getName(), project.getDescription());

    // save to db
    Project savedProject = projectRepo.save(newProject);

    // map to response
    return new CreateProjectResponse(
        savedProject.getId(),
        savedProject.getName(),
        savedProject.getDescription()
    );

  }
}
