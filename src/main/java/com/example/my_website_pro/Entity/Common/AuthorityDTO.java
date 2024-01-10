package com.example.my_website_pro.Entity.Common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorityDTO {
    private String createdBy;

    private Date createdAt;

    private Date modifiedAt;

    private String modifiedBy;

}
