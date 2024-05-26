package com.example.my_website_pro.Service.Impl;

import com.example.my_website_pro.Config.AppException;
import com.example.my_website_pro.Entity.DTO.CustomDTO.FinanceSumProjection;
import com.example.my_website_pro.Entity.DTO.FinanceDTO;

import com.example.my_website_pro.Entity.DTO.UserDTO;
import com.example.my_website_pro.Entity.Finance;
import com.example.my_website_pro.Entity.Mapper.FinanceMapper;

import com.example.my_website_pro.Repository.FinanceRepository;
import com.example.my_website_pro.Repository.TransactionRepository;
import com.example.my_website_pro.Service.FinanceService;
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
import java.util.Map;

@Service
@Transactional
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceRepository financeRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private final FinanceMapper financeMapper = Mappers.getMapper(FinanceMapper.class);

    @Override
    public FinanceDTO getDetailFinance(String id) {
        Finance res = financeRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.NOT_FOUND));
        return financeMapper.toDTO(res);
    }
    
    @Override
    public Finance insertFinance(FinanceDTO finance) {
        return financeRepository.save(financeMapper.toEntity(finance));
    }
    
    @Override
    public Finance updateFinance(String id, FinanceDTO finance) {
        Finance value = financeRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.NOT_FOUND));
        value.setName(finance.getName());
        value.setInitialAmount(finance.getInitialAmount());
        value.setColor(finance.getColor());
        value.setIcon(finance.getIcon());
        value.setDescription(finance.getDescription());

        return financeRepository.save(value);
    }
    
    @Override
    public String deleteFinance(String id){
        financeRepository.deleteById(id);
        return id;
    }
    
    @Override
    public List<FinanceDTO> getListFinance() {
        Specification<Finance> spec = getSpecification();
        List<Finance> value = financeRepository.findAll(spec);
        List<FinanceSumProjection> sumTransaction = transactionRepository.getTotalMoneyAmountByFinanceIds(value);

        List<FinanceDTO> financeDTOs = new ArrayList<>();
        for (Finance finance : value) {
            FinanceDTO financeDTO = financeMapper.toDTO(finance);

            Long totalSum = sumTransaction.stream()
                    .filter(it -> it.getFinanceId().equals(finance.getId()))
                    .map(FinanceSumProjection::getTotalSum)
                    .findFirst()
                    .orElse(0L);

            financeDTO.setTotalSum(totalSum);
            financeDTOs.add(financeDTO);
        }
        return financeDTOs;
    }
    
    private Specification<Finance> getSpecification() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Authentication authen = SecurityContextHolder.getContext().getAuthentication();
            UserDTO user = (UserDTO) authen.getPrincipal();
            predicates.add(criteriaBuilder.equal(root.get("createdBy"), user.getUsername()));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}