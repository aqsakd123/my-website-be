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
@Table(name = "QUES_AND_ANS")
public class QuesAndAns extends Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 300)
    private String question;

    @Column(nullable = false, length = 2500)
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUB_TASK_ID")
    private Memo memo;

}