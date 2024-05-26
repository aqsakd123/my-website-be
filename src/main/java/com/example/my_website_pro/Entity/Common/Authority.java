package com.example.my_website_pro.Entity.Common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Data
@MappedSuperclass
@SuperBuilder
public class Authority {

    @Column(name = "CREATED_BY")
    @CreatedBy
    private String createdBy;

    @Column(name = "CREATED_AT")
    @CreatedDate
    private Date createdAt;

    @Column(name = "MODIFIED_AT")
    @LastModifiedDate
    private Date modifiedAt;

    @Column(name = "MODIFIED_BY")
    @LastModifiedBy
    private String modifiedBy;

}
