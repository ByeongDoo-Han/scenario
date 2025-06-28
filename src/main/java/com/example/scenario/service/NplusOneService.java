package com.example.scenario.service;

import com.example.scenario.entity.Team;
import com.example.scenario.repository.MemberRepository;
import com.example.scenario.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NplusOneService {

    private final TeamRepository teamRepository;
    
    private final MemberRepository memberRepository;
}