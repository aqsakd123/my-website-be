package com.example.my_website_pro.Service;

import com.example.my_website_pro.Entity.DTO.RequestDTO.SubTaskSpecificationDTO;
import com.example.my_website_pro.Entity.DTO.SubTaskDTO;
import com.example.my_website_pro.Entity.SubTask;
import org.springframework.data.domain.Page;
import java.util.List;

public interface SubTaskService {

    SubTask insertSubTask(SubTaskDTO subTask);

    SubTask updateSubTask(String id, SubTaskDTO subTask);

    String deleteSubTask(String id);

    List<SubTask> getListSubTask(SubTaskSpecificationDTO specification);

    SubTask changeStatus(String id, Boolean status);

}