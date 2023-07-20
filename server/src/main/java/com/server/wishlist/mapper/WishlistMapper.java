package com.server.wishlist.mapper;

import com.server.wishlist.dto.WishlistDto;
import com.server.wishlist.entity.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING)
public interface WishlistMapper {

    Wishlist wishlistPostDtoToWishlist(WishlistDto.Post requestBody);

    Wishlist wishlistPutDtoToWishlist(WishlistDto.Patch requestBody);

    WishlistDto.Response wishlistToWishlistResponseDto(Wishlist wishlist);

    List<WishlistDto.Response> wishlistsToWishlistResponseDtos(List<Wishlist> wishlists);
}
