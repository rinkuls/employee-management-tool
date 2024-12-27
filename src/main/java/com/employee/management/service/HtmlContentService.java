package com.employee.management.service;

import java.util.Map;

public interface HtmlContentService {
    String generateHtml(String templateName, Map<String, Object> data);
}
