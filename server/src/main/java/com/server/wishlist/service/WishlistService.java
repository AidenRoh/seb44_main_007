package com.server.wishlist.service;

import com.server.advice.BusinessLogicException;
import com.server.advice.ExceptionCode;
import com.server.trade.entity.Trade;
import com.server.wishlist.entity.Wishlist;
import com.server.wishlist.repository.WishlistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist createWishlist(Wishlist wishlist) {

        return wishlistRepository.save(wishlist);
    }

    public Wishlist updateWishlist(Wishlist wishlist, Long memberId) {
        Wishlist findWishlist = findVerifiedWishlist(wishlist.getWishlistId());
        if(!findWishlist.getMemberId().equals(memberId)){
            throw new BusinessLogicException(ExceptionCode.WISHLIST_MEMBER_NOT_MATCH);
        }
        Optional.ofNullable(wishlist.getWishlistName())
                .ifPresent(wishlistName -> findWishlist.setWishlistName(wishlistName));
        Optional.ofNullable(wishlist.getPrice())
                .ifPresent(price -> findWishlist.setPrice(price));
        Optional.ofNullable(wishlist.getCategory())
                .ifPresent(category -> findWishlist.setCategory(category));
        return wishlistRepository.save(findWishlist);
    }

    @Transactional(readOnly = true)
    public Wishlist findWishlist(Long wishlistId) {
        Wishlist findWishlist = wishlistRepository.findById(wishlistId).orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.WISHLIST_NOT_FOUND));
        return findWishlist;
    }

    @Transactional(readOnly = true)
    public List<Wishlist> findWishlistsByLatest() {
        return wishlistRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public List<Wishlist> findWishlistsByLowPrice() {
        return wishlistRepository.findByOrderByPriceAsc();
    }


    public void deleteWishlist(Long wishlistId) {
        Wishlist findWishlist = findVerifiedWishlist(wishlistId);
        wishlistRepository.delete(findWishlist);
    }

    // 해당 거래가 있는지 조회
    private Wishlist findVerifiedWishlist(Long wishlistId) {
        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishlistId);
        if(optionalWishlist.isEmpty()){
            throw new BusinessLogicException(ExceptionCode.WISHLIST_NOT_FOUND);
        }
        return optionalWishlist.get();
    }
}
