package com.example.my_website_pro.Service.Impl;

import com.example.my_website_pro.Config.AppException;
import com.example.my_website_pro.Entity.DTO.CountdownDTO;
import com.example.my_website_pro.Entity.DTO.RequestDTO.CountdownSpecificationDTO;
import com.example.my_website_pro.Entity.DTO.UserDTO;
import com.example.my_website_pro.Entity.Countdown;
import com.example.my_website_pro.Entity.Mapper.CountdownMapper;
import com.example.my_website_pro.Entity.Mapper.SubTaskMapper;

import com.example.my_website_pro.Entity.Mapper.TaskListMapper;
import com.example.my_website_pro.Entity.Mapper.WorkSpaceMapper;
import com.example.my_website_pro.Repository.CountdownRepository;
import com.example.my_website_pro.Service.CountdownService;
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

@Service
@Transactional
public class CountdownServiceImpl implements CountdownService {

    @Autowired
    private CountdownRepository countdownRepository;

    private final CountdownMapper countdownMapper = Mappers.getMapper(CountdownMapper.class);
    private final SubTaskMapper subtaskMapper = Mappers.getMapper(SubTaskMapper.class);

    private final WorkSpaceMapper workSpaceMapper = Mappers.getMapper(WorkSpaceMapper.class);

    private final TaskListMapper taskListMapper = Mappers.getMapper(TaskListMapper.class);

    @Override
    public Countdown insertCountdown(CountdownDTO countdown) {
        countdown.setIsFinish(false);
        return countdownRepository.save(countdownMapper.toEntity(countdown));
    }
    
    @Override
    public Countdown updateCountdown(String id, CountdownDTO countdown) {
        Countdown value = countdownRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.NOT_FOUND));
        value.setName(countdown.getName());
        value.setDescription(countdown.getDescription());
        value.setPriority(countdown.getPriority());
        value.setIsFinish(countdown.getIsFinish());
        value.setEndDate(countdown.getEndDate());
        value.setSelectionType(countdown.getSelectionType());
        value.setInnerSubTasks(subtaskMapper.toListEntities(countdown.getInnerSubTasks()));
        value.setSubTask(subtaskMapper.toEntity(countdown.getSubTask()));
        value.setWorkspaces(workSpaceMapper.toEntity(countdown.getWorkspaces()));
        value.setTaskLists(taskListMapper.toEntity(countdown.getTaskLists()));

        return countdownRepository.save(value);

    }
    
    @Override
    public Countdown changeStatus(String id, Boolean status) {
        Countdown saveCountdown = countdownRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.NOT_FOUND));
        saveCountdown.setIsFinish(status);
        return countdownRepository.save(saveCountdown);
    }
    
    @Override
    public String deleteCountdown(String id){
        countdownRepository.deleteById(id);
        return id;

    }
    
    @Override
    public List<Countdown> getListCountdown(CountdownSpecificationDTO specification) {
        Specification<Countdown> spec = getSpecification(specification);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return countdownRepository.findAll(spec , sort);

    }
    
    private Specification<Countdown> getSpecification(CountdownSpecificationDTO specification) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Authentication authen = SecurityContextHolder.getContext().getAuthentication();
            UserDTO user = (UserDTO) authen.getPrincipal();
            predicates.add(criteriaBuilder.equal(root.get("createdBy"), user.getUsername()));

            if (specification.getIsFinish() != null){
               predicates.add(criteriaBuilder.equal(root.get("isFinish"), specification.getIsFinish()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}