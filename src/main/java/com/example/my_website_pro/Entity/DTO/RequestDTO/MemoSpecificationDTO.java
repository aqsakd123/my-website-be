package com.example.my_website_pro.Entity.DTO.RequestDTO;

import com.example.my_website_pro.Entity.Common.PaggingRequest;
import com.example.my_website_pro.Entity.DTO.CategoryDTO;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemoSpecificationDTO extends PaggingRequest {

    private String name;

    private String type;

    private Integer status;

    private CategoryDTO category;

}
