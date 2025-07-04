package com.personal.taskmanagement.util;

import com.personal.taskmanagement.entity.Project;
import com.personal.taskmanagement.entity.ProjectMember;
import com.personal.taskmanagement.model.dto.MemberDto;
import com.personal.taskmanagement.model.dto.OwnerDto;
import com.personal.taskmanagement.model.dto.ProjectDto;
import com.personal.taskmanagement.model.vo.ProjectCreateRequest;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;

/**
 * Support to mapping among these 3 objects - DTO(Data Transfer Object) - VO(Value Object like API
 * request) - Entity(DB domain object)
 */
public class MapperUtil {

  public static final ModelMapper mapper;

  static {
    mapper = new ModelMapper();
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    customMappingProjectCreateRequestToProjectDto();

    customMappingProjectToProjectDto();
  }

  private static void customMappingProjectToProjectDto() {
    TypeMap<Project, ProjectDto> propertyMapper = mapper.createTypeMap(Project.class,
        ProjectDto.class);

    propertyMapper.addMappings(mapper -> {
      // Custom map list ProjectMember to list MemberDto
      mapper.using(ctx -> {
        List<ProjectMember> projectMembers = (List<ProjectMember>) ctx.getSource();
        return projectMembers.stream()
            .map(ProjectMember::getUser)
            .map(user -> MemberDto.builder().id(user.getId()).name(user.getName()).build())
            .toList();

      }).map(Project::getProjectMembers, ProjectDto::setMembers);
    });
  }

  private static void customMappingProjectCreateRequestToProjectDto() {
    TypeMap<ProjectCreateRequest, ProjectDto> propertyMapper = mapper.createTypeMap(
        ProjectCreateRequest.class, ProjectDto.class
    );

    // Config map ProjectCreateRequest to ProjectDto
    propertyMapper.addMappings(mapper ->
    {
      // Custom map  list members ids to list MemberDto
      mapper.using(ctx -> {
        List<Long> ids = (List<Long>) ctx.getSource();
        return ids.stream()
            .map(id -> MemberDto.builder().id(id).build())
            .toList();
      }).map(ProjectCreateRequest::getMemberIds, ProjectDto::setMembers);

      // Custom map owner id to OwnerDto
      mapper.using(ctx -> {
        Long id = (Long) ctx.getSource();
        return OwnerDto.builder().id(id).build();
      }).map(ProjectCreateRequest::getOwnerId, ProjectDto::setOwner);

    });
  }

}
