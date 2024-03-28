package org.example.servlet.mapper;

import org.example.model.User;
import org.example.servlet.dto.UserIncomingDto;
import org.example.servlet.dto.UserOutGoingDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDtoMapper {
    User map(UserIncomingDto userIncomingDto);

    UserOutGoingDto map(User user);

}