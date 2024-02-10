package com.example.my_website_pro.Entity.DTO.RequestDTO;

import com.example.my_website_pro.Entity.Common.PaggingRequest;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TagSpecificationDTO extends PaggingRequest {

    private String name;

    private String type;

    private Integer status;

}
