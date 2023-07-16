package com.server.fixed.entity;

import com.server.member.entity.Member;
import com.server.trade.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "fixed")
public class Fixed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fixedId;
    private String fixedName;
    private String type;
    @NotNull
    private BigDecimal amount;
    private String note;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column
    private Long memberId;

    public void setDate(LocalDate date) {
        this.date = date;
    }


}