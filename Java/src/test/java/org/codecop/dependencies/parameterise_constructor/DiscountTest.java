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
        Discount discount = new Discount();

        Money net = new Money(1002);
        Money total = discount.discountFor(net);

        assertEquals(new Money(new BigDecimal("901.8")), total);
    }


}
