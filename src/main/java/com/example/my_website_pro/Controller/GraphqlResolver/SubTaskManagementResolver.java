package com.example.my_website_pro.Controller.GraphqlResolver;

import com.example.my_website_pro.Entity.DTO.RequestDTO.SubTaskSpecificationDTO;
import com.example.my_website_pro.Entity.DTO.SubTaskDTO;

import com.example.my_website_pro.Entity.SubTask;
import com.example.my_website_pro.Service.SubTaskService;
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
public class SubTaskManagementResolver {
    
    @Autowired
    private SubTaskService subTaskService;

    @QueryMapping
    public List<SubTask> getListSubTask(@Valid @Argument(name = "specification") SubTaskSpecificationDTO specification) {
        return subTaskService.getListSubTask(specification);}

    @MutationMapping
    public String insertSubTask(@Valid @Argument(name = "subTask") SubTaskDTO subTaskInput) {
        return subTaskService.insertSubTask(subTaskInput).getId();
    }

    @MutationMapping
    public String updateSubTask(@Argument(name = "id") String id, @Valid @Argument(name = "subTask") SubTaskDTO subTaskInput) {
        return subTaskService.updateSubTask(id, subTaskInput).getId();
    }

    @MutationMapping
    public String changeStatusSubTask(@Argument(name = "id") String id, @Argument(name = "status") Boolean status) {
        return subTaskService.changeStatus(id, status).getId();
    }

    @MutationMapping
    public String deleteSubTask(@Argument(name = "id") String id) {
        return  subTaskService.deleteSubTask(id);
    }

}