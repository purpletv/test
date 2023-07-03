package eStoreProduct.Test;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import eStoreProduct.DAO.admin.EmailConfigDAO;
import eStoreProduct.DAO.admin.OrderValueWiseShippingChargeDAO;
import eStoreProduct.DAO.admin.RegionDAO;
import eStoreProduct.DAO.admin.adminDAO;
import eStoreProduct.controller.admin.adminSettingsController;
import eStoreProduct.model.admin.entities.EmailConfigModel;
import eStoreProduct.model.admin.input.OrderValueWiseShippingChargesInput;
import eStoreProduct.model.admin.input.Regions;
import eStoreProduct.model.admin.output.OrderValueWiseShippingCharge;
import eStoreProduct.model.admin.output.RegionsOutput;

public class AdminSettingsControllerTest {

    @Mock
    private adminDAO adminDao;

    @Mock
    private EmailConfigDAO emailConfigDao;

    @Mock
    private RegionDAO regionDao;

    @Mock
    private OrderValueWiseShippingChargeDAO shippingChargeDao;

    @InjectMocks
    private adminSettingsController controller;

    @Mock
    private Model model;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddRegion() {
        Regions region = new Regions();

        String result = controller.addRegion(region, model);

        verify(regionDao).addRegion(region);
        Assert.assertEquals("DONE", result);
    }

    @Test
    public void testRemoveRegion() {
        String id = "1";

        String result = controller.removeRegion(id, model);

        verify(regionDao).removeRegion(1);
        Assert.assertEquals("DONE", result);
    }

    @Test
    public void testShippingRedirect() {
        List<RegionsOutput> regions = new ArrayList<>();
        when(regionDao.getRegions()).thenReturn(regions);

        String view = controller.shippingRedirect(model);

        verify(regionDao).getRegions();
        verify(model).addAttribute("regionList", regions);
        Assert.assertEquals("regions", view);
    }

    @Test
    public void testEmailConfiguration() {
        EmailConfigModel emailConfig = new EmailConfigModel();

        String result = controller.emailConfiguration(emailConfig, model);

        verify(emailConfigDao).changeEmail(emailConfig);
        Assert.assertEquals("done", result);
    }

    @Test
    public void testReturnPage() {
        String view = controller.returnPage(model);

        Assert.assertEquals("emailConfig", view);
    }

    @Test
    public void testOrderValueWisePage() {
        List<OrderValueWiseShippingCharge> charges = new ArrayList<>();
        when(shippingChargeDao.getAll()).thenReturn(charges);

        String view = controller.orderValueWisePage(model);

        verify(shippingChargeDao).getAll();
        verify(model).addAttribute("chargeList", charges);
        Assert.assertEquals("OrderValueWisePage", view);
    }

    @Test
    public void testUpdateCharge() {
        OrderValueWiseShippingChargesInput input = new OrderValueWiseShippingChargesInput();
        when(shippingChargeDao.updateCharge(any(OrderValueWiseShippingChargesInput.class))).thenReturn(true);

        boolean result = controller.updateCharge(input, model);

        verify(shippingChargeDao).updateCharge(input);
        Assert.assertTrue(result);
    }

    @Test
    public void testAddCharge() {
        OrderValueWiseShippingChargesInput input = new OrderValueWiseShippingChargesInput();
        when(shippingChargeDao.addCharge(any(OrderValueWiseShippingChargesInput.class))).thenReturn(true);

        boolean result = controller.addCharge(input, model);

        verify(shippingChargeDao).addCharge(input);
        Assert.assertTrue(result);
    }
}
