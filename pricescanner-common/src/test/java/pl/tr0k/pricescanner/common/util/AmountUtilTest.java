package pl.tr0k.pricescanner.common.util;

import org.junit.Test;
import pl.tr0k.pricescanner.common.util.AmountUtil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AmountUtilTest {

    @Test
    public void testIsAmountValid() throws Exception {
        assertTrue(AmountUtil.isAmountValid("1020"));
        assertTrue(AmountUtil.isAmountValid("100.3"));
        assertTrue(AmountUtil.isAmountValid("1570,50"));
        assertTrue(AmountUtil.isAmountValid("1512"));
        assertTrue(AmountUtil.isAmountValid("120.50"));
        assertTrue(AmountUtil.isAmountValid("200,0"));
        assertTrue(AmountUtil.isAmountValid("12"));
        assertTrue(AmountUtil.isAmountValid("160680"));
        assertTrue(AmountUtil.isAmountValid("2.7"));
        assertTrue(AmountUtil.isAmountValid("157,56"));
        assertTrue(AmountUtil.isAmountValid("0,1"));
        assertTrue(AmountUtil.isAmountValid("0,11"));
        assertTrue(AmountUtil.isAmountValid("10,11"));
        assertTrue(AmountUtil.isAmountValid("2"));
        assertTrue(AmountUtil.isAmountValid("234"));
        assertTrue(AmountUtil.isAmountValid("0.00"));
        assertTrue(AmountUtil.isAmountValid("0,0"));
        assertTrue(AmountUtil.isAmountValid("0.40"));

        assertFalse(AmountUtil.isAmountValid("24.55.55"));
        assertFalse(AmountUtil.isAmountValid("53.432"));
        assertFalse(AmountUtil.isAmountValid("d&1"));
        assertFalse(AmountUtil.isAmountValid("3&13"));
        assertFalse(AmountUtil.isAmountValid("10,30.20"));
        assertFalse(AmountUtil.isAmountValid("-1"));
        assertFalse(AmountUtil.isAmountValid("-1.50"));
        assertFalse(AmountUtil.isAmountValid("20..20"));
        assertFalse(AmountUtil.isAmountValid("48.242"));
        assertFalse(AmountUtil.isAmountValid("-20.20"));
        assertFalse(AmountUtil.isAmountValid("43,476"));
        assertFalse(AmountUtil.isAmountValid("03"));
        assertFalse(AmountUtil.isAmountValid("0"));
        assertFalse(AmountUtil.isAmountValid("00"));
        assertFalse(AmountUtil.isAmountValid("00,00"));
        assertFalse(AmountUtil.isAmountValid("0.000"));
    }

    @Test
    public void testIsAmountHigherThan() throws Exception {
        assertTrue(AmountUtil.isAmountHigherThan("0.01", -1));
        assertTrue(AmountUtil.isAmountHigherThan("0.02", 1));
        assertTrue(AmountUtil.isAmountHigherThan("0,01", -1));
        assertTrue(AmountUtil.isAmountHigherThan("0,02", 1));
        assertTrue(AmountUtil.isAmountHigherThan("1122", 0));
        assertTrue(AmountUtil.isAmountHigherThan("3,56", 355));

        assertFalse(AmountUtil.isAmountHigherThan("19.99", 2000));
        assertFalse(AmountUtil.isAmountHigherThan("0.01", 1));
        assertFalse(AmountUtil.isAmountHigherThan("19,99", 2000));
        assertFalse(AmountUtil.isAmountHigherThan("0,01", 1));
        assertFalse(AmountUtil.isAmountHigherThan("1", 100));
        assertFalse(AmountUtil.isAmountHigherThan("3,55", 355));
    }

    @Test
    public void testThrowingExceptionsIsAmountHigherThan() {
        assertTrue(isThrowingException(null, 0));
        assertTrue(isThrowingException("a", 0));
        assertTrue(isThrowingException("a123", 42));
        assertTrue(isThrowingException("321a1", 55));
        assertTrue(isThrowingException("321a1", 66));
        assertTrue(isThrowingException("-11", 66));
        assertTrue(isThrowingException("", 0));
        assertTrue(isThrowingException("0 1", 1));
        assertTrue(isThrowingException("0,221", 1));
        assertTrue(isThrowingException("0,2214", 1));
        assertTrue(isThrowingException("0.2214", 1));
        assertTrue(isThrowingException("0,2214654645", 1));
        assertTrue(isThrowingException("0.2214654645", 1));
    }

    public boolean isThrowingException(String amount, int limit) {
        try {
            AmountUtil.isAmountHigherThan(amount, limit);
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}