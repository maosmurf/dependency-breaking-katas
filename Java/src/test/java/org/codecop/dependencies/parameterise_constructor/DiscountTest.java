package org.codecop.dependencies.parameterise_constructor;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class DiscountTest {

    @Test
    public void higher_discount_for_CrazySalesDay() {
        final Discount discount = new Discount();

        final Money net = new Money(1002);
        final Money total = discount.discountFor(net);

        Assert.assertEquals(new Money(new BigDecimal("851.7")).describe(), total.describe());
    }

    @Test
    public void no_discount_for_usual_day() {
        final Discount discount = new Discount(new MarketingCampaign() {
            @Override
            public boolean isCrazySalesDay() {
                return false;
            }
        });

        final Money net = new Money(10);
        final Money total = discount.discountFor(net);

        Assert.assertEquals(new Money(new BigDecimal("10")).describe(), total.describe());
    }
}

/*

1. run tests -> red or green
2. falls red -> was ist das Problem
3. ich sehe es nicht weil Money kein toString hat
   3.1. debuggen
        breakpoint, starten, 2x auffalten
        Problem: muss es jedes Mal machen, wenn test rot ist (mühsam)
        -> FUNKTIONIERT, aber mühsam
   3.2. add toString
        darf ich das mit einer Methode die polymorph aufgerufen wird. eigentlich nicht.
        Problem: ev. legacy code etwas böses tut (geringe Wahrscheinlichkeit)
        -> ist Java Standard, je nach Risk machbar oder nicht
        -> je nachdem muss eine andere Methode nehmen
   3.3. change assert to use money.value, make value package access (with comment * visible for test *)
        Problem: break encapsulation, lower abstraction of test
        -> aber es ist alles immutable, somit kein Problem
   3.4. matcher für Money. also needs access to value
        braucht auch das toString, aber das haben wir nicht
        Problem: break encapsulation
        // Assert.assertThat(total, CoreMatchers.is(new Money(new BigDecimal("901.8"))));
   3.5. introduce "describe" und verwende es im Test
        Problem: eine Methode nur für Test hinzugefügt.
        -> schönste Lösung für uns (wenn ToString nicht geht)
        !! wir haben Code geschrieben ohne Test

4. test anpassen? -> 851.7
   green
   run with coverage -> 3 von 4 branches rot.

5. add more test cases
   egal was wir machen, es geht nicht, weil crazy sales schluckt alles.
   5.1. dependency im ctor übergeben (stub Campaign)
   5.2. call in methode auslagern und überschreiben (subclass, "partial stub")

   5.3. mit (encapsulate) getter kapseln und überschreiben (subclass, stub Campaign)
   5.4. lazy create überschreiben (subclass, stub Campaign)

   5.5. power mock (stub Campaign)
   5.6. nach Instanzierung mit Refection tauschen (stub Campaign)

6. modify code, only automatic?, minimal code change in production?
   6.1.a generate constructior with selected field, chain constructors (manually)
   6.1.b. introduce parameter and keep old method as delegate
      one existing test protects the no-args constructor

 */