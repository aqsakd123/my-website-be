package com.example.my_website_pro.Entity;

import com.example.my_website_pro.Entity.Common.Authority;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "MEMO_ID", referencedColumnName = "id")
    @OrderBy("position asc")
    private List<TabCard> tabCardList;

}
