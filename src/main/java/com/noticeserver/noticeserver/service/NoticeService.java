package com.noticeserver.noticeserver.service;

import com.noticeserver.noticeserver.entity.Notice;
import com.noticeserver.noticeserver.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service //서비스니까 서비스 annotation
//이 서비스는 NoticeController에서 이용
public class NoticeService {

    @Autowired //원래 자바문법상 해야할 = new... 과정을 대신 해주는 annotation
    private NoticeRepository noticeRepository;

    //게시글 작성
    public void write(Notice notice) {

        noticeRepository.save(notice);
    }

    //게시글 리스트 처리
    public Page<Notice> noticeList(Pageable pageable) { //list에도 pageble 넣기
        return noticeRepository.findAll(pageable); //findAll을 통해 전부 가져오고 controller에서 분류된 기준으로 출력해줌
    }

    //게시글 검색

    public Page<Notice> noticeList(String searchKeyword, Pageable pageable) {
        return noticeRepository.findByTitleContaining(searchKeyword, pageable);
    }

    //게시글 상세 불러오기
    public Notice noticeView(Integer id) {
        return noticeRepository.findById(id).get();

    }
    //게시글 삭제
    public void noticeDelete(Integer id) {  //id값을 읽고
        noticeRepository.deleteById(id); //그 id값에 해당하는 내용 삭제
    }
}
