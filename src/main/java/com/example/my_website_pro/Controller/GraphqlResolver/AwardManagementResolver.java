package com.example.my_website_pro.Controller.GraphqlResolver;

import com.example.my_website_pro.Entity.DTO.AwardDTO;

import com.example.my_website_pro.Entity.Award;
import com.example.my_website_pro.Service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;

import java.util.List;

@Controller
public class AwardManagementResolver {
    
    @Autowired
    private AwardService awardService;

    @QueryMapping
    public AwardDTO getDetailAward(@Argument(name = "id") String id) {
        return awardService.getDetailAward(id);
    }

    @QueryMapping
    public List<AwardDTO> getListAward() {
        return awardService.getListAward();
    }

    @MutationMapping
    public String insertAward(@Valid @Argument(name = "award") AwardDTO awardInput) {
        return awardService.insertAward(awardInput).getId();
    }

    @MutationMapping
    public String updateAward(@Argument(name = "id") String id, @Valid @Argument(name = "award") AwardDTO awardInput) {
        return awardService.updateAward(id, awardInput).getId();
    }

    @MutationMapping
    public String deleteAward(@Argument(name = "id") String id) {
        return awardService.deleteAward(id);

    }

}