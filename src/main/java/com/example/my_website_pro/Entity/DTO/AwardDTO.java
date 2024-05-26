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

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class AwardDTO extends AuthorityDTO {

    private String id;
    
    @NotNull
    @Size(max=255)
    private String name;
    
    @Size(max=255)
    private String description;
    
    @NotNull
    private Integer awardPoint;
    
    private String color;
    
}