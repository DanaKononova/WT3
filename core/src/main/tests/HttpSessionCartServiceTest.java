import com.es.core.entity.cart.Cart;
import com.es.core.entity.cart.CartItem;
import com.es.core.service.impl.HttpSessionCartService;
import com.es.core.entity.jewelry.jewelry;
import com.es.core.dao.jewelryDao;
import com.es.core.dao.StockDao;
import com.es.core.entity.order.OutOfStockException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class HttpSessionCartServiceTest {
    @InjectMocks
    private HttpSessionCartService cartService;

    @Mock
    private jewelryDao jewelryDao;

    @Mock
    private StockDao stockDao;

    private HttpSession httpSession;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        httpSession = new MockHttpSession();
        cartService.setHttpSession(httpSession);
    }

    @Test
    public void testAddjewelryWithNewCartItem() throws OutOfStockException {
        Long jewelryId = 1L;
        Long quantity = 2L;
        Cart cart = cartService.getCart();
        jewelry jewelry = new jewelry();
        when(jewelryDao.get(jewelryId)).thenReturn(Optional.of(jewelry));
        when(stockDao.availableStock(jewelryId)).thenReturn(Math.toIntExact(quantity));

        cartService.addjewelry(jewelryId, quantity);

        assertEquals(1, cart.getItems().size());
        assertEquals(jewelry, cart.getItems().get(0).getjewelry());
        assertEquals(quantity, cart.getItems().get(0).getQuantity());
    }

    @Test
    public void testAddjewelryWithExistingCartItem() throws OutOfStockException {
        Long jewelryId = 1L;
        Long quantity = 2L;
        Cart cart = cartService.getCart();
        jewelry jewelry = new jewelry(1L, null, null, null, null);
        when(jewelryDao.get(jewelryId)).thenReturn(Optional.of(jewelry));
        when(stockDao.availableStock(jewelryId)).thenReturn(5);

        cartService.addjewelry(jewelryId, quantity);
        cartService.addjewelry(jewelryId, quantity);

        assertEquals(1, cart.getItems().size());
        assertEquals(jewelry, cart.getItems().get(0).getjewelry());
        assertEquals(Optional.of(quantity * 2), Optional.ofNullable(cart.getItems().get(0).getQuantity()));
    }

    @Test
    public void testAddjewelryOutOfStock() {
        Long jewelryId = 1L;
        Long quantity = 10L;
        Cart cart = cartService.getCart();
        when(stockDao.availableStock(jewelryId)).thenReturn(5);

        assertThrows(OutOfStockException.class, () -> cartService.addjewelry(jewelryId, quantity));

        assertEquals(0, cart.getItems().size());
    }

    @Test
    public void testUpdateCart() throws OutOfStockException {
        Long jewelryId = 1L;
        Long newQuantity = 3L;
        Cart cart = cartService.getCart();
        CartItem cartItem = new CartItem();
        cartItem.setjewelry(new jewelry(1L, null, null, null, null));
        cartItem.setQuantity(1L);
        cart.getItems().add(cartItem);
        when(stockDao.availableStock(jewelryId)).thenReturn(Math.toIntExact(newQuantity));

        cartService.update(jewelryId, newQuantity);

        assertEquals(1, cart.getItems().size());
        assertEquals(newQuantity, cart.getItems().get(0).getQuantity());
    }

    @Test
    public void testUpdateCartOutOfStock() {
        Long jewelryId = 1L;
        Long newQuantity = 10L;
        Cart cart = cartService.getCart();
        CartItem cartItem = new CartItem();
        cartItem.setjewelry(new jewelry(1L, null, null, null, null));
        cartItem.setQuantity(1L);
        cart.getItems().add(cartItem);
        when(stockDao.availableStock(jewelryId)).thenReturn(5);

        assertThrows(OutOfStockException.class, () -> cartService.update(jewelryId, newQuantity));

        assertEquals(1, cart.getItems().size());
        assertEquals(Optional.of(1L), Optional.of(cart.getItems().get(0).getQuantity()));
    }

    @Test
    public void testRemoveCartItem() {
        Long jewelryId = 1L;
        Cart cart = cartService.getCart();
        CartItem cartItem = new CartItem();
        cartItem.setjewelry(new jewelry(1L, null, null, null, null));
        cartItem.setQuantity(1L);
        cart.getItems().add(cartItem);

        cartService.remove(jewelryId);

        assertEquals(0, cart.getItems().size());
    }

    @Test
    public void testGetTotalQuantity() {
        Cart cart = cartService.getCart();
        CartItem cartItem1 = new CartItem();
        cartItem1.setQuantity(2L);
        jewelry jewelry1 = new jewelry();
        jewelry1.setPrice(BigDecimal.valueOf(100));
        cartItem1.setjewelry(jewelry1);

        CartItem cartItem2 = new CartItem();
        cartItem2.setQuantity(3L);
        jewelry jewelry2 = new jewelry();
        jewelry2.setPrice(BigDecimal.valueOf(50));
        cartItem2.setjewelry(jewelry2);

        cart.getItems().addAll(Arrays.asList(cartItem1, cartItem2));
        cartService.recalculateCart(cart);
        Long totalQuantity = cartService.getTotalQuantity();

        assertEquals(Optional.of(5L), Optional.of(totalQuantity));
    }

    @Test
    public void testGetTotalCost() {
        Cart cart = cartService.getCart();
        CartItem cartItem1 = new CartItem();
        cartItem1.setQuantity(2L);
        jewelry jewelry1 = new jewelry();
        jewelry1.setPrice(BigDecimal.valueOf(100));
        cartItem1.setjewelry(jewelry1);

        CartItem cartItem2 = new CartItem();
        cartItem2.setQuantity(3L);
        jewelry jewelry2 = new jewelry();
        jewelry2.setPrice(BigDecimal.valueOf(50));
        cartItem2.setjewelry(jewelry2);

        cart.getItems().addAll(Arrays.asList(cartItem1, cartItem2));
        cartService.recalculateCart(cart);
        BigDecimal totalCost = cartService.getTotalCost();

        assertEquals(BigDecimal.valueOf(350), totalCost);
    }

    @Test
    public void testRecalculateCartWithNulljewelryPrice() {
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(2L);
        jewelry jewelry = new jewelry();
        jewelry.setPrice(null);
        cartItem.setjewelry(jewelry);
        cart.getItems().add(cartItem);

        cartService.recalculateCart(cart);

        assertEquals(2L, cart.getTotalQuantity());
        assertEquals(BigDecimal.ZERO, cart.getTotalCost());
    }

    @Test
    public void testRecalculateCartWithMixedItems() {
        Cart cart = new Cart();
        CartItem cartItem1 = new CartItem();
        cartItem1.setQuantity(2L);
        jewelry jewelry1 = new jewelry();
        jewelry1.setPrice(BigDecimal.valueOf(100));
        cartItem1.setjewelry(jewelry1);

        CartItem cartItem2 = new CartItem();
        cartItem2.setQuantity(3L);
        jewelry jewelry2 = new jewelry();
        jewelry2.setPrice(BigDecimal.valueOf(50));
        cartItem2.setjewelry(jewelry2);

        cart.getItems().addAll(Arrays.asList(cartItem1, cartItem2));

        cartService.recalculateCart(cart);

        assertEquals(5L, cart.getTotalQuantity());
        assertEquals(BigDecimal.valueOf(350), cart.getTotalCost());
    }

}
