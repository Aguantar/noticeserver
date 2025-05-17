package com.noticeserver.noticeserver.repository;

import com.noticeserver.noticeserver.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {  //Entitiy이름, pk의 데이터타입
    // mySQL workbench에 데이터타입 LONG이 안보여서 일단 int로함

    Page<Notice> findByTitleContaining(String searchKeyword, Pageable pageable);


}
