package com.example.my_website_pro.Repository;

import com.example.my_website_pro.Entity.DTO.CustomDTO.FinanceSumProjection;
import com.example.my_website_pro.Entity.DTO.FinanceDTO;
import com.example.my_website_pro.Entity.Finance;
import com.example.my_website_pro.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String>, JpaSpecificationExecutor<Transaction> {

    @Query("SELECT t.finance.id AS financeId, COALESCE(SUM(t.moneyAmount), 0) AS totalSum " +
            "FROM Transaction t WHERE t.finance IN :finances GROUP BY t.finance.id")
    List<FinanceSumProjection> getTotalMoneyAmountByFinanceIds(List<Finance> finances);

}