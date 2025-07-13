package com.personal.taskmanagement.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.personal.taskmanagement.model.constant.GlobalConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ProjectCreateRequest {

  @NotBlank(message = "name must not be blank")
  private String name;

  private String description;

  @NotNull(message = "start date must not be null")
  @JsonFormat(pattern = GlobalConstant.DATE_PATTERN)
  private LocalDate startDate;

  @NotNull(message = "end date must not be null")
  @JsonFormat(pattern = GlobalConstant.DATE_PATTERN)
  private LocalDate endDate;

  @NotNull(message = "owner id must not be null")
  private Long ownerId;

  private List<Long> memberIds = new ArrayList<>();
}
