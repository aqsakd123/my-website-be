package com.example.my_website_pro.Service;

import com.example.my_website_pro.Entity.DTO.AwardDTO;
import com.example.my_website_pro.Entity.Award;
import org.springframework.data.domain.Page;
import java.util.List;

public interface AwardService {
    
    AwardDTO getDetailAward(String id);
    
    Award insertAward(AwardDTO award);
    
    Award updateAward(String id, AwardDTO award);
    
    String deleteAward(String id);
    
    List<AwardDTO> getListAward();
    
}