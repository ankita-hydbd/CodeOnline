package com.ankita.codeonline.compiler;

import com.ankita.codeonline.model.CompilationResult;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
@Component
public class JavaCodeCompiler implements CodeCompiler {

    private static final String JAVAC_CMD_FORMAT = "javac %s";

    @SneakyThrows
    @Override
    public CompilationResult compile(final Path codeFilePath) {
        // final String javaCompileCmd = JAVAC_CMD_FORMAT.formatted(codeFilePath.toString());

        ProcessBuilder compileProcessBuilder = new ProcessBuilder("javac", codeFilePath.toString());
        compileProcessBuilder.redirectErrorStream(true);

        Process compilationProcess = compileProcessBuilder.start();
        // InputStream is output from the compilation command
        BufferedReader errorReader = new BufferedReader(
                new InputStreamReader(compilationProcess.getInputStream()));

        StringBuilder errorValue = new StringBuilder();
        String errorLine;
        while((errorLine = errorReader.readLine()) != null) {
            errorValue.append(errorLine);
        }
        final int exitCode = compilationProcess.waitFor();
        log.info("Compilation Exited with {}, Errors = {}", exitCode, errorValue);

        return CompilationResult.builder()
                .exitCode(exitCode)
                .errorMessage(errorValue.toString())
                .build();
    }

    public static void main(String[] args) {
        String fp = "codeBlock/test-directory/Main.java";
        JavaCodeCompiler codeCompiler = new JavaCodeCompiler();
        System.out.println(codeCompiler.compile(Paths.get(fp)));
    }
}
