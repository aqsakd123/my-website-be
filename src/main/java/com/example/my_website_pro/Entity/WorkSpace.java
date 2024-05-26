package com.example.my_website_pro.Entity;

import com.example.my_website_pro.Entity.Common.Authority;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import org.hibernate.annotations.BatchSize;
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
@Table(name = "WORK_SPACE")
public class WorkSpace extends Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 3000)
    private String description;

    @Column(nullable = false)
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @OneToMany(targetEntity = TaskList.class, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 100)
    @JoinColumn(name = "WORK_SPACE_ID", referencedColumnName = "id")
    @OrderBy("created_at asc")
    private List<TaskList> taskList;

}