package org.codecop.dependencies.parameterise_constructor;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DiscountTest {

    @Test
    public void testCrazySalesDay() {

        MarketingCampaign marketingCampaign = mock(MarketingCampaign.class);
        when(marketingCampaign.isCrazySalesDay()).thenReturn(true);
        Discount discount = new Discount(marketingCampaign);

        Money net = new Money(100);
        Money total = discount.discountFor(net);

        assertEquals(new Money(new BigDecimal("85")), total);
    }

    @Test
    public void testOverOneThousand() {
        MarketingCampaign marketingCampaign = mock(MarketingCampaign.class);
        when(marketingCampaign.isCrazySalesDay()).thenReturn(false);
        Discount discount = new Discount(marketingCampaign);


        Money net = new Money(1002);
        Money total = discount.discountFor(net);

        assertEquals(new Money(new BigDecimal("901.8")), total);
    }

    @Test
    public void testOneThousandWithActiveCampaign() {
        MarketingCampaign marketingCampaign = mock(MarketingCampaign.class);
        when(marketingCampaign.isCrazySalesDay()).thenReturn(false);
        when(marketingCampaign.isActive()).thenReturn(true);
        Discount discount = new Discount(marketingCampaign);


        Money net = new Money(1000);
        Money total = discount.discountFor(net);

        assertEquals(new Money(new BigDecimal("950")), total);
    }

    @Test
    public void testOneThousandWithInActiveCampain() {
        MarketingCampaign marketingCampaign = mock(MarketingCampaign.class);
        when(marketingCampaign.isCrazySalesDay()).thenReturn(false);
        when(marketingCampaign.isActive()).thenReturn(false);
        Discount discount = new Discount(marketingCampaign);


        Money net = new Money(1000);
        Money total = discount.discountFor(net);

        assertEquals(new Money(new BigDecimal("1000")), total);
    }

    @Test
    public void testZeroNetPrice() {
        MarketingCampaign marketingCampaign = mock(MarketingCampaign.class);
        when(marketingCampaign.isCrazySalesDay()).thenReturn(false);
        when(marketingCampaign.isActive()).thenReturn(false);
        Discount discount = new Discount(marketingCampaign);


        Money net = new Money(0);
        Money total = discount.discountFor(net);

        assertEquals(new Money(new BigDecimal("0")), total);
    }

    @Test(expected = NullPointerException.class)
    public void testNullNetPrice() {
        MarketingCampaign marketingCampaign = mock(MarketingCampaign.class);
        when(marketingCampaign.isCrazySalesDay()).thenReturn(false);
        when(marketingCampaign.isActive()).thenReturn(false);
        Discount discount = new Discount(marketingCampaign);


        Money net = null;
        Money total = discount.discountFor(net);
    }

    @Test
    public void testMinusValueNetPrice() {
        MarketingCampaign marketingCampaign = mock(MarketingCampaign.class);
        when(marketingCampaign.isCrazySalesDay()).thenReturn(true);
        when(marketingCampaign.isActive()).thenReturn(false);
        Discount discount = new Discount(marketingCampaign);


        Money net = new Money(-100);
        Money total = discount.discountFor(net);

        assertEquals(new Money(new BigDecimal("-85")), total);
    }
}
