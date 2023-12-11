package com.es.jewelryshop.web.controller.pages;

import com.es.core.dao.JewelryDao;
import com.es.core.entity.cart.Cart;
import com.es.core.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {

    @Resource
    private JewelryDao jewelryDao;
    @Resource
    private CartService cartService;

    @GetMapping("/{id}")
    public String showProduct(@PathVariable("id") Long jewelryId, Model model) {
        model.addAttribute("jewelry", jewelryDao.get(jewelryId).orElse(null));
        return "productPage";
    }

    @ModelAttribute("cart")
    public Cart cartOnPage() {
        return cartService.getCart();
    }
}
