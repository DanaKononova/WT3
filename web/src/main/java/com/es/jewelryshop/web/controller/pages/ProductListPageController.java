package com.es.jewelryshop.web.controller.pages;

import com.es.core.entity.cart.Cart;
import com.es.core.service.CartService;
import com.es.core.entity.jewelry.sort.SortField;
import com.es.core.entity.jewelry.sort.SortOrder;
import com.es.core.entity.jewelry.Jewelry;
import com.es.core.dao.JewelryDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {

    @Resource
    private JewelryDao jewelryDao;
    @Resource
    private CartService cartService;

    private static final int jewelryS_ON_PAGE = 10;

    @GetMapping
    public String showProductList(@RequestParam(name = "page", required = false) Integer pageNumber,
                                  @RequestParam(name = "sort", required = false) String sortField,
                                  @RequestParam(name = "order", required = false) String sortOrder,
                                  @RequestParam(name = "query", required = false) String query,
                                  Model model) {
        List<Jewelry> jewelries = jewelryDao.findAll(((pageNumber == null ? 1 : pageNumber) - 1) * jewelryS_ON_PAGE, jewelryS_ON_PAGE,
                SortField.getValue(sortField), SortOrder.getValue(sortOrder), query);
        model.addAttribute("jewelries", jewelries);
        Long number = jewelryDao.numberByQuery(query);
        model.addAttribute("numberOfjewelrys", number);
        model.addAttribute("numberOfPages", (number + jewelryS_ON_PAGE - 1) / jewelryS_ON_PAGE);
        return "productList";
    }

    @ModelAttribute("cart")
    public Cart cartOnPage() {
        return cartService.getCart();
    }
}
