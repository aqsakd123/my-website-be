package com.example.my_website_pro.Entity.Mapper;

import com.example.my_website_pro.Entity.DTO.CategoryDTO;
import com.example.my_website_pro.Entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO toDTO(Category entity);

    Category toEntity(CategoryDTO dto);

    List<CategoryDTO> toListDTO(List<Category> listEntity);

    List<Category> toListEntities(List<CategoryDTO> listDTO);

}