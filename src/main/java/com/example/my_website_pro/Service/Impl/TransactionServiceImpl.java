package com.example.my_website_pro.Service.Impl;

import com.example.my_website_pro.Config.AppException;
import com.example.my_website_pro.Entity.DTO.TransactionDTO;
import com.example.my_website_pro.Entity.DTO.RequestDTO.TransactionSpecificationDTO;
import com.example.my_website_pro.Entity.DTO.UserDTO;
import com.example.my_website_pro.Entity.Transaction;
import com.example.my_website_pro.Entity.Mapper.TransactionMapper;

import com.example.my_website_pro.Repository.TransactionRepository;
import com.example.my_website_pro.Service.TransactionService;
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

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);

    @Override
    public TransactionDTO getDetailTransaction(String id) {
        Transaction res = transactionRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.NOT_FOUND));
        return transactionMapper.toDTO(res);
    }
    
    @Override
    public Transaction insertTransaction(TransactionDTO transaction) {
        return transactionRepository.save(transactionMapper.toEntity(transaction));
    }
    
    @Override
    public Transaction updateTransaction(String id, TransactionDTO transaction) {
        Transaction value = transactionRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.NOT_FOUND));
        value.setName(transaction.getName());
        value.setDate(transaction.getDate());
        value.setType(transaction.getType());
        value.setSpecificType(transaction.getSpecificType());
        value.setNote(transaction.getNote());
        value.setMoneyAmount(transaction.getMoneyAmount());

        return transactionRepository.save(value);
    }
    
    @Override
    public String deleteTransaction(String id){
        transactionRepository.deleteById(id);
        return id;
    }
    
    @Override
    public List<TransactionDTO> getListTransaction(TransactionSpecificationDTO specification) {
        Specification<Transaction> spec = getSpecification(specification);
        return transactionMapper.toListDTO(transactionRepository.findAll(spec));
    }
    
    private Specification<Transaction> getSpecification(TransactionSpecificationDTO specification) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Authentication authen = SecurityContextHolder.getContext().getAuthentication();
            UserDTO user = (UserDTO) authen.getPrincipal();
            predicates.add(criteriaBuilder.equal(root.get("createdBy"), user.getUsername()));

            if (specification.getDate() != null){
                LocalDate date = specification.getDate().toLocalDate();
                LocalDate startDate = YearMonth.from(date).atDay(1); // First day of the month
                LocalDate endDate = YearMonth.from(date).atEndOfMonth(); // Last day of the month

                predicates.add(criteriaBuilder.between(root.get("date"), startDate, endDate));
            }

            if (StringUtils.hasLength(specification.getType())){
               predicates.add(criteriaBuilder.equal(root.get("type"), specification.getType()));
            }

            if (!Objects.isNull(specification.getFinance())){
                predicates.add(criteriaBuilder.equal(root.get("finance").get("id"), specification.getFinance().getId()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}