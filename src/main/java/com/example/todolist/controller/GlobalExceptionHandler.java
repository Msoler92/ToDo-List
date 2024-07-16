package com.example.todolist.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_VIEW = "error";
    @ExceptionHandler(PropertyReferenceException.class)
    public ModelAndView invalidUrlParametersHandler(HttpServletRequest req, PropertyReferenceException e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "Property filter mismatch: could not process request.");
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(ERROR_VIEW);
        return mav;
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView methodArgumentTypeMismatchHandler(HttpServletRequest req, MethodArgumentTypeMismatchException e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "Type mismatch: could not process request.");
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(ERROR_VIEW);
        return mav;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView usernameNotFoundHandler(HttpServletRequest req, UsernameNotFoundException e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "Username not found");
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(ERROR_VIEW);
        return mav;
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ModelAndView handlerMethodValidationHandler(HttpServletRequest req, HandlerMethodValidationException e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "Invalid parameters");
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(ERROR_VIEW);
        return mav;
    }
}
