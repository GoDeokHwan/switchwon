package com.example.switchwonapi.model.policy;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Comment(value = "정책(수수료)")
@Getter
@Entity
@Table(name="policy_fees")
public class PolicyFeesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20)
    @Comment(value = "ID")
    private Long id;
    @Column
    @Comment(value = "수수료")
    private double fees;
    @Column
    @Comment(value = "시작일")
    private LocalDateTime startDate;
    @Column
    @Comment(value = "종료일")
    private LocalDateTime endDate;
    @Column
    @CreationTimestamp
    @Comment(value = "생성일")
    private LocalDateTime createDate;
    @Column
    @UpdateTimestamp
    @Comment(value = "수정일")
    private LocalDateTime modifyDate;
}
