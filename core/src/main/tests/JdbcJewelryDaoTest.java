import com.es.core.entity.jewelry.color.Color;
import com.es.core.dao.impl.jewelryDaoImpl;
import com.es.core.entity.jewelry.jewelry;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:resources/context/test-applicationContext.xml")
public class JdbcjewelryDaoTest {

    @Resource
    private jewelryDaoImpl jdbcjewelryDao;

    @Resource
    private DataSource dataSource;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/resources/context/test-applicationContext.xml");
        dataSource = (DataSource) applicationContext.getBean("dataSource");
        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
    }

    @After
    public void tearDown() {
        JdbcTestUtils.dropTables(jdbcTemplate, "colors", "jewelrys");
    }

    @Test
    public void notEmptyDataBaseWhenjewelryDaoTestFindAll() {
              assertFalse(jdbcjewelryDao.findAll(0,5, null,null,null).isEmpty());
    }

    @Test
    public void emptyDataBaseWhenjewelryDaoTestFindAll() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "colors", "jewelrys");
        assertTrue(jdbcjewelryDao.findAll(0, 5,null,null,null).isEmpty());
    }

    @Test
    public void getShouldReturnCorrectjewelryById() {
        jewelry expectedjewelry = getjewelry();
        jewelry actualParameterjewelry = jdbcjewelryDao.get(1000L).get();
        assertEquals(expectedjewelry, actualParameterjewelry);
    }

    @Test
    public void getShouldReturnEmptyOptionalForNonExistingjewelry() {
        Optional<jewelry> jewelry = jdbcjewelryDao.get(9999L);
        assertFalse(jewelry.isPresent());
    }

    private static jewelry getjewelry() {
        jewelry actualParameterjewelry = new jewelry();
        Color color = new Color();
        color.setId(1000L);
        color.setCode("Black");
        Set<Color> colors = new HashSet<>();
        colors.add(color);
        color.setId(1001L);
        color.setCode("White");
        colors.add(color);
        actualParameterjewelry.setId(1000L);
        actualParameterjewelry.setModel("ARCHOS 101 G9");
        actualParameterjewelry.setPrice(null);
        actualParameterjewelry.setBrand("ARCHOS");
        actualParameterjewelry.setImageUrl(null);
        actualParameterjewelry.setColors(colors);
        return actualParameterjewelry;
    }
}
