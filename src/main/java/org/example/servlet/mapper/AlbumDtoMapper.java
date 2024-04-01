package org.example.servlet.mapper;

import org.example.model.Album;
import org.example.servlet.dto.AlbumIncomingDto;
import org.example.servlet.dto.AlbumOutGoingDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlbumDtoMapper {
    Album map(AlbumIncomingDto albumIncomingDto);

    AlbumOutGoingDto map(Album album);

    List<AlbumOutGoingDto> map(List<Album> albums);
}
