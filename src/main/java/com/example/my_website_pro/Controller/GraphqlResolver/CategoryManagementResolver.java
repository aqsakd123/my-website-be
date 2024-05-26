package com.example.my_website_pro.Controller.GraphqlResolver;

import com.example.my_website_pro.Entity.DTO.CategoryDTO;

import com.example.my_website_pro.Entity.Category;
import com.example.my_website_pro.Entity.DTO.RequestDTO.CategorySpecificationDTO;
import com.example.my_website_pro.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@RestController
public class CategoryManagementResolver {
    
    @Autowired
    private CategoryService categoryService;

    @PostMapping("api/category/list")
    public List<CategoryDTO> getListCategory(@Valid @RequestBody CategorySpecificationDTO specification) {
        return categoryService.getListCategory(specification);
    }

    @MutationMapping
    public String insertCategory(@Valid @Argument(name = "category") CategoryDTO categoryInput) {
        return categoryService.insertCategory(categoryInput).getId();
    }

    @MutationMapping
    public String updateCategory(@Argument(name = "id") String id, @Valid @Argument(name = "category") CategoryDTO categoryInput) {
        return categoryService.updateCategory(id, categoryInput).getId();
    }

    @MutationMapping
    public String deleteCategory(@Argument(name = "id") String id) {
        return categoryService.deleteCategory(id);
    }

}