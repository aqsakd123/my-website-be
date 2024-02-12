package com.example.my_website_pro.Entity.Mapper;

import com.example.my_website_pro.Entity.DTO.AwardDTO;
import com.example.my_website_pro.Entity.Award;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AwardMapper {

    AwardMapper INSTANCE = Mappers.getMapper(AwardMapper.class);

    AwardDTO toDTO(Award entity);

    Award toEntity(AwardDTO dto);

    List<AwardDTO> toListDTO(List<Award> listEntity);

    List<Award> toListEntities(List<AwardDTO> listDTO);

}