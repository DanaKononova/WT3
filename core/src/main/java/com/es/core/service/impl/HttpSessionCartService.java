package com.es.core.service.impl;

import com.es.core.dao.JewelryDao;
import com.es.core.dao.StockDao;
import com.es.core.entity.cart.Cart;
import com.es.core.entity.cart.CartItem;
import com.es.core.entity.order.OutOfStockException;
import com.es.core.service.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class HttpSessionCartService implements CartService {

    @Resource
    private HttpSession httpSession;
    @Resource
    private JewelryDao jewelryDao;
    @Resource
    private StockDao stockDao;
    private static final String CART_SESSION_ATTRIBUTE = "cart";

    @Override
    public Cart getCart() {
        Cart cart = (Cart) httpSession.getAttribute(CART_SESSION_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute(CART_SESSION_ATTRIBUTE, cart);
        }
        return cart;
    }

    @Override
    public void addjewelry(Long jewelryId, Long quantity) throws OutOfStockException {
        Cart cart = getCart();
        CartItem item = cart.findItemById(jewelryId).orElse(null);
        Long stock = stockDao.availableStock(jewelryId).longValue();
        if (item == null) {
            if (stock - quantity < 0)
                throw new OutOfStockException("Available " + stock, jewelryId);
            item = new CartItem();
            item.setJewelry(jewelryDao.get(jewelryId).orElse(null));
            item.setQuantity(quantity);
            cart.getItems().add(item);
        } else {
            if (stock - (quantity + item.getQuantity()) < 0)
                throw new OutOfStockException("Available " + (stock - item.getQuantity()), jewelryId);
            item.setQuantity(item.getQuantity() + quantity);
        }
        recalculateCart(cart);
    }

    @Override
    public void update(Long jewelryId, Long quantity) throws OutOfStockException {
        Cart cart = getCart();
        Long stock = stockDao.availableStock(jewelryId).longValue();
        if (stock - quantity < 0) {
            throw new OutOfStockException("Available " + stock, jewelryId);
        }
        cart.getItems().stream()
                .filter(item -> Objects.equals(item.getJewelry().getId(), jewelryId))
                .forEach(item -> item.setQuantity(quantity));
        recalculateCart(cart);
    }

    @Override
    public void clear(){
        Cart cart = getCart();
        cart.getItems().clear();
        recalculateCart(cart);
    }
    @Override
    public void remove(Long jewelryId) {
        Cart cart = getCart();
        cart.getItems().removeIf(item -> jewelryId.equals(item.getJewelry().getId()));
        recalculateCart(cart);
    }

    @Override
    public long getTotalQuantity() {
        return getCart().getTotalQuantity();
    }

    @Override
    public BigDecimal getTotalCost() {
        return getCart().getTotalCost();
    }

    public void recalculateCart(Cart cart) {
        cart.setTotalQuantity(cart.getItems().stream()
                .map(CartItem::getQuantity)
                .collect(Collectors.summingLong(q -> q.longValue()))
        );

        cart.setTotalCost(cart.getItems().stream()
                .map(item -> {
                    if (item.getJewelry().getPrice() == null)
                        return BigDecimal.ZERO;
                    else return item.getJewelry().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }
}
