package com.server.fixed.mapper;

import com.server.fixed.dto.FixedDto;
import com.server.fixed.entity.Fixed;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-17T15:43:53+0900",
    comments = "version: 1.5.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.1.1.jar, environment: Java 11.0.18 (Oracle Corporation)"
)
@Component
public class FixedMapperImpl implements FixedMapper {

    @Override
    public Fixed fixedPostDtoToFixed(FixedDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Fixed.FixedBuilder fixed = Fixed.builder();

        fixed.fixedName( requestBody.getFixedName() );
        fixed.type( requestBody.getType() );
        fixed.amount( requestBody.getAmount() );
        fixed.note( requestBody.getNote() );
        fixed.date( requestBody.getDate() );
        fixed.category( requestBody.getCategory() );
        fixed.memberId( requestBody.getMemberId() );

        return fixed.build();
    }

    @Override
    public Fixed fixedPutDtoToFixed(FixedDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Fixed.FixedBuilder fixed = Fixed.builder();

        fixed.fixedId( requestBody.getFixedId() );
        fixed.fixedName( requestBody.getFixedName() );
        fixed.type( requestBody.getType() );
        fixed.amount( requestBody.getAmount() );
        fixed.note( requestBody.getNote() );
        fixed.date( requestBody.getDate() );
        fixed.category( requestBody.getCategory() );

        return fixed.build();
    }

    @Override
    public FixedDto.Response fixedToResponseDto(Fixed fixed) {
        if ( fixed == null ) {
            return null;
        }

        FixedDto.Response.ResponseBuilder response = FixedDto.Response.builder();

        if ( fixed.getFixedId() != null ) {
            response.fixedId( fixed.getFixedId() );
        }
        response.type( fixed.getType() );
        response.fixedName( fixed.getFixedName() );
        response.amount( fixed.getAmount() );
        response.note( fixed.getNote() );
        response.date( fixed.getDate() );
        response.category( fixed.getCategory() );

        return response.build();
    }

    @Override
    public List<FixedDto.Response> fixedsToResponseDtos(List<Fixed> fixedList) {
        if ( fixedList == null ) {
            return null;
        }

        List<FixedDto.Response> list = new ArrayList<FixedDto.Response>( fixedList.size() );
        for ( Fixed fixed : fixedList ) {
            list.add( fixedToResponseDto( fixed ) );
        }

        return list;
    }
}
