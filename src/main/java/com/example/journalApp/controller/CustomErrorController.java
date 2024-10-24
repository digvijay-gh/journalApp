package com.example.journalApp.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
@Hidden
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")

    @ResponseBody
    public String handleError() {

        return "The endpoint you are looking for does not exist.Please visit the Swagger documentation at digvijay.software/docs";
    }

//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }
}
