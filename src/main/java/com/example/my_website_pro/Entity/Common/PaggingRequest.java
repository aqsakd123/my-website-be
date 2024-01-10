package com.example.my_website_pro.Entity.Common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaggingRequest {

    private Integer pageSize;
    private Integer pageIndex;
    private String orderName;
    private String order;
}
