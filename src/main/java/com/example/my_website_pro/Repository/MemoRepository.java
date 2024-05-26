package com.example.my_website_pro.Repository;

import com.example.my_website_pro.Entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemoRepository extends JpaRepository<Memo, String>, JpaSpecificationExecutor<Memo> {
}
