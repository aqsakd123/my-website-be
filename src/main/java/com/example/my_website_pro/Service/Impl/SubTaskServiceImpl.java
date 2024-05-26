package com.example.my_website_pro.Service.Impl;

import com.example.my_website_pro.Config.AppException;
import com.example.my_website_pro.Entity.DTO.RequestDTO.SubTaskSpecificationDTO;
import com.example.my_website_pro.Entity.DTO.SubTaskDTO;

import com.example.my_website_pro.Entity.DTO.UserDTO;
import com.example.my_website_pro.Entity.SubTask;
import com.example.my_website_pro.Entity.Mapper.SubTaskMapper;

import com.example.my_website_pro.Repository.SubTaskRepository;
import com.example.my_website_pro.Service.SubTaskService;
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
public class SubTaskServiceImpl implements SubTaskService {

    @Autowired
    private SubTaskRepository subTaskRepository;

    private final SubTaskMapper subTaskMapper = Mappers.getMapper(SubTaskMapper.class);

    @Override
    public SubTask insertSubTask(SubTaskDTO subTask) {
        return subTaskRepository.save(subTaskMapper.toEntity(subTask));
    }
    
    @Override
    public SubTask updateSubTask(String id, SubTaskDTO subTask) {
        SubTask value = subTaskRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.NOT_FOUND));
        value.setName(subTask.getName());
        value.setDescription(subTask.getDescription());
        value.setIsFinish(subTask.getIsFinish());
        value.setStartDate(subTask.getStartDate());
        value.setEndDate(subTask.getEndDate());
        value.setEstimate(subTask.getEstimate());
        value.setInnerTasks(subTaskMapper.toListEntities(subTask.getInnerTasks()));
        return subTaskRepository.save(value);

    }
    
    @Override
    public SubTask changeStatus(String id, Boolean status) {
        SubTask saveSubTask = subTaskRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.NOT_FOUND));
        saveSubTask.setIsFinish(status);
        return subTaskRepository.save(saveSubTask);

    }
    
    @Override
    public String deleteSubTask(String id){
        subTaskRepository.deleteById(id);
        return id;
    }
    
    @Override
    public List<SubTask> getListSubTask(SubTaskSpecificationDTO specification) {
        Specification<SubTask> spec = getSpecification(specification);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return subTaskRepository.findAll(spec , sort);

    }
    
    private Specification<SubTask> getSpecification(SubTaskSpecificationDTO specification) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Authentication authen = SecurityContextHolder.getContext().getAuthentication();
            UserDTO user = (UserDTO) authen.getPrincipal();
            predicates.add(criteriaBuilder.equal(root.get("createdBy"), user.getUsername()));

            if (StringUtils.hasLength(specification.getParentTaskListId())){
                predicates.add(criteriaBuilder.equal(root.get("parentTaskList").get("id"), specification.getParentTaskListId()));
            }

            if (specification.getStartDate() != null && specification.getEndDate() != null) {
                LocalDate startDate = specification.getStartDate().toLocalDate();
                LocalDate endDate = specification.getEndDate().toLocalDate();

                // Add predicate to filter by startDate and endDate range
                Predicate startDatePredicate = criteriaBuilder.between(root.get("startDate"), startDate, endDate);
                Predicate endDatePredicate = criteriaBuilder.between(root.get("endDate"), startDate, endDate);
                predicates.add(criteriaBuilder.or(startDatePredicate, endDatePredicate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}