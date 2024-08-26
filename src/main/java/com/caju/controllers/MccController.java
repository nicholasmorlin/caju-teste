package com.caju.controllers;

import com.caju.controllers.dto.request.MccCreationRequest;
import com.caju.controllers.dto.response.MccResponse;
import com.caju.services.MccService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/mcc")
public class MccController {

    private final MccService mccService;

    public MccController(MccService mccService) {
        this.mccService = mccService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createMcc(@RequestBody MccCreationRequest request) {
        mccService.createMcc(request);
    }

    @GetMapping
    public List<MccResponse> getAllMcc() {
        return mccService.findAllMcc();
    }

    @GetMapping("/{code}")
    public MccResponse getMccByCode(@PathVariable String code) {
        return mccService.findMccByCode(code);
    }
}
