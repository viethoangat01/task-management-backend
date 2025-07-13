package com.personal.taskmanagement.service;

import com.personal.taskmanagement.entity.Project;
import com.personal.taskmanagement.entity.ProjectMember;
import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.model.constant.RoleProject;
import com.personal.taskmanagement.model.dto.MemberDto;
import com.personal.taskmanagement.model.dto.ProjectDto;
import com.personal.taskmanagement.model.exception.InvalidValueException;
import com.personal.taskmanagement.model.exception.ResourceNotFoundException;
import com.personal.taskmanagement.repository.ProjectMemberRepository;
import com.personal.taskmanagement.repository.ProjectRepository;
import com.personal.taskmanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Service class for managing projects.
 */
@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepo;
  private final ProjectMemberRepository projectMemberRepo;
  private final UserRepository userRepo;

  /**
   * Creates a new project with the given project information.
   * <p>
   * This method will:
   * <ul>
   *   <li>Validate the existence of the project owner.</li>
   *   <li>Persist the project without its members.</li>
   *   <li>Validate and associate members with the project using {@link ProjectMember} entities.</li>
   *   <li>Save all project members and assign them to the project.</li>
   * </ul>
   *
   * @param projectDto the data transfer object containing project details, including owner and
   *                   member IDs
   * @return a {@link ProjectDto} representing the saved project, including its members
   * @throws InvalidValueException if the owner or any member does not exist in the system
   */
  public ProjectDto createProject(ProjectDto projectDto) {
    // Check existing owner
    User owner = userRepo.findById(projectDto.getOwner().getId())
        .orElseThrow(() -> new InvalidValueException("owner",
            "owner with id " + projectDto.getOwner().getId() + " is not exist"));

    // Validate startDate and endDate
    if (projectDto.getStartDate().isAfter(projectDto.getEndDate())) {
      throw new InvalidValueException("endDate", "endDate must be is equal to or after startDate");
    }

    // Save project to database without members
    Project project = new Project(projectDto.getName(), projectDto.getDescription(),
        projectDto.getStartDate(), projectDto.getEndDate(), owner
    );

    projectRepo.save(project);

    // Get existing members
    List<Long> memberIds = projectDto.getMembers().stream()
        .map(MemberDto::getId)
        .toList();
    List<User> members = userRepo.findAllById(memberIds);

    // Create list ProjectMember entity with existing members
    List<ProjectMember> projectMembers = members.stream()
        .map(member -> new ProjectMember(member, project,
            RoleProject.MEMBER)) // Set default role in project is MEMBER
        .toList();

    // Save projectMembers to database
    projectMemberRepo.saveAll(projectMembers);

    // Set to project entity
    project.setProjectMembers(projectMembers);

    return ProjectDto.of(project);
  }

  /**
   * Updates an existing project with new details including name, description, dates, owner, and
   * member list.
   *
   * <p>Members not in the new list will be removed (if orphanRemoval is true),
   * and new members will be added.
   *
   * @param projectDto the updated project data
   * @return the updated project DTO
   * @throws ResourceNotFoundException if the project is not found
   * @throws InvalidValueException     if the owner is invalid or dates are incorrect
   */
  @Transactional // For using projectmember after fetch project because fetchType is LAZY
  public ProjectDto updateProject(ProjectDto projectDto) {
    // Get existing project
    Project project = projectRepo.findById(projectDto.getId())
        .orElseThrow(
            () -> new ResourceNotFoundException(
                "Project not found with id: " + projectDto.getId()));

    // Validate startDate and endDate
    if (projectDto.getStartDate().isAfter(projectDto.getEndDate())) {
      throw new InvalidValueException("endDate", "endDate must be is equal to or after startDate");
    }
    project.setStartDate(projectDto.getStartDate());
    project.setEndDate(projectDto.getEndDate());

    // Validate owner
    List<Long> memberIds = projectDto.getMembers().stream()
        .map(MemberDto::getId)
        .toList();
    if (memberIds.contains(projectDto.getOwner().getId())) {
      throw new InvalidValueException("owner", "owner cannot be a member");
    }
    User owner = userRepo.findById(projectDto.getOwner().getId())
        .orElseThrow(() -> new InvalidValueException("owner",
            "owner with id " + projectDto.getOwner().getId() + " is not exist"));
    project.setOwner(owner);

    // Check existing users of member list in request body
    List<User> members = userRepo.findAllById(memberIds);
    if (members.size() != projectDto.getMembers().size()) {
      throw new InvalidValueException("memberIds", "some memberIds are not exist in database");
    }

    // Delete member is not in memberIds list
    List<ProjectMember> projectMembers = project.getProjectMembers();
    projectMembers.removeIf(member -> !memberIds.contains(member.getId()));

    // Add new projectMember
    for (User member : members) {
      ProjectMember existing = project.getProjectMemberByUserId(member.getId());
      if (existing == null) {
        projectMembers.add(new ProjectMember(member, project, RoleProject.MEMBER));
      }
    }
    project.setName(projectDto.getName());
    project.setDescription(projectDto.getDescription());

    // Save to database
    project = projectRepo.save(project);

    return ProjectDto.of(project);
  }
}
