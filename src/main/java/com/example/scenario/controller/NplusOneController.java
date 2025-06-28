package com.example.scenario.controller;

import com.example.scenario.entity.Member;
import com.example.scenario.service.NplusOneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NplusOneController {

    private final NplusOneService nplusOneService;
    public List<Member> getAllMember(){
        return nplusOneService.getAllMember();
    }
}
