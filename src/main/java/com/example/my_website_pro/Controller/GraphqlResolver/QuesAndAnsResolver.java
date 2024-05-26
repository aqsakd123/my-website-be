package com.example.my_website_pro.Controller.GraphqlResolver;

import com.example.my_website_pro.Entity.DTO.QuesAndAnsDTO;

import com.example.my_website_pro.Entity.QuesAndAns;
import com.example.my_website_pro.Service.QuesAndAnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@RestController
public class QuesAndAnsResolver {

    @Autowired
    private QuesAndAnsService quesAndAnsService;

    @MutationMapping
    public String insertQuesAndAns(@Valid @Argument(name = "quesAndAns") QuesAndAnsDTO quesAndAnsInput) {
        return quesAndAnsService.insertQuesAndAns(quesAndAnsInput).getId();
    }

    @MutationMapping
    public String updateQuesAndAns(@Argument(name = "id") String id, @Valid @Argument(name = "quesAndAns") QuesAndAnsDTO quesAndAnsInput) {
        return quesAndAnsService.updateQuesAndAns(id, quesAndAnsInput).getId();
    }

    @MutationMapping
    public String deleteQuesAndAns(@Argument(name = "id") String id) {
        return  quesAndAnsService.deleteQuesAndAns(id);
    }

}