package com.server.wishlist.entity;


import com.server.audit.Auditable;
import com.server.wishlist.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter @Setter
@Entity
public class Wishlist extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    @Column(length = 100, nullable = false)
    private String wishlistName;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column
    private Category category;

    @Column
    private Long memberId;

}

