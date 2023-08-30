package com.ankita.codeonline.controller;

import com.ankita.codeonline.helper.FileHelper;
import com.ankita.codeonline.model.CodeFormData;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@Controller
public class CodeFormController {

    private final FileHelper fileHelper;

    @Autowired
    public CodeFormController(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }

    @GetMapping("/code")
    public String getCodeForm(final Model model) {
        log.info("Inside get method of code form controller");
        model.addAttribute("codeFormData", new CodeFormData());
        return "code";
    }

    @PostMapping("/code")
    public String submitCodeForm(
            @ModelAttribute final CodeFormData codeFormData,
            @NonNull final Model model) {
        log.info("Received CodeFormRequestData = {}", codeFormData);
        final String codeFilePath = fileHelper.writeFileToWorkspace(codeFormData.getCodeBlock());
        log.info("Code file path = {}", codeFilePath);
        codeFormData.setResultBlock("result is processing....");
        model.addAttribute("codeFormData", codeFormData);
        return "code";
    }
}
