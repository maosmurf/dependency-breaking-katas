package org.codecop.dependencies.subclass_and_override;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.function.LongSupplier;

public class MarketingCampaign {

    private final LongSupplier systemTimeMillis;

    public MarketingCampaign() {
        this(System::currentTimeMillis);
    }

    public MarketingCampaign(LongSupplier currentTimeMillis) {
        systemTimeMillis = currentTimeMillis;
    }

    public boolean isActive() {
        return milliSeconds() % 2 == 0;
    }

    private long milliSeconds() {
        return systemTimeMillis.getAsLong();
    }

    public boolean isCrazySalesDay() {
        return dayOfWeek().compareTo(DayOfWeek.FRIDAY) == 0;
    }

    DayOfWeek dayOfWeek() {
        return LocalDateTime.now().getDayOfWeek();
    }
}
