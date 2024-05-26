package com.example.my_website_pro.Entity.DTO.RequestDTO;

import com.example.my_website_pro.Entity.Common.PaggingRequest;
import com.example.my_website_pro.Entity.DTO.FinanceDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransactionSpecificationDTO extends PaggingRequest {

    private LocalDateTime date;

    private String type;

    // Parent Module
    private FinanceDTO finance;

}