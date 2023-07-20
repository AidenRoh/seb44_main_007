package com.server.total.dto;

import com.server.total.entity.Total;
import com.server.trade.entity.Trade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;

public class TotalDto {


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        private BigDecimal totalIncome;
        private BigDecimal totalOutcome;
        private BigDecimal goal;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {
        private Long totalId;
        private BigDecimal totalIncome;
        private BigDecimal totalOutcome;
        private BigDecimal goal;

        public TotalDto.Patch addTotalId(Long totalId) {
            Assert.notNull(totalId, "total id must not be null.");
            this.totalId = totalId;
            return this;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private long totalId;
        private BigDecimal totalIncome;
        private BigDecimal totalOutcome;
        private BigDecimal goal;

        public static Response response(Total total) {
            return Response.builder()
                    .totalId(total.getTotalId())
                    .totalIncome(total.getTotalIncome())
                    .totalOutcome(total.getTotalOutcome())
                    .goal(total.getGoal())
                    .build();
        }
    }


}
