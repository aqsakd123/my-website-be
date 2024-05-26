package com.example.my_website_pro.Entity.Mapper;

import com.example.my_website_pro.Entity.DTO.TransactionDTO;
import com.example.my_website_pro.Entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "finance", ignore = true)
//    @Mapping(target = "finance", conditionExpression = "java(requestedFields.contains('finance'))")
    TransactionDTO toDTO(Transaction entity);

    Transaction toEntity(TransactionDTO dto);

    List<TransactionDTO> toListDTO(List<Transaction> listEntity);

    List<Transaction> toListEntities(List<TransactionDTO> listDTO);

}