package com.example.my_website_pro.Entity;

import com.example.my_website_pro.Entity.Common.Authority;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import java.util.List;
import java.util.Date;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "CATEGORY")
public class Category extends Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false, length = 20)
    private String name;

    @Column(length = 500)
    private String description;

    private String type;

    private Boolean isDeleted;

    @OneToMany(targetEntity = Category.class, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 100)
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "id")
    @OrderBy("created_at asc")
    @Where(clause = "is_deleted = false")
    private List<Category> subCategories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category parentCategory;

    @OneToMany(targetEntity = Memo.class, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 100)
    @JoinColumn(name = "MEMO_ID", referencedColumnName = "id")
    private List<Memo> memoList;

}