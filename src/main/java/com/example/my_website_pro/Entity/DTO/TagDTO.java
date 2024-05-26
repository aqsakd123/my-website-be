package com.example.my_website_pro.Entity.DTO;

import com.example.my_website_pro.Entity.Common.AuthorityDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class TagDTO extends AuthorityDTO {

    private String id;

    private String name;

    private String description;

    private String color;

    private Integer status;

    private String type;

    private String icon;

}
