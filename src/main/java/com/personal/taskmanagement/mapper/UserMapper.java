package com.personal.taskmanagement.mapper;

import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.model.dto.request.user.UserCreateRequest;
import com.personal.taskmanagement.model.dto.response.user.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User mapToUser(UserCreateRequest userRequest);

  UserResponse mapToUserResponse(User user);
}
