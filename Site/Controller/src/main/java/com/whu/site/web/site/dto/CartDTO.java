package com.whu.site.web.site.dto;

import com.whu.groupon.cart.entity.Cart;
import com.whu.groupon.deal.entity.Deal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 购物车显示
 */
public class CartDTO {

    @Getter @Setter private Cart cart;
    @Getter @Setter private Deal deal;
    @Getter @Setter private Integer subtotal;//小计

    public CartDTO(Cart cart, Deal deal) {
        this.cart = cart;
        this.deal = deal;
        this.subtotal = deal.getDealPrice() * cart.getCount();
    }

}
