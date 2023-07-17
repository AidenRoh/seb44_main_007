package com.server.trade.mapper;

import com.server.trade.dto.TradeDto;
import com.server.trade.entity.Trade;
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
public class TradeMapperImpl implements TradeMapper {

    @Override
    public Trade tradePostDtoToTrade(TradeDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Trade.TradeBuilder trade = Trade.builder();

        trade.type( requestBody.getType() );
        trade.tradeName( requestBody.getTradeName() );
        trade.amount( requestBody.getAmount() );
        trade.note( requestBody.getNote() );
        trade.date( requestBody.getDate() );
        trade.category( requestBody.getCategory() );
        trade.memberId( requestBody.getMemberId() );

        return trade.build();
    }

    @Override
    public Trade tradePatchDtoToTrade(TradeDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Trade.TradeBuilder trade = Trade.builder();

        if ( requestBody.getTradeId() != null ) {
            trade.tradeId( requestBody.getTradeId() );
        }
        trade.type( requestBody.getType() );
        trade.tradeName( requestBody.getTradeName() );
        trade.amount( requestBody.getAmount() );
        trade.note( requestBody.getNote() );
        trade.date( requestBody.getDate() );
        trade.category( requestBody.getCategory() );

        return trade.build();
    }

    @Override
    public TradeDto.Response tradeToResponseDto(Trade trade) {
        if ( trade == null ) {
            return null;
        }

        TradeDto.Response.ResponseBuilder response = TradeDto.Response.builder();

        response.tradeId( trade.getTradeId() );
        response.type( trade.getType() );
        response.tradeName( trade.getTradeName() );
        response.amount( trade.getAmount() );
        response.note( trade.getNote() );
        response.date( trade.getDate() );
        response.category( trade.getCategory() );
        response.memberId( trade.getMemberId() );

        return response.build();
    }

    @Override
    public List<TradeDto.Response> tradesToResponseDtos(List<Trade> trades) {
        if ( trades == null ) {
            return null;
        }

        List<TradeDto.Response> list = new ArrayList<TradeDto.Response>( trades.size() );
        for ( Trade trade : trades ) {
            list.add( tradeToResponseDto( trade ) );
        }

        return list;
    }
}
