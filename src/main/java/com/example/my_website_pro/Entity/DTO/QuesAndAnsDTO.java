package com.example.my_website_pro.Entity.DTO;

import com.example.my_website_pro.Entity.Common.AuthorityDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class QuesAndAnsDTO extends AuthorityDTO {

    private String id;

    @NotNull
    @Size(max=300)
    private String question;

    @NotNull
    @Size(max=2500)
    private String answer;

    private MemoDTO memo;

}