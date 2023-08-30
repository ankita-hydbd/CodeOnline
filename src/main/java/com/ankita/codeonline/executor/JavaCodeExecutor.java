package com.ankita.codeonline.executor;

import com.ankita.codeonline.model.ExecutionResult;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
@Component
public class JavaCodeExecutor implements CodeExecutor {

    private static final String JAVA_CMD_FORMAT = "java -cp %s %s";
    private static final String STD_CLASS_NAME = "Main";

    @SneakyThrows
    @Override
    public ExecutionResult execute(Path codeFilePath) {
        final ProcessBuilder exeProcessBuilder =
                new ProcessBuilder("java", "-cp", codeFilePath.getParent().toString(), STD_CLASS_NAME);
        exeProcessBuilder.redirectErrorStream(true);

        Process executionProcess = exeProcessBuilder.start();

        // InputStream is output from the compilation command
        BufferedReader errorReader = new BufferedReader(
                new InputStreamReader(executionProcess.getInputStream()));

        StringBuilder errorValue = new StringBuilder();
        String errorLine;
        while((errorLine = errorReader.readLine()) != null) {
            errorValue.append(errorLine);
        }
        final int exitCode = executionProcess.waitFor();
        log.info("Execution Complete with status = {}, Errors = {}", exitCode, errorValue);

        return ExecutionResult.builder()
                .exitCode(exitCode)
                .error(errorValue.toString())
                .build();
    }

    public static void main(String[] args) {
        String fp = "codeBlock/c2c85c9f-9bc6-4d8a-b977-e412a8fea9a5/Main.java";
        JavaCodeExecutor executor = new JavaCodeExecutor();
        System.out.println(executor.execute(Paths.get(fp)));
    }
}
