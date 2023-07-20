package com.server.fixed.controller;

import com.server.dto.ResponseDto;
import com.server.fixed.dto.FixedDto;
import com.server.fixed.entity.Fixed;
import com.server.fixed.mapper.FixedMapper;
import com.server.fixed.service.FixedService;
import com.server.utils.UriCreator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("fixeds")
@Validated
@CrossOrigin
public class FixedController {
    private final static String FIXED_URL = "fixeds";
    private final FixedService fixedService;
    private final FixedMapper mapper;

    public FixedController(FixedService fixedService, FixedMapper mapper) {
        this.fixedService = fixedService;
        this.mapper = mapper;
    }


    @PostMapping("/{memberId}")
    public ResponseEntity postFixed(@PathVariable("memberId") @Positive Long memberId,
                                    @Valid @RequestBody FixedDto.Post requestBody) {
        if (requestBody == null) {
            throw new IllegalArgumentException("Request body cannot be null.");
        }

        requestBody.setMemberId(memberId);
        Fixed fixed = mapper.fixedPostDtoToFixed(requestBody);
        Fixed createFixed = fixedService.createFixed(fixed);
        URI location = UriCreator.createUri(FIXED_URL, fixed.getFixedId());
        return new ResponseEntity<>(FixedDto.Response.response(fixed), HttpStatus.CREATED);
    }

    @PatchMapping("/{fixedId}/{memberId}")
    public ResponseEntity patchFixed(@PathVariable("fixedId") @Positive Long fixedId,
                                   @PathVariable("memberId") @Positive Long memberId,
                                   @Valid @RequestBody FixedDto.Patch requestBody) {
        Fixed fixed = fixedService.updateFixed(mapper.fixedPutDtoToFixed(requestBody.addFixedId(fixedId)), memberId);
        return new ResponseEntity(new ResponseDto.SingleResponseDto<>(mapper.fixedToResponseDto(fixed)),
                HttpStatus.OK);
    }

    @GetMapping("/{fixedId}")
    public ResponseEntity getFixed(@PathVariable("fixedId") @Positive Long fixedId) {
        Fixed fixed = fixedService.findFixed(fixedId);
        return new ResponseEntity<>(new ResponseDto.SingleResponseDto<>(FixedDto.Response.response(fixed)), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<?> getFixeds(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<Fixed> fixeds = fixedService.findFixeds(startDate, endDate);

        if (fixeds.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(fixeds, HttpStatus.OK);
        }

    }


    @DeleteMapping("/{fixedId}")
    public ResponseEntity deleteFixed(@PathVariable("fixedId") @Positive Long fixedId) {
        fixedService.deleteFixed(fixedId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}