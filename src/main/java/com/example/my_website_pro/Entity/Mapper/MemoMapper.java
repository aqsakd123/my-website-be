package com.example.my_website_pro.Entity.Mapper;

import com.example.my_website_pro.Entity.DTO.MemoDTO;
import com.example.my_website_pro.Entity.Memo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemoMapper {

    MemoMapper INSTANCE = Mappers.getMapper(MemoMapper.class);

    MemoDTO toDTO(Memo entity);

    Memo toEntity(MemoDTO dto);

    List<MemoDTO> toListDTO(List<Memo> listEntity);

    List<Memo> toListEntities(List<MemoDTO> listDTO);

}
