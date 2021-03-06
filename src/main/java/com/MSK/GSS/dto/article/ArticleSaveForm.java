package com.MSK.GSS.dto.article;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ArticleSaveForm {

    @NotBlank
    private String title;

    @NotBlank
    private String body;

}
