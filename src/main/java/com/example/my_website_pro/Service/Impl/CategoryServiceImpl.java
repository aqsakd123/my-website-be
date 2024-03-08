package com.example.my_website_pro.Service.Impl;

import com.example.my_website_pro.Config.AppException;
import com.example.my_website_pro.Entity.DTO.CategoryDTO;

import com.example.my_website_pro.Entity.DTO.RequestDTO.CategorySpecificationDTO;
import com.example.my_website_pro.Entity.DTO.UserDTO;
import com.example.my_website_pro.Entity.Category;
import com.example.my_website_pro.Entity.Mapper.CategoryMapper;

import com.example.my_website_pro.Repository.CategoryRepository;
import com.example.my_website_pro.Service.CategoryService;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    @Override
    public List<CategoryDTO> getListCategory(CategorySpecificationDTO specification) {
        Specification<Category> spec = getSpecification(specification);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return categoryMapper.toListDTO(categoryRepository.findAll(spec, sort));
    }

    @Override
    public Category insertCategory(CategoryDTO category) {
        Category saveData = categoryMapper.toEntity(category);
        if (StringUtils.hasLength(category.getParentCategoryId())) {
            Category parent = categoryRepository.findById(category.getParentCategoryId()).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.NOT_FOUND));
            saveData.setParentCategory(parent);
        }
        saveData.setIsDeleted(false);
        return categoryRepository.save(saveData);
    }
    
    @Override
    public Category updateCategory(String id, CategoryDTO category) {
        Category value = categoryRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.NOT_FOUND));
        value.setName(category.getName());
        value.setDescription(category.getDescription());
        return categoryRepository.save(value);
    }
    
    @Override
    public String deleteCategory(String id){
        Category value = categoryRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.NOT_FOUND));
        value.setIsDeleted(true);
        return id;
    }
    
    private Specification<Category> getSpecification(CategorySpecificationDTO specification) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Authentication authen = SecurityContextHolder.getContext().getAuthentication();
            UserDTO user = (UserDTO) authen.getPrincipal();
            predicates.add(criteriaBuilder.equal(root.get("createdBy"), user.getUsername()));

            if (StringUtils.hasLength(specification.getType())){
                predicates.add(criteriaBuilder.equal(root.get("type"), specification.getType()));
            }

            predicates.add(criteriaBuilder.isNull(root.get("parentCategory")));
            predicates.add(criteriaBuilder.equal(root.get("isDeleted"), false));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}