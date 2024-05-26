package com.example.my_website_pro.Entity.Mapper;

import com.example.my_website_pro.Entity.DTO.TagDTO;
import com.example.my_website_pro.Entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagDTO toDTO(Tag entity);

    Tag toEntity(TagDTO dto);

    List<TagDTO> toListDTO(List<Tag> listEntity);

    List<Tag> toListEntities(List<TagDTO> listDTO);

}
