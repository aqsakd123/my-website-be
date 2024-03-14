package com.example.my_website_pro.Service.Impl;

import com.example.my_website_pro.Config.AppException;
import com.example.my_website_pro.Entity.DTO.QuesAndAnsDTO;
import com.example.my_website_pro.Entity.Mapper.QuesAndAnsMapper;
import com.example.my_website_pro.Entity.QuesAndAns;
import com.example.my_website_pro.Repository.QuesAndAnsRepository;
import com.example.my_website_pro.Service.QuesAndAnsService;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class QuesAndAnsServiceImpl implements QuesAndAnsService {

    @Autowired
    private QuesAndAnsRepository quesAndAnsRepository;

    private final QuesAndAnsMapper quesAndAnsMapper = Mappers.getMapper(QuesAndAnsMapper.class);

    @Override
    public QuesAndAns insertQuesAndAns(QuesAndAnsDTO quesAndAns) {
        return quesAndAnsRepository.save(quesAndAnsMapper.toEntity(quesAndAns));
    }

    @Override
    public QuesAndAns updateQuesAndAns(String id, QuesAndAnsDTO quesAndAns) {
        QuesAndAns value = quesAndAnsRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.NOT_FOUND));
        value.setQuestion(quesAndAns.getQuestion());
        value.setAnswer(quesAndAns.getAnswer());
        return quesAndAnsRepository.save(value);
    }

    @Override
    public String deleteQuesAndAns(String id){
        quesAndAnsRepository.deleteById(id);
        return id;
    }

}