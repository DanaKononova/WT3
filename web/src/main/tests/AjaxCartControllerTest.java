import com.es.core.entity.cart.dto.CartAddDto;
import com.es.core.entity.cart.dto.CartItemDto;
import com.es.core.service.CartService;
import com.es.jewelryshop.web.controller.AjaxCartController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AjaxCartControllerTest {

    @Mock
    private CartService cartService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private AjaxCartController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddjewelrySuccess() {
        CartItemDto cartItem = new CartItemDto();
        cartItem.setjewelryId(1L);
        cartItem.setQuantity(2L);
        when(bindingResult.hasErrors()).thenReturn(false);

        CartAddDto result = controller.addjewelry(cartItem, bindingResult);

        verify(cartService).addjewelry(1L, 2L);
        assertEquals("Successfully added to cart", result.getMessage());
        assertEquals(false, result.isErrorStatus());
    }

}
