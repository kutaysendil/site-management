package com.kutaysendil.siteManagement.mapper;

import com.kutaysendil.siteManagement.dto.request.UserRequest;
import com.kutaysendil.siteManagement.dto.response.UserResponse;
import com.kutaysendil.siteManagement.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserResponse toResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    public User toEntity(UserRequest request) {
        return modelMapper.map(request, User.class);
    }

    public List<UserResponse> toResponseList(List<User> users) {
        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}