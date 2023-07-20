package com.server.wishlist.dto;

import com.server.wishlist.entity.Wishlist;
import com.server.wishlist.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class WishlistDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        private Long memberId;
        @NotBlank
        private String wishlistName;

        private BigDecimal price;

        private Category category;

        private Integer priority;


        public void setMemberId(Long memberId) {
            this.memberId = memberId;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Patch{

        private Long wishlistId;

        private String wishlistName;

        private BigDecimal price;

        private Category category;

        private Integer priority;



        public Patch addwishlistId(Long wishlistId) {
            Assert.notNull(wishlistId, "wishlist Id must not be Null");
            this.wishlistId = wishlistId;
            return this;
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long wishlistId;
        private String wishlistName;
        private BigDecimal price;
        private Category category;
        private Integer priority;


        public static Response response(Wishlist wishlist) {
            return Response.builder()
                    .wishlistId(wishlist.getWishlistId())
                    .wishlistName(wishlist.getWishlistName())
                    .price(wishlist.getPrice())
                    .category(wishlist.getCategory())
                    .priority(wishlist.getPriority())
                    .build();
        }
    }



}
