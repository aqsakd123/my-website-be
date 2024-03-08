package com.example.my_website_pro.Controller.GraphqlResolver;

import com.example.my_website_pro.Entity.DTO.TaskListDTO;

import com.example.my_website_pro.Entity.TaskList;
import com.example.my_website_pro.Service.TaskListService;
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
public class TaskListManagementResolver {
    
    @Autowired
    private TaskListService taskListService;

    @QueryMapping
    public List<TaskList> getListTaskList() {
        return taskListService.getListTaskList();}

    @MutationMapping
    public String insertTaskList(@Valid @Argument(name = "taskList") TaskListDTO taskListInput) {
        return taskListService.insertTaskList(taskListInput).getId();
    }

    @MutationMapping
    public String updateTaskList(@Argument(name = "id") String id, @Valid @Argument(name = "taskList") TaskListDTO taskListInput) {
        return taskListService.updateTaskList(id, taskListInput).getId();
    }

    @MutationMapping
    public String deleteTaskList(@Argument(name = "id") String id) {
        return  taskListService.deleteTaskList(id);
    }

}