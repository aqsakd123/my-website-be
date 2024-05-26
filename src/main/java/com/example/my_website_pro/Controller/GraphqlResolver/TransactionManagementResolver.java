package com.example.my_website_pro.Controller.GraphqlResolver;

import com.example.my_website_pro.Entity.DTO.TransactionDTO;
import com.example.my_website_pro.Entity.DTO.RequestDTO.TransactionSpecificationDTO;
import com.example.my_website_pro.Entity.Transaction;
import com.example.my_website_pro.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;

import java.util.List;

@Controller
public class TransactionManagementResolver {
    
    @Autowired
    private TransactionService transactionService;

    @QueryMapping
    public TransactionDTO getDetailTransaction(@Argument(name = "id") String id) {
        return transactionService.getDetailTransaction(id);
    }

    @QueryMapping
    public List<TransactionDTO> getListTransaction(@Valid @Argument(name = "specification") TransactionSpecificationDTO specification) {
        return transactionService.getListTransaction(specification);
    }

    @MutationMapping
    public String insertTransaction(@Valid @Argument(name = "transaction") TransactionDTO transactionInput) {
        return transactionService.insertTransaction(transactionInput).getId();
    }

    @MutationMapping
    public String updateTransaction(@Argument(name = "id") String id, @Valid @Argument(name = "transaction") TransactionDTO transactionInput) {
        return transactionService.updateTransaction(id, transactionInput).getId();
    }

    @MutationMapping
    public String deleteTransaction(@Argument(name = "id") String id) {
        return transactionService.deleteTransaction(id);

    }

}