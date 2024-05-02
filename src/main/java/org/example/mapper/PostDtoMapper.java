package org.example.mapper;

import org.example.model.Album;
import org.example.model.Post;
import org.example.model.User;
import org.example.dto.PostIncomingDto;
import org.example.dto.PostOutGoingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostDtoMapper {
    @Mapping(target = "author", source = "author")
    Post map(PostIncomingDto postIncomingDto);

    @Mapping(target = "authorId", source = "author")
    PostOutGoingDto map(Post post);

    List<PostOutGoingDto> map(List<Post> posts);

    default User mapToUser(long userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }
    default long mapToId(User user) {
        return user.getId();
    }

    default Long mapToId(Album album) {
        return album.getId();
    }
}
