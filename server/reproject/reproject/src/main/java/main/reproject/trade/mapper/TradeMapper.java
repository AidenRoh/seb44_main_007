package main.reproject.trade.mapper;

import main.reproject.trade.dto.TradeDto;
import main.reproject.trade.entity.Trade;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TradeMapper {
    Trade tradePostDtoToTrade(TradeDto.Post requestBody);
    Trade tradePutDtoToTrade(TradeDto.Put requestBody);
    TradeDto.Response tradeToResponseDto(Trade trade);
    List<TradeDto.Response> tradesToResponseDtos(List<Trade> trades);
}
