package org.codecop.dependencies.subclass_and_override;

import org.junit.Test;

import java.time.DayOfWeek;
import java.util.function.LongSupplier;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MarketingCampaignTest {

    @Test
    public void testTuesdayNotCrazySalesSay() {

        MarketingCampaign campaign = new MarketingCampaign() {
            DayOfWeek dayOfWeek() {
                return DayOfWeek.TUESDAY;
            }
        };

        boolean isCrazySalesDay = campaign.isCrazySalesDay();

        assertFalse(isCrazySalesDay);
    }

    @Test
    public void testFridayCrazySalesSay() {

        MarketingCampaign campaign = new MarketingCampaign() {
            DayOfWeek dayOfWeek() {
                return DayOfWeek.FRIDAY;
            }
        };

        boolean isCrazySalesDay = campaign.isCrazySalesDay();

        assertTrue(isCrazySalesDay);
    }


    @Test
    public void testEvenMillis() {
        LongSupplier supplier = mock(LongSupplier.class);
        long millisec = 4L;
        MarketingCampaign campaign = new MarketingCampaign(supplier);
        when(supplier.getAsLong()).thenReturn(millisec);
        boolean isActive = campaign.isActive();
        assertTrue(isActive);
    }
}
