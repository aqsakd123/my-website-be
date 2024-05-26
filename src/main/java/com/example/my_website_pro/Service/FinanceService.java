package com.example.my_website_pro.Service;

import com.example.my_website_pro.Entity.DTO.FinanceDTO;
import com.example.my_website_pro.Entity.Finance;
import org.springframework.data.domain.Page;
import java.util.List;

public interface FinanceService {
    
    FinanceDTO getDetailFinance(String id);
    
    Finance insertFinance(FinanceDTO finance);
    
    Finance updateFinance(String id, FinanceDTO finance);
    
    String deleteFinance(String id);
    
    List<FinanceDTO> getListFinance();
    
}