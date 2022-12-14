package com.luggage_delivery.controller.error;
/*
  User: admin
  Cur_date: 31.12.2022
  Cur_time: 12:42
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    private final static Logger LOG = LoggerFactory.getLogger(CustomErrorController.class);


    @GetMapping
    public String processError(HttpServletRequest request) {
        String statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString();

        if (statusCode.equals("403")) {
            LOG.debug("ERROR 403 OCCURRED");
            return "error-pages/403";
        } else if (statusCode.equals("500")) {
            LOG.debug("ERROR 500 OCCURRED");
            return "error-pages/500";
        }

        LOG.debug("ERROR 404 OCCURRED");
        return "error-pages/404";
    }
}
