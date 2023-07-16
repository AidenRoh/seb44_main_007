package com.server.total.entity;

import com.server.member.entity.Member;
import com.server.trade.entity.Trade;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "total")
@Builder
public class Total {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long totalId;
    @Column
    private BigDecimal totalIncome;
    @Column
    private BigDecimal totalOutcome;
    @Column
    private BigDecimal goal;


}