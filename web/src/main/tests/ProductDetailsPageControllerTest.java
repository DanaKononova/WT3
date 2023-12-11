import com.es.core.entity.cart.Cart;
import com.es.core.service.CartService;
import com.es.core.entity.jewelry.jewelry;
import com.es.core.dao.jewelryDao;
import com.es.jewelryshop.web.controller.pages.ProductDetailsPageController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductDetailsPageControllerTest {
    @InjectMocks
    private ProductDetailsPageController productDetailsPageController;

    @Mock
    private jewelryDao jewelryDao;

    @Mock
    private CartService cartService;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShowProductWithValidjewelryId() {
        Long jewelryId = 1L;
        jewelry jewelry = new jewelry();
        when(jewelryDao.get(jewelryId)).thenReturn(Optional.of(jewelry));

        String viewName = productDetailsPageController.showProduct(jewelryId, model);

        assertEquals("productPage", viewName);
        verify(model).addAttribute("jewelry", jewelry);
    }

    @Test
    public void testShowProductWithInvalidjewelryId() {
        Long jewelryId = 1L;
        when(jewelryDao.get(jewelryId)).thenReturn(Optional.empty());

        String viewName = productDetailsPageController.showProduct(jewelryId, model);

        assertEquals("productPage", viewName);
        verify(model).addAttribute("jewelry", null);
    }

    @Test
    public void testCartOnPage() {
        Cart cart = new Cart();
        when(cartService.getCart()).thenReturn(cart);

        Cart result = productDetailsPageController.cartOnPage();

        assertEquals(cart, result);
    }
}