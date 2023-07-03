package eStoreProduct.Test;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;import org.mockito.Mock;

import org.testng.annotations.Test;

import eStoreProduct.DAO.admin.CategoryDAO;
import eStoreProduct.DAO.common.ProductDAO;
import eStoreProduct.controller.admin.adminMasterEntryController;
import eStoreProduct.DAO.admin.stockSummaryDAO;
import eStoreProduct.model.admin.input.Category;
import eStoreProduct.model.admin.input.Product;
import eStoreProduct.model.admin.output.stockSummaryModel;

public class AdminMasterEntryControllerTest {

    @Mock
    private stockSummaryDAO stockSummaryDao;

    @Mock
    private ProductDAO productDao;

    @Mock
    private CategoryDAO categoryDao;

    @InjectMocks
    private adminMasterEntryController controller;

    @Mock
    private Model model;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new adminMasterEntryController(stockSummaryDao, productDao, categoryDao);
    }

    @Test
    public void testShowProductStocks() {
        List<stockSummaryModel> stocks = new ArrayList<>();
        when(stockSummaryDao.getStocks()).thenReturn(stocks);

        String result = controller.showProductStocks(model);

        verify(stockSummaryDao).getStocks();
        verify(model).addAttribute(eq("stocks1"), eq(stocks));
        Assert.assertEquals(result, "editableStocks");
    }

    @Test
    public void testUpdateStocks() {
        stockSummaryModel ssm = new stockSummaryModel();

        String result = controller.UpdateStocks(ssm, model);

        verify(stockSummaryDao).updateStocks(eq(ssm.getId()), eq(ssm.getImageUrl()), eq(ssm.getHsnCode()),
                eq(ssm.getReorderLevel()), eq(ssm.getStock()), eq(ssm.getMrp()));
        verify(stockSummaryDao).getStocks();
        verify(model).addAttribute(eq("stocks1"), anyList());
        Assert.assertEquals(result, "editableStocks");
    }

    @Test
    public void testAddNewProductInMasterEntryPage() {
        String result = controller.addNewProductInMasterEntryPage(model);

        Assert.assertEquals(result, "addNewProduct");
    }

    @Test
    public void testCreateNewProduct() {
        Product prod = new Product();
        BindingResult bindingResult = mock(BindingResult.class);

        String result = controller.createNewProduct(prod, model);

        verify(productDao).createProduct(eq(prod));
        Assert.assertEquals(result, "AddedProduct");
    }

    @Test
    public void testAddNewCategorytInMasterEntryPage() {
        String result = controller.addNewCategorytInMasterEntryPage(model);

        Assert.assertEquals(result, "addNewCategoryForm");
    }

    @Test
    public void testDisplayCategories() {
        List<Category> categories = new ArrayList<>();
        Category category1 = new Category();
        category1.setPrct_id(1);
        category1.setPrct_title("Category 1");
        categories.add(category1);

        when(productDao.getAllCategories()).thenReturn(categories);

        String htmlContent = controller.displayCategories(model);
        System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"+htmlContent);
        verify(productDao).getAllCategories();
        Assert.assertTrue(htmlContent.contains("<option disabled selected>Select Product category</option>"));
        Assert.assertTrue(htmlContent.contains("<option value='1'>Category 1</option>"));
    }

    @Test
    public void testCreateNewCategory() {
        Category catg = new Category();
        BindingResult bindingResult = mock(BindingResult.class);

        String result = controller.createNewCategory(catg, model);

        verify(categoryDao).addNewCategory(eq(catg));
        Assert.assertEquals(result, "AddedCategory");
    }
}
