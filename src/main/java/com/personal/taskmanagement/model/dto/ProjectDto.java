package com.personal.taskmanagement.model.dto;

import com.personal.taskmanagement.entity.Project;
import com.personal.taskmanagement.model.vo.ProjectCreateRequest;
import com.personal.taskmanagement.util.MapperUtil;
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
    return MapperUtil.mapper.map(projectCreateRequest, ProjectDto.class);
  }

  public static ProjectDto of(Project project) {
    return MapperUtil.mapper.map(project, ProjectDto.class);
  }
}
