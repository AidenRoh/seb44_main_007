package com.server.fixed.controller;

import com.server.dto.ResponseDto;
import com.server.fixed.dto.FixedDto;
import com.server.fixed.entity.Fixed;
import com.server.fixed.mapper.FixedMapper;
import com.server.fixed.service.FixedService;
import com.server.utils.UriCreator;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("fixeds")
@Validated
public class FixedController {
    private final static String FIXED_URL = "fixeds";
    private final FixedService fixedService;
    private final FixedMapper mapper;

    public FixedController(FixedService fixedService, FixedMapper mapper) {
        this.fixedService = fixedService;
        this.mapper = mapper;
    }


    @PostMapping("/memberId")
    public ResponseEntity postFixed(@PathVariable("memberId") @Positive Long memberId,
                                    @Valid @RequestBody FixedDto.Post requestBody) {
        requestBody.setMemberId(memberId);
        Fixed fixed = mapper.fixedPostDtoToFixed(requestBody);
        Fixed createFixed = fixedService.createFixed(fixed);
        URI location = UriCreator.createUri(FIXED_URL, fixed.getFixedId());
        return new ResponseEntity<>(FixedDto.Response.response(fixed), HttpStatus.CREATED);
    }

    @PutMapping("/{fixedId}")
    public ResponseEntity putFixed(@PathVariable("fixedId") @Positive long fixedId,
                                   @Valid @RequestBody FixedDto.Put requestBody) {
        Fixed fixed = fixedService.updateFixed(mapper.fixedPutDtoToFixed(requestBody.addFixed(fixedId)));
        return new ResponseEntity(new ResponseDto.SingleResponseDto<>(mapper.fixedToResponseDto(fixed)),
                HttpStatus.OK);
    }

    @GetMapping("/{fixedId}")
    public ResponseEntity getFixed(@PathVariable("fixedId") @Positive long fixedId) {
        Fixed fixed = fixedService.findFixed(fixedId);
        return new ResponseEntity<>(new ResponseDto.SingleResponseDto<>(FixedDto.Response.response(fixed)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getFixedAll(@Positive @RequestParam(defaultValue = "1") int page,
                                         @Positive @RequestParam(defaultValue = "10") int size){
        Page<Fixed> pageInfo = fixedService.findFixeds(page -1, size);
        List<Fixed> fixedList = pageInfo.getContent();
        List<FixedDto.ListElement> fixedInfoList = FixedDto.getList(fixedList);
        return new ResponseEntity<>(new ResponseDto.MultiResponseDto<>(fixedInfoList, pageInfo), HttpStatus.OK);
    }

    @DeleteMapping("/{fixedId}")
    public ResponseEntity deleteFixed(@PathVariable("fixedId") @Positive long fixedId) {
        fixedService.deleteFixed(fixedId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}