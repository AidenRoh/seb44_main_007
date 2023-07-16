package com.server.total.mapper;

import com.server.total.dto.TotalDto;
import com.server.total.entity.Total;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-14T17:44:31+0900",
    comments = "version: 1.5.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.1.1.jar, environment: Java 11.0.18 (Oracle Corporation)"
)
@Component
public class TotalMapperImpl implements TotalMapper {

    @Override
    public Total totalPostDtoToTotal(TotalDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Total.TotalBuilder total = Total.builder();

        total.totalIncome( requestBody.getTotalIncome() );
        total.totalOutcome( requestBody.getTotalOutcome() );
        total.goal( requestBody.getGoal() );

        return total.build();
    }

    @Override
    public Total totalPatchDtoToTotal(TotalDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Total.TotalBuilder total = Total.builder();

        total.totalId( requestBody.getTotalId() );
        total.totalIncome( requestBody.getTotalIncome() );
        total.totalOutcome( requestBody.getTotalOutcome() );
        total.goal( requestBody.getGoal() );

        return total.build();
    }

    @Override
    public TotalDto.Response totalToResponseDto(Total total) {
        if ( total == null ) {
            return null;
        }

        TotalDto.Response.ResponseBuilder response = TotalDto.Response.builder();

        if ( total.getTotalId() != null ) {
            response.totalId( total.getTotalId() );
        }
        response.totalIncome( total.getTotalIncome() );
        response.totalOutcome( total.getTotalOutcome() );
        response.goal( total.getGoal() );

        return response.build();
    }
}
