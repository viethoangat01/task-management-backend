package com.personal.taskmanagement.controller;

import com.personal.taskmanagement.model.dto.request.CreateProjectRequest;
import com.personal.taskmanagement.model.dto.response.CreateProjectResponse;
import com.personal.taskmanagement.service.ProjectService;
import com.personal.taskmanagement.util.annotation.ApiMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
  private final ProjectService projectService;

  @PostMapping
  @ApiMessage("Project created successfully")
  public ResponseEntity<CreateProjectResponse> createNewProject(@RequestBody CreateProjectRequest project) {
    return new ResponseEntity<>(projectService.createProject(project), HttpStatus.CREATED);
  }
}
