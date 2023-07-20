package com.server.trade.mapper;

import com.server.trade.dto.TradeDto;
import com.server.trade.entity.Trade;
import org.mapstruct.Mapper;
import java.util.List;


@Mapper(componentModel = "spring")
public interface TradeMapper {
    Trade tradePostDtoToTrade(TradeDto.Post requestBody);
    Trade tradePatchDtoToTrade(TradeDto.Patch requestBody);
    TradeDto.Response tradeToResponseDto(Trade trade);
    List<TradeDto.Response> tradesToResponseDtos(List<Trade> trades);


}
