package org.example.mapper;

import org.example.model.Album;
import org.example.model.Post;
import org.example.model.User;
import org.example.dto.UserIncomingDto;
import org.example.dto.UserOutGoingDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDtoMapper {
    User mapToUser(UserIncomingDto userIncomingDto);

    UserOutGoingDto mapToOutGoing(User user);

    List<UserOutGoingDto> mapToUotGoings(List<User> user);

    default Long mapById(Post post){
        return post.getId();
    }

    default Long mapById(Album album){
        return album.getId();
    }
}
