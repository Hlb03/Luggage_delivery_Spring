package com.luggage_delivery.controller;
/*
  User: admin
  Cur_date: 28.12.2022
  Cur_time: 17:00
*/

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.luggage_delivery.reports.DeliveryReport;
import com.luggage_delivery.reports.DeliveryReportProvider;
import com.luggage_delivery.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.security.Principal;

@Controller
@RequestMapping("/reports")
public class ManagerReportController {

    private final static Logger LOG = LoggerFactory.getLogger(ManagerReportController.class);

    private final RouteService routeService;
    private final DeliveryReportProvider deliveryReportProvider;

    private final TemplateEngine templateEngine;

    @Autowired
    public ManagerReportController(RouteService routeService,
                                   DeliveryReportProvider deliveryReportProvider,
                                   TemplateEngine templateEngine) {
        this.routeService = routeService;
        this.deliveryReportProvider = deliveryReportProvider;
        this.templateEngine = templateEngine;
    }

    @GetMapping
    public String getReportPage(Principal principal, Model model) {

        model.addAttribute("allRoutes", routeService.findRoutes());
        model.addAttribute("userLogin", principal.getName());
        return "create-report";
    }

    @GetMapping("/view")
    public String createReport(@RequestParam(value = "type") String typeOfReport,
                               @RequestParam(value = "reportField") String reportField,
                               Model model) {

        DeliveryReport deliveryReport = deliveryReportProvider.getReportInstance(typeOfReport);
        model.addAttribute("entities", deliveryReport.createReport(reportField));
        model.addAttribute("name", typeOfReport);
        model.addAttribute("field", reportField);
        LOG.debug("REPORT WAS CREATED");
        return "report";
    }

    @GetMapping("/view/pdf")
    public ResponseEntity<?> createPdf(@RequestParam(value = "type") String reportName,
                                       @RequestParam(value = "field") String field,
                                       HttpServletRequest req, HttpServletResponse resp) {

        DeliveryReport deliveryReport = deliveryReportProvider.getReportInstance(reportName);

        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("name", reportName);
        context.setVariable("field", field);
        context.setVariable("entities", deliveryReport.createReport(field));
        String htmlReport = templateEngine.process("report", context);

        ByteArrayOutputStream target = new ByteArrayOutputStream();

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8080");

        HtmlConverter.convertToPdf(htmlReport, target, converterProperties);

        byte[] bytes = target.toByteArray();

        LOG.debug("PDF REPORT WAS CREATED");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }
}
