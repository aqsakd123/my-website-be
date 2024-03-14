package com.example.my_website_pro.Service.Impl;

import com.example.my_website_pro.Config.AppException;
import com.example.my_website_pro.Entity.DTO.RequestDTO.TaskListSpecificationDTO;
import com.example.my_website_pro.Entity.DTO.TaskListDTO;

import com.example.my_website_pro.Entity.DTO.UserDTO;
import com.example.my_website_pro.Entity.TaskList;
import com.example.my_website_pro.Entity.Mapper.TaskListMapper;

import com.example.my_website_pro.Repository.TaskListRepository;
import com.example.my_website_pro.Service.TaskListService;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Objects;

@Service
@Transactional
public class TaskListServiceImpl implements TaskListService {

    @Autowired
    private TaskListRepository taskListRepository;

    private final TaskListMapper taskListMapper = Mappers.getMapper(TaskListMapper.class);

    @Override
    public TaskList insertTaskList(TaskListDTO taskList) {
        return taskListRepository.save(taskListMapper.toEntity(taskList));
    }
    
    @Override
    public TaskList updateTaskList(String id, TaskListDTO taskList) {
        TaskList value = taskListRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.NOT_FOUND));
        value.setName(taskList.getName());
        value.setDescription(taskList.getDescription());
        value.setIcon(taskList.getIcon());
        value.setPriority(taskList.getPriority());
        return taskListRepository.save(value);
    }
    
    @Override
    public String deleteTaskList(String id){
        taskListRepository.deleteById(id);
        return id;

    }
    
    @Override
    public List<TaskList> getListTaskList(TaskListSpecificationDTO specification) {
        Specification<TaskList> spec = getSpecification(specification);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return taskListRepository.findAll(spec , sort);

    }
    
    private Specification<TaskList> getSpecification(TaskListSpecificationDTO specification) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Authentication authen = SecurityContextHolder.getContext().getAuthentication();
            UserDTO user = (UserDTO) authen.getPrincipal();
            predicates.add(criteriaBuilder.equal(root.get("createdBy"), user.getUsername()));

            if ((!Objects.isNull(specification)) && StringUtils.hasLength(specification.getWorkspaceId())) {
                predicates.add(criteriaBuilder.equal(root.get("workspace").get("id"), specification.getWorkspaceId()));
            } else {
                predicates.add(criteriaBuilder.isNull(root.get("workspace").get("id")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}