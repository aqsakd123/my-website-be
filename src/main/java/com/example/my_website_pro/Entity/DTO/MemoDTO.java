package com.example.my_website_pro.Entity.DTO;

import com.example.my_website_pro.Entity.Category;
import com.example.my_website_pro.Entity.Common.AuthorityDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class MemoDTO extends AuthorityDTO {

    private String id;

    private String color;

    private String name;

    @NotNull
    private String type;

    private Integer status;

    private Double position;

    private List<TabCardDTO> tabCardList;

    private List<TagDTO> tags;

    private List<QuesAndAnsDTO> qaList;

    private Category category;

}
