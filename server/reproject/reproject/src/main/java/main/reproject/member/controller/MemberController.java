package main.reproject.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import main.reproject.member.dto.MemberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@Validated
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody)

}
