package com.example.my_website_pro.Service;

import com.example.my_website_pro.Entity.DTO.QuesAndAnsDTO;
import com.example.my_website_pro.Entity.QuesAndAns;
import org.springframework.data.domain.Page;
import java.util.List;

public interface QuesAndAnsService {

    QuesAndAns insertQuesAndAns(QuesAndAnsDTO quesAndAns);

    QuesAndAns updateQuesAndAns(String id, QuesAndAnsDTO quesAndAns);

    String deleteQuesAndAns(String id);
}