package com.personal.taskmanagement.model.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class UserRegisterRequest {

    @NotBlank(message = "name should not be null or empty")
    private String name;

    @Override
    public String toString() {
        return "UserRegisterRequest{" +
                "name='" + name +
                '}';
    }
}
