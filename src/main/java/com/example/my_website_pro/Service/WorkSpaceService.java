package com.example.my_website_pro.Service;

import com.example.my_website_pro.Entity.DTO.WorkSpaceDTO;
import com.example.my_website_pro.Entity.WorkSpace;
import org.springframework.data.domain.Page;
import java.util.List;

public interface WorkSpaceService {

    WorkSpace insertWorkSpace(WorkSpaceDTO workSpace);

    WorkSpace updateWorkSpace(String id, WorkSpaceDTO workSpace);

    String deleteWorkSpace(String id);

    List<WorkSpace> getListWorkSpace();
}