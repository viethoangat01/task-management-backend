package com.personal.taskmanagement.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.personal.taskmanagement.model.constant.GlobalConstant;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
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
public class ProjectUpdateRequest {

  @NotNull(message = "id must not be null")
  private Long id;

  private String name;

  private String description;

  @JsonFormat(pattern = GlobalConstant.DATE_PATTERN)
  private LocalDate startDate;

  @JsonFormat(pattern = GlobalConstant.DATE_PATTERN)
  private LocalDate endDate;

  private Long ownerId;

  private List<Long> memberIds;
}
