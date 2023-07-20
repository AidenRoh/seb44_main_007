package com.server.fixed.entity;

import com.server.member.entity.Member;
import com.server.trade.entity.Category;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "fixed")
@Builder
public class Fixed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fixedId;
    @Column
    private String fixedName;
    @Column
    private String type;
    @NotNull
    @Column
    private BigDecimal amount;
    @Column
    private String note;
    @Column
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    @Column
    private Category category;
    @Column
    private Long memberId;

    public void setDate(LocalDate date) {
        this.date = date;
    }


}