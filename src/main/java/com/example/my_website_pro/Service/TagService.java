package com.example.my_website_pro.Service;

import com.example.my_website_pro.Entity.DTO.TagDTO;
import com.example.my_website_pro.Entity.DTO.RequestDTO.TagSpecificationDTO;
import com.example.my_website_pro.Entity.Tag;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TagService {

    TagDTO getDetailTag(String id);

    Tag insertTag(TagDTO tag);

    Tag updateTag(String id, TagDTO tag);

    Tag changeStatus(String id, Integer status);

    Tag deleteTag(String id);

    List<TagDTO> getListTag(TagSpecificationDTO specification);

    Page<TagDTO> getPageTag(TagSpecificationDTO specification);

    Integer countTagByFilter(TagSpecificationDTO specification);

    void deleteAllByFilter(TagSpecificationDTO specification);

}
