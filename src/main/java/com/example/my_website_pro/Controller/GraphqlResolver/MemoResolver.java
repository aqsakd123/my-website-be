package com.example.my_website_pro.Controller.GraphqlResolver;

import com.example.my_website_pro.Entity.DTO.MemoDTO;
import com.example.my_website_pro.Entity.DTO.RequestDTO.MemoSpecificationDTO;
import com.example.my_website_pro.Entity.Memo;
import com.example.my_website_pro.Service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MemoResolver {

    @Autowired
    private MemoService memoService;

    @QueryMapping
    public MemoDTO getDetailMemo(@Argument(name = "id") String id) {
        return memoService.getDetailMemo(id);
    }

    @QueryMapping
    public List<Memo> getListMemo(@Argument(name = "specification") MemoSpecificationDTO specification) {
        return memoService.getListMemo(specification);
    }

    @QueryMapping
    public Page<MemoDTO> getPageMemo(@Argument(name = "specification") MemoSpecificationDTO specification) {
        return memoService.getPageMemo(specification);
    }

    @QueryMapping
    public Integer countMemoByFilter(@Argument(name = "specification") MemoSpecificationDTO specification) {
        return memoService.countMemoByFilter(specification);
    }

    @MutationMapping
    public String insertMemo(@Argument(name = "memo") MemoDTO memoInput) {
        return memoService.insertMemo(memoInput).getId();
    }

    @MutationMapping
    public String updateMemo(@Argument(name = "id") String id, @Argument(name = "memo") MemoDTO memoInput) {
        return memoService.updateMemo(id, memoInput).getId();
    }

    @MutationMapping
    public String changeStatusMemo(@Argument(name = "id") String id, @Argument(name = "status") Integer status) {
        return memoService.changeStatus(id, status).getId();
    }

    @MutationMapping
    public String deleteMemo(@Argument(name = "id") String id) {
        return memoService.deleteMemo(id).getId();
    }

}
