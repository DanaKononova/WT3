package com.es.jewelryshop.web.controller.pages;

import com.es.core.entity.jewelry.JewelryNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(JewelryNotFoundException.class)
    public ModelAndView handleException(JewelryNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("errorPages/notFoundjewelry");
        modelAndView.addObject("errorMessage", exception.getErrorMessage());
        return modelAndView;
    }
}
