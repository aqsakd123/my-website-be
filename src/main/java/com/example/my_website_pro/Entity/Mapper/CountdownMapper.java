package com.example.my_website_pro.Entity.Mapper;

import com.example.my_website_pro.Entity.DTO.CountdownDTO;
import com.example.my_website_pro.Entity.Countdown;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountdownMapper {

    CountdownMapper INSTANCE = Mappers.getMapper(CountdownMapper.class);

    CountdownDTO toDTO(Countdown entity);

    Countdown toEntity(CountdownDTO dto);

    List<CountdownDTO> toListDTO(List<Countdown> listEntity);

    List<Countdown> toListEntities(List<CountdownDTO> listDTO);

}