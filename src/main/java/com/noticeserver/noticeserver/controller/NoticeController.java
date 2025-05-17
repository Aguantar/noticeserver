package com.noticeserver.noticeserver.controller;

import com.noticeserver.noticeserver.entity.Notice;
import com.noticeserver.noticeserver.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class NoticeController {

    @Autowired
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/notice/write") //localhost:8080/notice/write로 들어오면

    public String noticeWriterForm() {
        return "noticeWrite"; //noticeWrite.html을 호출
    }
    @PostMapping("/notice/writepro") // html의 form에 들어온 값이 정상적으로 들어오는지 확인
    public String noticeWriterPro(Notice notice) { //notice의 요소들이 들어옴
        noticeService.write(notice);


        return "redirect:/notice/list";
    }
    @GetMapping("/notice/list")
    public String noticeList(Model model,
                             @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
                                     //PageableDefault를 통해 JPA 페이징 작업, 0페이지(1페이지)부터, 10개씩 id로 정렬
        Pageable pageable,
                             @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {

        Page<Notice> list = null;

        if(searchKeyword == null) { //검색이 없을때
            list = noticeService.noticeList(pageable);
        }else{ //검색 할때
            list = noticeService.noticeList(searchKeyword, pageable);
        }


        int nowPage = list.getPageable().getPageNumber()+1; //+1의 이유? 0페이지가 첫번째로 나오기에 +1을 하여 맞춤
        int startPage = Math.max(nowPage -4,1); //시작페이지를 정의했는데, 1페이지에서 -4가될경우. -3이 나오므로
            // 방지를 위해 1을 넣고 max를 사용하여 -4한 값이 1보다 작으면 1이 출력되게 함.
        int endPage = Math.min(nowPage + 5, list.getTotalPages()); 
            //같은 원리로 마지막 페이지인데 +5가 되면 없는 페이지가 나오므로, min을 사용하여 total보다 큰값이 나오면 total로 되게 설정

        model.addAttribute("list", list); //리스트에 값을 담아서 noticelist로 넘김
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        
        return "noticeList";
    }

    @GetMapping("/notice/view")

    public String noticeView(Model model,   @RequestParam("id") Integer id) { //localhost:8080/notice/view?id=1

        model.addAttribute("notice", noticeService.noticeView(id));
        return "noticeView";
    }

    @GetMapping("/notice/delete")
    public String noticeDelete(@RequestParam("id") Integer id) {
        noticeService.noticeDelete(id);
        return "redirect:/notice/list"; //게시글이 삭제될시 notice/list화면으로 리다이렉트(리턴)
    }
    @GetMapping("/notice/modify/{id}") //{id} 부분이
    public String noticeModify(@PathVariable("id") Integer id, Model model) {  //여기 Intefer id로 인식되어 들어옴

        model.addAttribute("notice",noticeService.noticeView(id));
        return "noticeModify";      //pathvariable은 view?id=1 ..같은 형식이 아닌 깔끔하게 /1 이런식으로 들어오게됨
    }

    @PostMapping("/notice/update/{id}")
    public String noticeUpdate(@PathVariable("id") Integer id, Notice notice) {

        Notice noticeTemp = noticeService.noticeView(id); //기존의 글을 찾아야 하기에 이런 형태로 선언 ->가져옴
        noticeTemp.setTitle(notice.getTitle()); //그래서 기존 내용을 덮어씌움
        noticeTemp.setContent(notice.getContent()); //얘도 덮어씌움

        noticeService.write(noticeTemp); //수정된걸 저장함

        return "redirect:/notice/list"; //수정완료되면 list로 리다이렉트
    }
}
