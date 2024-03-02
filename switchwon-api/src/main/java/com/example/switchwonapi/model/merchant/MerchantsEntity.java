package com.example.switchwonapi.model.merchant;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Comment(value = "상점 정보")
@Getter
@Entity
@Table(name="merchants")
public class MerchantsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, length = 20)
    @Comment(value = "ID")
    private Long id;
    @Column(length = 150)
    @Comment(value = "상점명")
    private String name;
    @Column
    @CreationTimestamp
    @Comment(value = "생성일")
    private LocalDateTime createDate;
    @Column
    @UpdateTimestamp
    @Comment(value = "수정일")
    private LocalDateTime modifyDate;
}
