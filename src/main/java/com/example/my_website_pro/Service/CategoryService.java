package com.example.my_website_pro.Service;

import com.example.my_website_pro.Entity.DTO.CategoryDTO;
import com.example.my_website_pro.Entity.Category;
import com.example.my_website_pro.Entity.DTO.RequestDTO.CategorySpecificationDTO;
import org.springframework.data.domain.Page;
import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getListCategory(CategorySpecificationDTO specification);

    Category insertCategory(CategoryDTO category);

    Category updateCategory(String id, CategoryDTO category);

    String deleteCategory(String id);
}