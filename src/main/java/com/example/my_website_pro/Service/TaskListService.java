package com.example.my_website_pro.Service;

import com.example.my_website_pro.Entity.DTO.RequestDTO.TaskListSpecificationDTO;
import com.example.my_website_pro.Entity.DTO.TaskListDTO;
import com.example.my_website_pro.Entity.TaskList;
import org.springframework.data.domain.Page;
import java.util.List;

public interface TaskListService {

    TaskList insertTaskList(TaskListDTO taskList);

    TaskList updateTaskList(String id, TaskListDTO taskList);

    String deleteTaskList(String id);

    List<TaskList> getListTaskList(TaskListSpecificationDTO specification);
}