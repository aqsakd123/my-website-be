package com.example.my_website_pro.Entity.Mapper;

import com.example.my_website_pro.Entity.DTO.WorkSpaceDTO;
import com.example.my_website_pro.Entity.WorkSpace;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkSpaceMapper {

    WorkSpaceMapper INSTANCE = Mappers.getMapper(WorkSpaceMapper.class);

    WorkSpaceDTO toDTO(WorkSpace entity);

    WorkSpace toEntity(WorkSpaceDTO dto);

    List<WorkSpaceDTO> toListDTO(List<WorkSpace> listEntity);

    List<WorkSpace> toListEntities(List<WorkSpaceDTO> listDTO);

}