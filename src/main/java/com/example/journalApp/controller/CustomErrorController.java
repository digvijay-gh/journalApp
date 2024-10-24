package com.example.journalApp.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Hidden
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public String handleError(HttpServletRequest request) {
        // Retrieve the error status code
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            // Check if the error is a 404 Not Found
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "The endpoint you are looking for does not exist. Please visit the Swagger documentation at digvijay.software/docs";
            }
            return "An error occurred. HTTP Status Code: " + statusCode;
        }

        // Return a generic error message for other status codes
        return "An unexpected error occurred. Please try again later.";
    }
}
