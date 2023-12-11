import com.es.core.service.CartService;
import com.es.core.entity.jewelry.sort.SortField;
import com.es.core.entity.jewelry.sort.SortOrder;
import com.es.core.entity.jewelry.jewelry;
import com.es.core.dao.jewelryDao;
import com.es.jewelryshop.web.controller.pages.ProductListPageController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ProductListPageControllerTest {

    @Mock
    private jewelryDao jewelryDao;

    @Mock
    private CartService cartService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductListPageController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShowProductList() {
        // Mock data
        List<jewelry> jewelrys = new ArrayList<>();
        jewelrys.add(new jewelry()); // Add sample jewelry objects
        when(jewelryDao.findAll(anyInt(), anyInt(), any(), any(), any())).thenReturn(jewelrys);
        when(jewelryDao.numberByQuery(any())).thenReturn(jewelrys.size() * 2L);

        String viewName = controller.showProductList(1, "model", "asc", "query", model);

        verify(jewelryDao).findAll(0, 10, SortField.MODEL, SortOrder.ASC, "query");
        verify(jewelryDao).numberByQuery("query");
        verify(model).addAttribute("jewelrys", jewelrys);
        verify(model).addAttribute("numberOfjewelrys", jewelrys.size() * 2L);
        verify(model).addAttribute("numberOfPages", 1L);
        assertEquals("productList", viewName);
    }

    @Test
    public void testShowProductListWithNullParams() {
        List<jewelry> jewelrys = new ArrayList<>();
        jewelrys.add(new jewelry());
        when(jewelryDao.findAll(anyInt(), anyInt(), any(), any(), any())).thenReturn(jewelrys);
        when(jewelryDao.numberByQuery(any())).thenReturn(jewelrys.size() * 2L);

        String viewName = controller.showProductList(null, null, null, null, model);

        verify(jewelryDao).findAll(0, 10, null, null, null);
        verify(jewelryDao).numberByQuery(null);
        verify(model).addAttribute("jewelrys", jewelrys);
        verify(model).addAttribute("numberOfjewelrys", jewelrys.size() * 2L);
        verify(model).addAttribute("numberOfPages", 1L);
        assertEquals("productList", viewName);
    }
}
