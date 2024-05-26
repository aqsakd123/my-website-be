package com.example.my_website_pro.Controller;

import com.example.my_website_pro.Entity.DTO.MemoDTO;
import com.example.my_website_pro.Entity.DTO.RequestDTO.MemoSpecificationDTO;
import com.example.my_website_pro.Entity.Memo;
import com.example.my_website_pro.Service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class MemoController {

    @Autowired
    MemoService memoService;

    @GetMapping("/{id}")
    public MemoDTO getDetailMemo(@PathVariable String id) {
        return memoService.getDetailMemo(id);
    }

    @PostMapping
    public void insertMemo(@RequestBody MemoDTO memo) {
        memoService.insertMemo(memo);
    }

    @PutMapping("/{id}")
    public void updateMemo(@PathVariable String id, @RequestBody MemoDTO memo) {
        memoService.updateMemo(id, memo);
    }

    @DeleteMapping("/{id}")
    public void deleteMemo(@PathVariable String id) {
        memoService.deleteMemo(id);
    }

    @PostMapping("/list")
    public List<Memo> getListMemo(@RequestBody MemoSpecificationDTO specification) {
        return memoService.getListMemo(specification);
    }

    @PostMapping("/page")
    public Page<MemoDTO> getPageMemo(@RequestBody MemoSpecificationDTO specification) {
        return memoService.getPageMemo(specification);
    }

}
