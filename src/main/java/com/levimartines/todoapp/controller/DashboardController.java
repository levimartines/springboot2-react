package com.levimartines.todoapp.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DashboardController {

    private final MessageSource messageSource;

    @GetMapping
    @ApiOperation(value = "Get hello world message")
    public ResponseEntity<String> getHelloWorld() {
        return ResponseEntity.ok(messageSource.getMessage("dashboard.message", null,
            LocaleContextHolder.getLocale()));
    }
}
