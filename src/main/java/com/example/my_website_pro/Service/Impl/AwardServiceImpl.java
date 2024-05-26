package com.example.my_website_pro.Service.Impl;

import com.example.my_website_pro.Config.AppException;
import com.example.my_website_pro.Entity.DTO.AwardDTO;

import com.example.my_website_pro.Entity.DTO.RequestDTO.TagSpecificationDTO;
import com.example.my_website_pro.Entity.DTO.UserDTO;
import com.example.my_website_pro.Entity.Award;
import com.example.my_website_pro.Entity.Mapper.AwardMapper;

import com.example.my_website_pro.Entity.Tag;
import com.example.my_website_pro.Repository.AwardRepository;
import com.example.my_website_pro.Service.AwardService;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AwardServiceImpl implements AwardService {

    @Autowired
    private AwardRepository awardRepository;

    private final AwardMapper awardMapper = Mappers.getMapper(AwardMapper.class);

    
    @Override
    public AwardDTO getDetailAward(String id) {
        Award res = awardRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.NOT_FOUND));
        return awardMapper.toDTO(res);
    }
    
    @Override
    public Award insertAward(AwardDTO award) {
        return awardRepository.save(awardMapper.toEntity(award));
    }
    
    @Override
    public Award updateAward(String id, AwardDTO award) {
        Award value = awardRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.NOT_FOUND));
        value.setName(award.getName());
        value.setDescription(award.getDescription());
        value.setAwardPoint(award.getAwardPoint());
        value.setColor(award.getColor());

        return awardRepository.save(value);
    }
    
    @Override
    public String deleteAward(String id){
        awardRepository.deleteById(id);
        return id;
    }
    
    @Override
    public List<AwardDTO> getListAward() {
        Specification<Award> spec = getSpecification();
        return awardMapper.toListDTO(awardRepository.findAll(spec));
    }

    private Specification<Award> getSpecification() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Authentication authen = SecurityContextHolder.getContext().getAuthentication();
            UserDTO user = (UserDTO) authen.getPrincipal();

            predicates.add(criteriaBuilder.equal(root.get("createdBy"), user.getUsername()));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}