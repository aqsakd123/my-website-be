package com.example.my_website_pro.Entity.Mapper;

import com.example.my_website_pro.Entity.DTO.TabCardDTO;
import com.example.my_website_pro.Entity.DTO.TagDTO;
import com.example.my_website_pro.Entity.TabCard;
import com.example.my_website_pro.Entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TabMapper {

    TabMapper INSTANCE = Mappers.getMapper(TabMapper.class);

    TabCardDTO toDTO(TabCard entity);

    TabCard toEntity(TabCardDTO dto);

    List<TabCardDTO> toListDTO(List<TabCard> listEntity);

    List<TabCard> toListEntities(List<TabCardDTO> listDTO);

}
