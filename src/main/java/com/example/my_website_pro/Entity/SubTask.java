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
@Table(name = "SUB_TASK")
public class SubTask extends Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 500)
    private String description;

    private Boolean isFinish;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer displayOrder;

    private Integer estimate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_LIST_ID")
    private TaskList parentTaskList;

    @OneToMany(targetEntity = SubTask.class, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 100)
    @JoinColumn(name = "SUB_TASK_ID", referencedColumnName = "id")
    @OrderBy("is_finish asc,created_at desc")
    private List<SubTask> innerTasks;

}
