package com.ankita.codeonline.compiler;

import com.ankita.codeonline.model.CompilationResult;

import java.nio.file.Path;

public interface CodeCompiler {

    CompilationResult compile(Path codeFilePath);
}
