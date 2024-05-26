package com.example.my_website_pro.Entity.Mapper;

import com.example.my_website_pro.Entity.DTO.SubTaskDTO;
import com.example.my_website_pro.Entity.SubTask;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubTaskMapper {

    SubTaskMapper INSTANCE = Mappers.getMapper(SubTaskMapper.class);

    SubTaskDTO toDTO(SubTask entity);

    SubTask toEntity(SubTaskDTO dto);

    List<SubTaskDTO> toListDTO(List<SubTask> listEntity);

    List<SubTask> toListEntities(List<SubTaskDTO> listDTO);

}