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
@Table(name = "FINANCE")
public class Finance extends Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false, length = 255)
    private String name;
    
    @Column(nullable = false)
    private Integer initialAmount;
    
    private String color;
    
    private String icon;
    
    @Column(length = 255)
    private String description;
    
    @OneToMany(targetEntity = Transaction.class, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "FINANCE_ID", referencedColumnName = "id")
    @OrderBy("date desc, created_at asc")
    private List<Transaction> transactions;

}