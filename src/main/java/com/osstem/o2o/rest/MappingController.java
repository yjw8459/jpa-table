package com.osstem.o2o.rest;

import com.osstem.o2o.service.MappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MappingController {

    private final MappingService mappingService;

    @GetMapping("/case1")
    public HttpStatus case1() {
        mappingService.case1();
        return HttpStatus.OK;
    }
}
