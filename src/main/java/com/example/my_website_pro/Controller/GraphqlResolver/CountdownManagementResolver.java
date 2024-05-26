package com.example.my_website_pro.Controller.GraphqlResolver;

import com.example.my_website_pro.Entity.Countdown;
import com.example.my_website_pro.Entity.DTO.CountdownDTO;
import com.example.my_website_pro.Entity.DTO.RequestDTO.CountdownSpecificationDTO;
import com.example.my_website_pro.Service.CountdownService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountdownManagementResolver {
    
    @Autowired
    private CountdownService countdownService;

    @QueryMapping
    public List<Countdown> getListCountdown(@Valid @Argument(name = "specification") CountdownSpecificationDTO specification) {
        return countdownService.getListCountdown(specification);}

    @MutationMapping
    public String insertCountdown(@Valid @Argument(name = "countdown") CountdownDTO countdownInput) {
        return countdownService.insertCountdown(countdownInput).getId();
    }

    @MutationMapping
    public String updateCountdown(@Argument(name = "id") String id, @Valid @Argument(name = "countdown") CountdownDTO countdownInput) {
        return countdownService.updateCountdown(id, countdownInput).getId();
    }

    @MutationMapping
    public String changeStatusCountdown(@Argument(name = "id") String id, @Argument(name = "status") Boolean status) {
        return countdownService.changeStatus(id, status).getId();
    }

    @MutationMapping
    public String deleteCountdown(@Argument(name = "id") String id) {
        return  countdownService.deleteCountdown(id);
    }

}