package com.example.my_website_pro.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TabCardDTO {

    private String id;

    private String tabName;

    private String tabContent;

    private Integer position;

}
