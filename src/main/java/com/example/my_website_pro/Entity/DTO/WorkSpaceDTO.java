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
public class WorkSpaceDTO extends AuthorityDTO {

    private String id;
    
    @NotNull
    @Size(max=50)
    private String name;
    
    @NotNull
    @Size(max=3000)
    private String description;
    
    @NotNull
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    private List<TaskListDTO> taskList;
    
}