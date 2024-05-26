package com.example.my_website_pro.Repository;

import com.example.my_website_pro.Entity.Finance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FinanceRepository extends JpaRepository<Finance, String>, JpaSpecificationExecutor<Finance> {

}