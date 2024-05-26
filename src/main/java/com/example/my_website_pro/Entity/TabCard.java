package com.example.my_website_pro.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "TAB_CARD")
public class TabCard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "TAB_NAME", nullable = false)
    private String tabName;

    @Column(name = "TAB_CONTENT", columnDefinition = "TEXT")
    private String tabContent;

    @Column(name = "POSITION")
    private Integer position;

}
