package com.example.my_website_pro.Controller.GraphqlResolver;

import com.example.my_website_pro.Entity.DTO.WorkSpaceDTO;

import com.example.my_website_pro.Entity.WorkSpace;
import com.example.my_website_pro.Service.WorkSpaceService;
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
public class WorkSpaceResolver {
    
    @Autowired
    private WorkSpaceService workSpaceService;

    @QueryMapping
    public List<WorkSpace> getListWorkSpace() {
        return workSpaceService.getListWorkSpace();}

    @MutationMapping
    public String insertWorkSpace(@Valid @Argument(name = "workSpace") WorkSpaceDTO workSpaceInput) {
        return workSpaceService.insertWorkSpace(workSpaceInput).getId();
    }

    @MutationMapping
    public String updateWorkSpace(@Argument(name = "id") String id, @Valid @Argument(name = "workSpace") WorkSpaceDTO workSpaceInput) {
        return workSpaceService.updateWorkSpace(id, workSpaceInput).getId();
    }

    @MutationMapping
    public String deleteWorkSpace(@Argument(name = "id") String id) {
        return  workSpaceService.deleteWorkSpace(id);
    }

}