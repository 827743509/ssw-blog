package com.ssw.blog.controller;

import com.ssw.blog.dto.YgoJammingRateParam;
import com.ssw.blog.service.UtilService;
import com.ssw.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/util")
public class UtilController {

    @Autowired
    private UtilService utilService;

    @PostMapping("/getYGOJammingRate")
    public Result<String> getYGOJammingRate(@RequestBody YgoJammingRateParam param) {
        return new Result<>(utilService.getYGOJammingRate(param));
    }
}
