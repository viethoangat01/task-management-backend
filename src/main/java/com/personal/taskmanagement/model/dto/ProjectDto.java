package com.personal.taskmanagement.model.dto;

import com.personal.taskmanagement.entity.Project;
import com.personal.taskmanagement.entity.ProjectMember;
import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.model.vo.ProjectCreateRequest;
import com.personal.taskmanagement.model.vo.ProjectUpdateRequest;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {

  private Long id;

  private String name;

  private String description;

  private LocalDate startDate;

  private LocalDate endDate;

  private OwnerDto owner;

  private List<MemberDto> members = new ArrayList<>();

  private Instant createdAt;

  public static ProjectDto of(ProjectCreateRequest projectCreateRequest) {
    OwnerDto owner = OwnerDto.builder().id(projectCreateRequest.getOwnerId()).build();
    List<MemberDto> members = projectCreateRequest.getMemberIds()
        .stream()
        .map(id -> MemberDto.builder().id(id).build())
        .toList();

    return ProjectDto.builder()
        .name(projectCreateRequest.getName())
        .description(projectCreateRequest.getDescription())
        .startDate(projectCreateRequest.getStartDate())
        .endDate(projectCreateRequest.getEndDate())
        .owner(owner)
        .members(members)
        .build();
  }

  public static ProjectDto of(ProjectUpdateRequest projectUpdateRequest) {
    OwnerDto owner = OwnerDto.builder().id(projectUpdateRequest.getOwnerId()).build();
    List<MemberDto> members = projectUpdateRequest.getMemberIds()
        .stream()
        .map(id -> MemberDto.builder().id(id).build())
        .toList();

    return ProjectDto.builder()
        .id(projectUpdateRequest.getId())
        .name(projectUpdateRequest.getName())
        .description(projectUpdateRequest.getDescription())
        .startDate(projectUpdateRequest.getStartDate())
        .endDate(projectUpdateRequest.getEndDate())
        .owner(owner)
        .members(members)
        .build();
  }

  public static ProjectDto of(Project project) {
    User ownerEntity = project.getOwner();
    OwnerDto owner = (ownerEntity == null) ? null : OwnerDto.builder()
        .id(ownerEntity.getId())
        .name(ownerEntity.getName())
        .email(ownerEntity.getEmail())
        .build();

    List<MemberDto> members = project.getProjectMembers().stream()
        .map(ProjectMember::getUser)
        .map(user -> new MemberDto(user.getId(), user.getName()))
        .toList();

    return ProjectDto.builder()
        .id(project.getId())
        .name(project.getName())
        .description(project.getDescription())
        .startDate(project.getStartDate())
        .endDate(project.getEndDate())
        .owner(owner)
        .members(members)
        .createdAt(project.getCreatedAt())
        .build();
  }
}
