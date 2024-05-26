package com.example.my_website_pro.Service;

import com.example.my_website_pro.Entity.DTO.MemoDTO;
import com.example.my_website_pro.Entity.DTO.RequestDTO.MemoSpecificationDTO;
import com.example.my_website_pro.Entity.Memo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MemoService {

    MemoDTO getDetailMemo(String id);

    Memo insertMemo(MemoDTO memo);

    Memo updateMemo(String id, MemoDTO memo);

    Memo changeStatus(String id, Integer status);

    Memo deleteMemo(String id);

    List<Memo> getListMemo(MemoSpecificationDTO specification);

    Page<MemoDTO> getPageMemo(MemoSpecificationDTO specification);

    Integer countMemoByFilter(MemoSpecificationDTO specification);

    void deleteAllByFilter(MemoSpecificationDTO specification);

}
