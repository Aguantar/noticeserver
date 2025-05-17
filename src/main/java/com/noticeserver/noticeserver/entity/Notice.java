package com.noticeserver.noticeserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity //java에서 테이블을 의미
@Data//loobok으로 데이터 처리

public class Notice { //내가 만든 notice 테이블 ,jpa가 읽음

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id; //테이블에 있는 요소들
    private String title;
    private String content;

}
