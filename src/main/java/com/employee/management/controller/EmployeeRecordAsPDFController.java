package com.employee.management.controller;

import com.employee.management.service.PdfGenerationService;
import com.lowagie.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pdf")
@AllArgsConstructor
public class EmployeeRecordAsPDFController {

    @Autowired
    private final PdfGenerationService pdfGenerationService;

    @Operation(summary = "Generate PDF from Freemarker template")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PDF generated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{name}")
    @Transactional
    public ResponseEntity<byte[]> generatePdf(
            @PathVariable String name

    ) throws DocumentException {


        byte[] pdfBytes = pdfGenerationService.generatePdfFromHtml(name).toByteArray();

        // Return the PDF as a response
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + "employee" + ".pdf\"");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdfBytes);

    }
}
