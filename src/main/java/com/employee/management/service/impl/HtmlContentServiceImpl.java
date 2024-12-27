package com.employee.management.service.impl;

import com.employee.management.service.HtmlContentService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@AllArgsConstructor
public class HtmlContentServiceImpl implements HtmlContentService {
    @Autowired
    private final Configuration freemarkerConfig;


    @Override
    public String generateHtml(String templateName, Map<String, Object> data) {
        try {
            Template template = freemarkerConfig.getTemplate(templateName + ".ftl");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            template.process(data, new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            return outputStream.toString(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process Freemarker template: " + e.getMessage(), e);
        }
    }
}
