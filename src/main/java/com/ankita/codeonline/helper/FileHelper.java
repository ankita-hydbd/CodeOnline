package com.ankita.codeonline.helper;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileHelper {

    private static final String STD_WORKSPACE = "codeBlock";
    private static final String STD_FILE_NAME = "Main.java";
    private static final String FILE_PATH_FORMAT = "%s/%s/%s";

    @SneakyThrows
    public String writeFileToWorkspace(final String codeBlock) {
        final String randomDir = UUID.randomUUID().toString();
        final String filePathStr = FILE_PATH_FORMAT.formatted(STD_WORKSPACE, randomDir, STD_FILE_NAME);
        final Path filePath = Paths.get(filePathStr);
        Files.createDirectories(filePath.getParent());
        final BufferedWriter writer = new BufferedWriter(new FileWriter(filePathStr));
        writer.write(codeBlock);
        writer.close();
        return filePathStr;
    }
}
