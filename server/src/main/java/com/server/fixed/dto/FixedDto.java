package com.server.fixed.dto;

import com.server.fixed.entity.Fixed;
import com.server.response.PageInfo;
import com.server.trade.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class FixedDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Post {
        private Long memberId;
        private String type; //수입 or 지출
        private String fixedName; //내역
        @NotNull
        private BigDecimal amount; //금액
        private String note; //비고
        private LocalDate date;
        private Category category;

        public void setMemberId(Long memberId) {
            this.memberId = memberId;
        }

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Patch {
        private Long fixedId;
        private String type; //수입 or 지출
        private String fixedName; //내역
        @NotNull
        private BigDecimal amount; //금액
        private String note; //비고
        private LocalDate date;
        private Category category;

        public FixedDto.Patch addFixedId(Long fixedId) {
            Assert.notNull(fixedId, "fixed id must not be null.");
            this.fixedId = fixedId;
            return this;
        }

    }



    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseDto<T> {
        private List<T> data;
        private PageInfo pageInfo;
    }




    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private long fixedId;
        private String type; //수입 or 지출
        private String fixedName; //내역
        private BigDecimal amount; //금액
        private String note; //비고
        private LocalDate date;
        private Category category;


        public static Response response(Fixed fixed) {
            return Response.builder()
                    .fixedId(fixed.getFixedId())
                    .type(fixed.getType())
                    .fixedName(fixed.getFixedName())
                    .amount(fixed.getAmount())
                    .note(fixed.getNote())
                    .date(fixed.getDate())
                    .category(fixed.getCategory())
                    .build();
        }
    }





    @Getter
    @Builder
    @AllArgsConstructor
    public static class fixedInfo {
        private long fixedId;
        private String type; //수입 or 지출
        private String fixedName; //내역
        private BigDecimal amount;
        private String note; //비고
        private LocalDate date;
        private Category category;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ListElement {
        private long fixedId;
        private String type; //수입 or 지출
        private String fixedName; //내역
        private BigDecimal amount;
        private String note; //비고
        private LocalDate date;
        private Category category;
    }

    public static List<ListElement> getList(List<Fixed> fixeds) {
        return fixeds.stream()
                .map(fixed -> ListElement.builder()
                        .fixedId(fixed.getFixedId())
                        .type(fixed.getType())
                        .fixedName(fixed.getFixedName())
                        .amount(fixed.getAmount())
                        .note(fixed.getNote())
                        .date(fixed.getDate())
                        .category(fixed.getCategory())
                        .build()
                )
                .collect(Collectors.toList());
    }


}