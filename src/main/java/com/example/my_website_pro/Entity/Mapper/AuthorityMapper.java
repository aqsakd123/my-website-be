package com.example.my_website_pro.Entity.Mapper;

import com.example.my_website_pro.Entity.Common.Authority;
import com.example.my_website_pro.Entity.Common.AuthorityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {

    AuthorityMapper INSTANCE = Mappers.getMapper(AuthorityMapper.class);

    AuthorityDTO toDTO(Authority entity);

    Authority toEntity(AuthorityDTO dto);

    List<AuthorityDTO> toListDTO(List<Authority> listEntity);

    List<Authority> toListEntities(List<AuthorityDTO> listDTO);


}
