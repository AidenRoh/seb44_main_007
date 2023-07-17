package com.server.fixed.mapper;

import com.server.fixed.dto.FixedDto;
import com.server.fixed.entity.Fixed;
import org.mapstruct.Mapper;
import java.util.List;


@Mapper(componentModel = "spring")
public interface FixedMapper {

    Fixed fixedPostDtoToFixed(FixedDto.Post requestBody);
    Fixed fixedPutDtoToFixed(FixedDto.Patch requestBody);
    FixedDto.Response fixedToResponseDto(Fixed fixed);
    List<FixedDto.Response> fixedsToResponseDtos(List<Fixed> fixedList);



}