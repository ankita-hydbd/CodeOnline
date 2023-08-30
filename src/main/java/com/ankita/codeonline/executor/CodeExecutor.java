package com.ankita.codeonline.executor;

import com.ankita.codeonline.model.ExecutionResult;

import java.nio.file.Path;

public interface CodeExecutor {

    ExecutionResult execute(Path codeFilePath);
}
