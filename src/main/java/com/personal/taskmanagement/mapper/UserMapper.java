package com.personal.taskmanagement.mapper;

import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.model.dto.response.user.UserSearchResponse;
import org.mapstruct.Mapper;


/**
 * Support to mapping among these 3 objects
 * - DTO(Data Transfer Object)
 * - VO(Value Object like API request)
 * - Entity(DB domain object)
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
  UserSearchResponse mapToUserSearchResponse(User user);
}
