package com.example.my_website_pro.Service.Impl;

import com.example.my_website_pro.Config.AppException;
import com.example.my_website_pro.Entity.DTO.WorkSpaceDTO;

import com.example.my_website_pro.Entity.DTO.UserDTO;
import com.example.my_website_pro.Entity.WorkSpace;
import com.example.my_website_pro.Entity.Mapper.WorkSpaceMapper;

import com.example.my_website_pro.Repository.WorkSpaceRepository;
import com.example.my_website_pro.Service.WorkSpaceService;
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
public class WorkSpaceServiceImpl implements WorkSpaceService {

    @Autowired
    private WorkSpaceRepository workSpaceRepository;

    private final WorkSpaceMapper workSpaceMapper = Mappers.getMapper(WorkSpaceMapper.class);

    
    @Override
    public WorkSpace insertWorkSpace(WorkSpaceDTO workSpace) {
        return workSpaceRepository.save(workSpaceMapper.toEntity(workSpace));

    }
    
    @Override
    public WorkSpace updateWorkSpace(String id, WorkSpaceDTO workSpace) {
        WorkSpace value = workSpaceRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.NOT_FOUND));
        value.setName(workSpace.getName());
        value.setDescription(workSpace.getDescription());
        value.setStartDate(workSpace.getStartDate());
        value.setEndDate(workSpace.getEndDate());
        return workSpaceRepository.save(value);

    }
    
    @Override
    public String deleteWorkSpace(String id){
        workSpaceRepository.deleteById(id);
        return id;

    }
    
    @Override
    public List<WorkSpace> getListWorkSpace() {
        Specification<WorkSpace> spec = getSpecification();
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return workSpaceRepository.findAll(spec , sort);

    }
    
    private Specification<WorkSpace> getSpecification() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Authentication authen = SecurityContextHolder.getContext().getAuthentication();
            UserDTO user = (UserDTO) authen.getPrincipal();
            predicates.add(criteriaBuilder.equal(root.get("createdBy"), user.getUsername()));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}