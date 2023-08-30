package com.ankita.codeonline.model;

import lombok.Data;

@Data
public class CodeFormData {

    private String codeBlock;
    private String inputBlock;
    private String resultBlock = "Code result will be updated here";
}
