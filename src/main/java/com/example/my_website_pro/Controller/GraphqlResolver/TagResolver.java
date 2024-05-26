package com.example.my_website_pro.Controller.GraphqlResolver;

import com.example.my_website_pro.Entity.DTO.RequestDTO.TagSpecificationDTO;
import com.example.my_website_pro.Entity.DTO.TagDTO;
import com.example.my_website_pro.Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TagResolver {

    @Autowired
    private TagService tagService;

    @QueryMapping
    public TagDTO getDetailTag(@Argument(name = "id") String id) {
        return tagService.getDetailTag(id);
    }

    @QueryMapping
    public List<TagDTO> getListTag(@Argument(name = "specification") TagSpecificationDTO specification) {
        return tagService.getListTag(specification);
    }

    @QueryMapping
    public Page<TagDTO> getPageTag(@Argument(name = "specification") TagSpecificationDTO specification) {
        return tagService.getPageTag(specification);
    }

    @QueryMapping
    public Integer countTagByFilter(@Argument(name = "specification") TagSpecificationDTO specification) {
        return tagService.countTagByFilter(specification);
    }

    @MutationMapping
    public String insertTag(@Argument(name = "tag") TagDTO tagInput) {
        return tagService.insertTag(tagInput).getId();
    }

    @MutationMapping
    public String updateTag(@Argument(name = "id") String id, @Argument(name = "tag") TagDTO tagInput) {
        return tagService.updateTag(id, tagInput).getId();
    }

    @MutationMapping
    public String changeStatusTag(@Argument(name = "id") String id, @Argument(name = "status") Integer status) {
        return tagService.changeStatus(id, status).getId();
    }

    @MutationMapping
    public String deleteTag(@Argument(name = "id") String id) {
        return tagService.deleteTag(id).getId();
    }

    @MutationMapping
    public void deleteAllTagsByFilter(@Argument(name = "specification") TagSpecificationDTO specification) {
        tagService.deleteAllByFilter(specification);
    }
}
        