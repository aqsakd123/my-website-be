package com.example.my_website_pro.Entity.Mapper;

import com.example.my_website_pro.Entity.DTO.TaskListDTO;
import com.example.my_website_pro.Entity.TaskList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskListMapper {

    TaskListMapper INSTANCE = Mappers.getMapper(TaskListMapper.class);

    TaskListDTO toDTO(TaskList entity);

    TaskList toEntity(TaskListDTO dto);

    List<TaskListDTO> toListDTO(List<TaskList> listEntity);

    List<TaskList> toListEntities(List<TaskListDTO> listDTO);

}