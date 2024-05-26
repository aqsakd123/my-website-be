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
@Table(name = "COUNTDOWN")
public class Countdown extends Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 2500)
    private String description;

    private String priority;

    private Boolean isFinish;

    @Column(nullable = false)
    private LocalDateTime endDate;

    private String selectionType;

    @OneToMany(targetEntity = SubTask.class, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 100)
    @JoinColumn(name = "COUNTDOWN_ID", referencedColumnName = "id")
    @OrderBy("display_order asc")
    private List<SubTask> innerSubTasks;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Countdown_Tag",
            joinColumns = @JoinColumn(name = "Countdown_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "Tag_id", referencedColumnName = "id")
    )
    @Fetch(FetchMode.JOIN)
    private List<Tag> tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKSPACE_ID")
    private WorkSpace workspaces;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASKLIST_ID")
    private TaskList taskLists;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBTASK_ID")
    private SubTask subTask;

}