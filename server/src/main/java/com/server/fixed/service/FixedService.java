package com.server.fixed.service;

import com.server.advice.BusinessLogicException;
import com.server.advice.ExceptionCode;
import com.server.fixed.entity.Fixed;
import com.server.fixed.repository.FixedRepository;
import com.server.trade.entity.Trade;
import com.server.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FixedService {
    private final FixedRepository fixedRepository;



    public Fixed createFixed(Fixed fixed) {
        return fixedRepository.save(fixed);
    }


    public Fixed updateFixed(Fixed fixed, Long memberId) {
        Fixed findFixed = findVerifiedFixed(fixed.getFixedId());
        if(!findFixed.getMemberId().equals(memberId)) {
            throw new BusinessLogicException(ExceptionCode.FIXED_MEMBER_NOT_MATCH);
        }
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
        return fixedRepository.save(findFixed);
    }

    @Transactional(readOnly = true)
    public Fixed findFixed(long fixedId) {
        Fixed findFixed = fixedRepository.findById(fixedId).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.FIXED_NOT_FOUND));
        return findFixed;
    }



    @Transactional(readOnly = true)
    public List<Fixed> findFixeds(LocalDate startDate, LocalDate endDate) {
        return fixedRepository.findByDateBetween(startDate, endDate);
    }

    public void deleteFixed(Long fixedId) {
        Fixed findFixed = findFixed(fixedId);
        fixedRepository.deleteById(fixedId);
    }

    // 해당 거래가 있는지 조회
    private Fixed findVerifiedFixed(Long fixedId) {
        Optional<Fixed> optionalFixed = fixedRepository.findById(fixedId);
        if(optionalFixed.isEmpty()){
            throw new BusinessLogicException(ExceptionCode.FIXED_NOT_FOUND);
        }
        return optionalFixed.get();
    }





}