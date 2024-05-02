package org.example.service;

import org.example.dto.UserIncomingDto;
import org.example.dto.UserOutGoingDto;

import java.util.List;


public interface UserService {
    UserOutGoingDto save(UserIncomingDto userIncomingDto);

    void update(UserIncomingDto userIncomingDto);

    boolean deleteById(long id);

    UserOutGoingDto findById(long id);

    List<UserOutGoingDto> findAll();

}
