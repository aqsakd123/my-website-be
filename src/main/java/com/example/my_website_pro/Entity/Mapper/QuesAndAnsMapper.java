package com.example.my_website_pro.Entity.Mapper;

import com.example.my_website_pro.Entity.DTO.QuesAndAnsDTO;
import com.example.my_website_pro.Entity.QuesAndAns;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuesAndAnsMapper {

    QuesAndAnsMapper INSTANCE = Mappers.getMapper(QuesAndAnsMapper.class);

    QuesAndAnsDTO toDTO(QuesAndAns entity);

    QuesAndAns toEntity(QuesAndAnsDTO dto);

    List<QuesAndAnsDTO> toListDTO(List<QuesAndAns> listEntity);

    List<QuesAndAns> toListEntities(List<QuesAndAnsDTO> listDTO);

}