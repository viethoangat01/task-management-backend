package com.personal.taskmanagement.controller;

import com.personal.taskmanagement.model.dto.ProjectDto;
import com.personal.taskmanagement.model.vo.ProjectCreateRequest;
import com.personal.taskmanagement.model.vo.ProjectUpdateRequest;
import com.personal.taskmanagement.service.ProjectService;
import com.personal.taskmanagement.util.annotation.ApiMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing the project
 */
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;

  /**
   * Creates a new project based on the provided request body.
   *
   * @param projectCreateRequest the project creation data received from the client
   * @return {@link ResponseEntity} containing the created {@link ProjectDto} and HTTP status 200
   * (OK)
   */
  @PostMapping
  @Operation(summary = "Create a new project.",
      description = "This API allows user to create a new project by providing required project data in the request body.",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Project create request",
          required = true,
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ProjectCreateRequest.class)
          )
      ),
      responses = {
          @ApiResponse(responseCode = "200", description = "Project successfully created"),
      })
  @ApiMessage("Project created successfully")
  public ResponseEntity<ProjectDto> createProject(
      @RequestBody @Valid ProjectCreateRequest projectCreateRequest) {
    return ResponseEntity.ok(projectService.createProject(ProjectDto.of(projectCreateRequest)));
  }

  /**
   * Updates a project based on the provided request body.
   *
   * @param projectUpdateRequest the project update data received from the client
   * @return {@link ResponseEntity} containing the created {@link ProjectDto} and HTTP status 200
   * (OK)
   */
  @PutMapping
  @Operation(summary = "Update a project.",
      description = "This API allows user to update a project by providing required project data in the request body.",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Project update request",
          required = true,
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ProjectUpdateRequest.class)
          )
      ),
      responses = {
          @ApiResponse(responseCode = "200", description = "Project successfully created"),
      })
  @ApiMessage(value = "Project updated successfully")
  public ResponseEntity<ProjectDto> updateProject(
      @RequestBody @Valid ProjectUpdateRequest projectUpdateRequest
  ) {
    return ResponseEntity.ok(projectService.updateProject(ProjectDto.of(projectUpdateRequest)));
  }
}
