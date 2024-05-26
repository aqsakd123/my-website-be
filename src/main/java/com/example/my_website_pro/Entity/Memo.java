package com.example.my_website_pro.Entity;

import com.example.my_website_pro.Entity.Common.Authority;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "MEMO")
public class Memo extends Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE")
    private String type; // memo | study

    @Column(name = "STATUS")
    private Integer status; // 1: active | 0: deleted

    @Column(name = "POSITION")
    private Double position;

    @OneToMany(targetEntity = TabCard.class, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 100)
    @JoinColumn(name = "MEMO_ID", referencedColumnName = "id")
    private List<TabCard> tabCardList;

    @OneToMany(targetEntity = QuesAndAns.class, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 100)
    @JoinColumn(name = "SUB_TASK_ID", referencedColumnName = "id")
    @OrderBy("created_at asc")
    private List<QuesAndAns> qaList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "memo_tag",
            joinColumns = @JoinColumn(name = "memo_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<Tag> tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

}
