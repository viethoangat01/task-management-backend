package com.personal.taskmanagement.service;

import com.personal.taskmanagement.model.dto.request.CreateProjectRequest;
import com.personal.taskmanagement.model.dto.response.CreateProjectResponse;

public interface ProjectService {
  CreateProjectResponse createProject(CreateProjectRequest project);
}
