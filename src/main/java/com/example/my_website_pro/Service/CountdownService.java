package com.example.my_website_pro.Service;

import com.example.my_website_pro.Entity.DTO.CountdownDTO;
import com.example.my_website_pro.Entity.Countdown;
import org.springframework.data.domain.Page;
import com.example.my_website_pro.Entity.DTO.RequestDTO.CountdownSpecificationDTO;

import java.util.List;

public interface CountdownService {

    Countdown insertCountdown(CountdownDTO countdown);

    Countdown updateCountdown(String id, CountdownDTO countdown);

    String deleteCountdown(String id);

    List<Countdown> getListCountdown(CountdownSpecificationDTO specification);

    Countdown changeStatus(String id, Boolean status);

}