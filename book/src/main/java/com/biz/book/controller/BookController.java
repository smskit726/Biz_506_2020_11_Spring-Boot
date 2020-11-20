package com.biz.book.controller;

import com.biz.book.domain.BookVO;
import com.biz.book.service.BookService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/book")
public class BookController {

    @Qualifier("bookServiceV1")
    private final BookService bService;

    public BookController(BookService bService) {
        this.bService = bService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getList(Model model) {

        List<BookVO> bookList = bService.selectAll();
        model.addAttribute("BOOKS",bookList);
        return "book/list";
    }

    @RequestMapping(value = "/view", method= RequestMethod.GET)
    public String view(String id, Model model){
        BookVO bookVO = bService.findById(Long.valueOf(id));
        model.addAttribute("BOOK",bookVO);
        return "book/view";
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") String id){
        bService.delete(Long.valueOf(id));
        return "redirect:/book/list";
    }

}
