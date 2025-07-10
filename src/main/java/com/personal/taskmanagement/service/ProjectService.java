package com.personal.taskmanagement.service;

import com.personal.taskmanagement.entity.Project;
import com.personal.taskmanagement.entity.ProjectMember;
import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.model.constant.RoleProject;
import com.personal.taskmanagement.model.dto.MemberDto;
import com.personal.taskmanagement.model.dto.ProjectDto;
import com.personal.taskmanagement.model.exception.InvalidValueException;
import com.personal.taskmanagement.repository.ProjectMemberRepository;
import com.personal.taskmanagement.repository.ProjectRepository;
import com.personal.taskmanagement.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    // Save projectmembers to database
    projectMemberRepo.saveAll(projectMembers);

    // Set to project entity
    project.setProjectMembers(projectMembers);

    return ProjectDto.of(project);
  }
}
