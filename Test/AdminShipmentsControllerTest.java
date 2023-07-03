package eStoreProduct.Test;

import eStoreProduct.controller.admin.adminShipmentsController;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import eStoreProduct.DAO.admin.orderProductDAO;
import eStoreProduct.DAO.common.OrderDAO;
import eStoreProduct.model.admin.entities.orderModel;
import eStoreProduct.model.admin.input.orderProductInput;
import eStoreProduct.model.admin.output.orderProductsModel;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class AdminShipmentsControllerTest {

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private orderModel orderModel;

    @Mock
    private orderProductDAO orderProductDAO;

    @InjectMocks
    private adminShipmentsController adminShipmentsController;

    @Mock
    private Model model;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);    }

    @Test
    public void testShowProcessedOrders() {
        List<orderModel> orders = new ArrayList<>();
        // Add test order data to the list
        // ...

        when(orderDAO.getAllOrders()).thenReturn(orders);

        String result = adminShipmentsController.showProcessedOrders(model);

        verify(model).addAttribute(eq("orders"), eq(orders));
        assertEquals(result, "shipmentProgressPage");
    }

    @Test
    public void testShowProcessedOrderProducts() {
        int orderId = 1;
        List<orderProductsModel> orderProducts = new ArrayList<>();
        // Add test order products data to the list
        // ...

        when(orderProductDAO.getOrderWiseOrderProducts(orderId)).thenReturn(orderProducts);

        String result = adminShipmentsController.showProcessedOrderProducts(orderId, model);

        verify(model).addAttribute(eq("orderproducts"), eq(orderProducts));
        assertEquals(result, "orderProductsListPage");
    }

    @Test
    public void testUpdateStatusOrderProducts() {
        orderProductInput orderProductInput = new orderProductInput();
        // Set test values for orderProductInput
        // ...

        String result = adminShipmentsController.updateStatusOrderProducts(orderProductInput, model);

        verify(orderProductDAO).updateOrderProductStatus(orderProductInput);
        verify(model).addAttribute(eq("orderproducts"), any(List.class));
        assertEquals(result, "orderProductsListPage");
    }

    @Test
    public void testShowShippedOrders() {
        List<orderModel> orders = new ArrayList<>();
        // Add test order data to the list
        // ...

        when(orderDAO.getAllOrders()).thenReturn(orders);

        String result = adminShipmentsController.showShippedOrders(model);

        verify(model).addAttribute(eq("orders"), eq(orders));
        assertEquals(result, "shipmentsShippedPage");
    }
}
