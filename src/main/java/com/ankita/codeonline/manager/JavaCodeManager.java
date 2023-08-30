package com.ankita.codeonline.manager;

import com.ankita.codeonline.compiler.JavaCodeCompiler;
import com.ankita.codeonline.executor.JavaCodeExecutor;
import com.ankita.codeonline.helper.FileHelper;
import com.ankita.codeonline.model.CompilationResult;
import com.ankita.codeonline.model.ExecutionResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
@Component
public class JavaCodeManager implements CodeManager {

    private final FileHelper fileHelper;
    private final JavaCodeCompiler javaCodeCompiler;
    private final JavaCodeExecutor javaCodeExecutor;

    @Autowired
    public JavaCodeManager(
            final FileHelper fileHelper,
            final JavaCodeCompiler javaCodeCompiler,
            final JavaCodeExecutor javaCodeExecutor) {
        this.fileHelper = fileHelper;
        this.javaCodeCompiler = javaCodeCompiler;
        this.javaCodeExecutor = javaCodeExecutor;
    }

    @Override
    public String runCode(final String codeBlock, final String inputBlock) {
        final String codeFilePathStr = storeContent(codeBlock);
        final Path codeFilePath = Paths.get(codeFilePathStr);
        log.debug("Code file path = {}", codeFilePath);

        final CompilationResult compilationResult = javaCodeCompiler.compile(codeFilePath);
        if (compilationResult.getExitCode() != 0) {
            log.error("Compilation failed, skipping execution, error = {}", compilationResult.getErrorMessage());
            return compilationResult.getErrorMessage();
        }

        final ExecutionResult executionResult = javaCodeExecutor.execute(codeFilePath);
        if (executionResult.getExitCode() != 0) {
            log.error("Execution failed, error = {}", executionResult.getError());
            return executionResult.getError();
        }

        return executionResult.getOutput();
    }

    private String storeContent(final String content) {
        final String codeFilePath = fileHelper.writeCodeToWorkspace(content);
        log.info("Code file path = {}", codeFilePath);
        return codeFilePath;
    }
}
