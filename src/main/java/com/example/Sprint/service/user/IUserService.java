package com.example.Sprint.service.user;

import com.example.Sprint.dto.UserDto;

public interface IUserService {
    UserDto registerAsUser(UserDto userDto);
    String login(UserDto userDto);
}
