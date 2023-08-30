package com.ankita.codeonline.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExecutionResult {

    private int exitCode;
    private String output;
    private String error;
}
