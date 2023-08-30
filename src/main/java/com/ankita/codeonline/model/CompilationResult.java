package com.ankita.codeonline.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompilationResult {

    private int exitCode;
    private String errorMessage;
}
