package com.personal.taskmanagement.model.dto;

import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.model.util.MapperUtil;
import com.personal.taskmanagement.model.vo.UserRegisterRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * A DTO representing a user
 */
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserDto {

    @NotBlank(message = "id should not be null or empty")
    private Long id;

    @Size(max = 50)
    private String name;

    public static UserDto of(UserRegisterRequest userRegisterRequest) {
        return MapperUtil.mapper.map(userRegisterRequest, UserDto.class);
    }

    public static UserDto of(User user) {
        return MapperUtil.mapper.map(user, UserDto.class);
    }
}
