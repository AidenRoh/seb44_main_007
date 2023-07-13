package main.reproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import main.reproject.response.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public class ResponseDto {
    @Getter
    @AllArgsConstructor
    public static class SingleResponseDto<T> {
        private T data;
    }

    @Getter
    public static class MultiResponseDto<T> {
        private List<T> data;
        private PageInfo pageInfo;

        public MultiResponseDto(List<T> data, Page page) {
            this.data = data;
            this.pageInfo = new PageInfo(page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
        }
    }


}