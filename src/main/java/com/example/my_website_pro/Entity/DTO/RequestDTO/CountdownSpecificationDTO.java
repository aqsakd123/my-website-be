package com.example.my_website_pro.Entity.DTO.RequestDTO;

import com.example.my_website_pro.Entity.Common.PaggingRequest;
import com.example.my_website_pro.Entity.DTO.SubTaskDTO;
import com.example.my_website_pro.Entity.DTO.TagDTO;
import com.example.my_website_pro.Entity.DTO.WorkSpaceDTO;
import com.example.my_website_pro.Entity.DTO.TaskListDTO;
import lombok.*;

import java.util.Date;
import java.time.LocalDateTime;

 
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CountdownSpecificationDTO  {
    
    private Boolean isFinish;
    
}