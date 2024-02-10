package com.example.my_website_pro.Entity.DTO;

import com.example.my_website_pro.Entity.Common.AuthorityDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

}
