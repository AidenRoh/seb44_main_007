package com.server.trade.service;

import com.server.advice.BusinessLogicException;
import com.server.advice.ExceptionCode;
import com.server.trade.entity.Trade;
import com.server.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;




@Service
@Transactional
@RequiredArgsConstructor
public class TradeService {
    private final TradeRepository tradeRepository;



    //    public Trade createTrade(String token, Trade trade) {  //태양님과 고친코드
//
//        Jws<Claims> claims = jwtTokenizer.getClaims(token, jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()));
//        Claims claims1 = claims.getBody();
//        String email = (String) claims1.get("username");
//        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
//        trade.setMember(member);
//        try{return tradeRepository.save(trade);}
//        catch (Exception e) {
//            Throwable cause = e.getCause();
//            if (cause != null) {
//                System.out.println("Cause: " + cause.getMessage());
//                System.out.println(e.getMessage());
//            }
//        }
//        return tradeRepository.save(trade);
//    }




    public Trade createTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    public Trade updateTrade (Trade trade, Long memberId) {
        Trade findTrade = findVerifiedTrade(trade.getTradeId());
        if(!findTrade.getMemberId().equals(memberId)){
            throw new BusinessLogicException(ExceptionCode.TRADE_MEMBER_NOT_MATCH);
        }
        Optional.ofNullable(trade.getType())
                .ifPresent(type -> findTrade.setType(type));
        Optional.ofNullable(trade.getTradeName())
                .ifPresent(tradeName -> findTrade.setTradeName(tradeName));
        Optional.ofNullable(trade.getAmount())
                .ifPresent(amount -> findTrade.setAmount(amount));
        Optional.ofNullable(trade.getNote())
                .ifPresent(note -> findTrade.setNote(note));
        Optional.ofNullable(trade.getDate())
                .ifPresent(date -> findTrade.setDate(date));
        Optional.ofNullable(trade.getCategory())
                .ifPresent(category -> findTrade.setCategory(category));
        return tradeRepository.save(findTrade);
    }


    @Transactional(readOnly = true)
    public Trade findTrade(Long tradeId) {
//        Trade findTrade = tradeRepository.findById(tradeId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.TRADE_NOT_FOUND));
        Trade trade = findVerifiedTrade(tradeId);
        return trade;
    }


    @Transactional(readOnly = true)
    public List<Trade> findTrades(LocalDate startDate, LocalDate endDate) {
        return tradeRepository.findByDateBetween(startDate, endDate);
    }








    public void deleteTrade(Long tradeId) {
        Trade findTrade = findTrade(tradeId);
        tradeRepository.delete(findTrade);
    }

    // 해당 거래가 있는지 조회
    private Trade findVerifiedTrade(Long tradeId) {
        Optional<Trade> optionalTrade = tradeRepository.findById(tradeId);
        if(optionalTrade.isEmpty()){
            throw new BusinessLogicException(ExceptionCode.TRADE_NOT_FOUND);
        }
        return optionalTrade.get();
    }


}
