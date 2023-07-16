package com.server.fixed.service;

import com.server.advice.BusinessLogicException;
import com.server.advice.ExceptionCode;
import com.server.fixed.entity.Fixed;
import com.server.fixed.repository.FixedRepository;
import com.server.trade.entity.Trade;
import com.server.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FixedService {
    private final FixedRepository fixedRepository;
    private final CustomBeanUtils<Trade> beanUtils;

    public FixedService(FixedRepository fixedRepository, CustomBeanUtils<Trade> beanUtils) {
        this.fixedRepository = fixedRepository;
        this.beanUtils = beanUtils;
    }

    public Fixed createFixed(Fixed fixed) {
        return fixedRepository.save(fixed);
    }


    public Fixed updateFixed(Fixed fixed) {
        Fixed findFixed = findFixed(fixed.getFixedId());
        setFixedInfos(findFixed, fixed);
        return fixedRepository.save(findFixed);
    }

    private void setFixedInfos(Fixed findFixed, Fixed fixed) {
        Optional.ofNullable(fixed.getType())
                .ifPresent(type -> findFixed.setType(type));
        Optional.ofNullable(fixed.getFixedName())
                .ifPresent(fixedName -> findFixed.setFixedName(fixedName));
        Optional.ofNullable(fixed.getAmount())
                .ifPresent(amount -> findFixed.setAmount(amount));
        Optional.ofNullable(fixed.getNote())
                .ifPresent(note -> findFixed.setNote(note));
        Optional.ofNullable(fixed.getDate())
                .ifPresent(date -> findFixed.setDate(date));
        Optional.ofNullable(fixed.getCategory())
                .ifPresent(category -> findFixed.setCategory(category));
    }

    @Transactional(readOnly = true)
    public Fixed findFixed(long fixedId) {
        Fixed findFixed = fixedRepository.findById(fixedId).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.FIXED_NOT_FOUND));
        return findFixed;
    }



    @Transactional(readOnly = true)
    public Page<Fixed> findFixeds(int page, int size) {
        return fixedRepository.findAll(PageRequest.of(page, size, Sort.by("date").descending()));
    } //todo 정렬기준 고려하기

    public void deleteFixed(long fixedId) {
        fixedRepository.deleteById(fixedId);
    }





}