package com.example.my_website_pro.Entity;

import com.example.my_website_pro.Entity.Common.Authority;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
import java.util.Date;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "TRANSACTION")
public class Transaction extends Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false, length = 255)
    private String name;
    
    @Column(nullable = false)
    private LocalDateTime date;
    
    @Column(nullable = false)
    private String type;
    
    @Column(nullable = false)
    private String specificType;
    
    @Column(length = 255)
    private String note;

    @Column(nullable = false)
    private Integer moneyAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCE_ID")
    private Finance finance;

}