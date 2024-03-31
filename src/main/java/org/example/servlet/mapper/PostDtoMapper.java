package org.example.servlet.mapper;

import org.example.model.Post;
import org.example.servlet.dto.PostIncomingDto;
import org.example.servlet.dto.PostOutGoingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostDtoMapper {
    @Mapping(target = "author", source = "author")
    Post map(PostIncomingDto postIncomingDto);

    PostOutGoingDto map(Post post);

    List<PostOutGoingDto> map (List<Post> posts);
}
