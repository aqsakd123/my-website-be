package com.example.my_website_pro.Entity.Mapper;

import com.example.my_website_pro.Entity.DTO.FinanceDTO;
import com.example.my_website_pro.Entity.Finance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FinanceMapper {

    FinanceMapper INSTANCE = Mappers.getMapper(FinanceMapper.class);

    @Mapping(target = "transactions", ignore = true)
    FinanceDTO toDTO(Finance entity);

    Finance toEntity(FinanceDTO dto);

    List<FinanceDTO> toListDTO(List<Finance> listEntity);

    List<Finance> toListEntities(List<FinanceDTO> listDTO);

}