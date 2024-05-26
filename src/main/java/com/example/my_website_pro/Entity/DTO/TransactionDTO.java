package com.example.my_website_pro.Entity.DTO;

import com.example.my_website_pro.Entity.Common.AuthorityDTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.SuperBuilder;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
import java.util.Date;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class TransactionDTO extends AuthorityDTO {

    private String id;
    
    @NotNull
    @Size(max=255)
    private String name;
    
    @NotNull
    private LocalDateTime date;
    
    @NotNull
    private String type;
    
    @NotNull
    private String specificType;
    
    @Size(max=255)
    private String note;

    @NotNull
    private Integer moneyAmount;

    private FinanceDTO finance;
    
}