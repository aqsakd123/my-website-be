package com.example.my_website_pro.Service;

import com.example.my_website_pro.Entity.DTO.TransactionDTO;
import com.example.my_website_pro.Entity.Transaction;
import org.springframework.data.domain.Page;
import com.example.my_website_pro.Entity.DTO.RequestDTO.TransactionSpecificationDTO;

import java.util.List;

public interface TransactionService {
    
    TransactionDTO getDetailTransaction(String id);
    
    Transaction insertTransaction(TransactionDTO transaction);
    
    Transaction updateTransaction(String id, TransactionDTO transaction);
    
    String deleteTransaction(String id);
    
    List<TransactionDTO> getListTransaction(TransactionSpecificationDTO specification);
    
}