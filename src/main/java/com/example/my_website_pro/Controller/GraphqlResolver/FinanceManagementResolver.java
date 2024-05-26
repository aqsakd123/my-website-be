package com.example.my_website_pro.Controller.GraphqlResolver;

import com.example.my_website_pro.Entity.DTO.FinanceDTO;
import com.example.my_website_pro.Service.FinanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FinanceManagementResolver {
    
    @Autowired
    private FinanceService financeService;

    @QueryMapping
    public FinanceDTO getDetailFinance(@Argument(name = "id") String id) {
        return financeService.getDetailFinance(id);
    }

    @QueryMapping
    public List<FinanceDTO> getListFinance() {
        return financeService.getListFinance();
    }

    @MutationMapping
    public String insertFinance(@Valid @Argument(name = "finance") FinanceDTO financeInput) {
        return financeService.insertFinance(financeInput).getId();
    }

    @MutationMapping
    public String updateFinance(@Argument(name = "id") String id, @Valid @Argument(name = "finance") FinanceDTO financeInput) {
        return financeService.updateFinance(id, financeInput).getId();
    }

    @MutationMapping
    public String deleteFinance(@Argument(name = "id") String id) {
        return financeService.deleteFinance(id);

    }

}