package eStoreProduct.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import eStoreProduct.controller.admin.adminController;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class AdminStockControllerTest {

    @Mock
    private eStoreProduct.DAO.admin.stockSummaryDAO stockSummaryDAO;

    @Mock
    private Model model;

    @InjectMocks
    private adminStockController adminStockController;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowStocks() {
        int page = 0;
        int pageSize = 5;
        List<StockSummaryModel> stocks = new ArrayList<>();
        // Add test stock data to the list
        // ...

        when(stockSummaryDAO.getStocks(page, pageSize)).thenReturn(stocks);
        when(stockSummaryDAO.getTotalStocks()).thenReturn(10);

        String result = adminStockController.showStocks(model, page);

        verify(model).addAttribute(eq("stocks"), eq(stocks));
        verify(model).addAttribute(eq("page"), eq(page));
        verify(model).addAttribute(eq("totalPages"), eq(2));
        assertEquals(result, "stockSummary");
    }

    @Test
    public void testShowStocksForPagination() {
        int page = 1;
        int pageSize = 5;
        List<StockSummaryModel> stocks = new ArrayList<>();
        // Add test stock data to the list
        // ...

        when(stockSummaryDAO.getStocks(page, pageSize)).thenReturn(stocks);
        when(stockSummaryDAO.getTotalStocks()).thenReturn(10);

        String result = adminStockController.showStocksForPagination(model, page);

        verify(model).addAttribute(eq("stocks"), eq(stocks));
        verify(model).addAttribute(eq("page"), eq(page));
        verify(model).addAttribute(eq("totalPages"), eq(2));
        assertEquals(result, "stockSummary");
    }
}
