package com.employee.management.service.impl;

import com.employee.management.exception.EmployeeNotFoundException;
import com.employee.management.model.Employee;
import com.employee.management.repo.EmployeeRepo;
import com.employee.management.service.HtmlContentService;
import com.employee.management.service.PdfGenerationService;
import com.lowagie.text.DocumentException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class PdfGenerationServiceImpl implements PdfGenerationService {
    @Autowired
    private final HtmlContentService htmlContentService;
    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    @Transactional
    public ByteArrayOutputStream generatePdfFromHtml(String name) {
        try {
            Employee employee = employeeRepo.findByName(name)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee with name '" + name + "' not found"));

            //byte[] imageBytes = Files.readAllBytes(Paths.get("src/main/resources/EmployeePic.jpg"));
            // Employee employee1 = employeeRepo.findByName(employee.getName()).get();
            //   employee1.setProfilePicture(imageBytes);
            //   employeeRepo.save(employee1);

            // Convert the profile picture to Base64
            String profilePictureUrl = null;
            if (employee.getProfilePicture() != null) {
                String profilePictureBase64 = Base64.getEncoder().encodeToString(employee.getProfilePicture());
                profilePictureUrl = "data:image/jpeg;base64," + profilePictureBase64;
            }

            Map<String, Object> data = new HashMap<>();

            data.put("employee", employee);
            data.put("photoUrl", profilePictureUrl);
            String htmlContent = htmlContentService.generateHtml("employee", data);

            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(pdfOutputStream, false);
            renderer.finishPDF();
            return pdfOutputStream;
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to generate PDF: " + e.getMessage(), e);
        }
    }

}
