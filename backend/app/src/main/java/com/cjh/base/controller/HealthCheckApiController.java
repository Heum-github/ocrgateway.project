package com.cjh.claim.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cjh.common.shared.utils.ApiResponseUtils;

@RestController
@RequestMapping("/health")
public class HealthCheckApiController {

    private static final Logger logger = LoggerFactory.getLogger(HealthCheckApiController.class);

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> healthCheck() {

        try{
            logger.info("healthCheck 정상");
            return ApiResponseUtils.createResponse("OK");
        }catch(Exception e){
            return ApiResponseUtils.createErrorResponse(e.getMessage());
        }
    }
}
