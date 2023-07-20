package com.server.total.service;

import com.server.advice.BusinessLogicException;
import com.server.advice.ExceptionCode;
import com.server.total.entity.Total;
import com.server.total.repository.TotalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TotalService {

    private final TotalRepository totalRepository;

    public TotalService(TotalRepository totalRepository) {
        this.totalRepository = totalRepository;
    }

    public Total createTotal(Total total) {
        Total savedTotal = totalRepository.save(total);
        return savedTotal;

    }

    public Total updateTotal(Total total) {
        Total findTotal = findTotal(total.getTotalId());
        setTotalInfo(findTotal, total);
        return totalRepository.save(findTotal);
    }
    private void setTotalInfo(Total findTotal, Total total){
        Optional.ofNullable(total.getTotalIncome())
                .ifPresent(totalIncome -> findTotal.setTotalIncome(totalIncome));
        Optional.ofNullable(total.getTotalOutcome())
                .ifPresent(totalOutcome -> findTotal.setTotalOutcome(totalOutcome));
        Optional.ofNullable(total.getGoal())
                .ifPresent(goal -> findTotal.setGoal(goal));
    }

    @Transactional(readOnly = true)
    public Total findTotal(long totalId) {
        Total findTotal = totalRepository.findById(totalId).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.TOTAL_NOT_FOUND));
        return findTotal;
    }


}
