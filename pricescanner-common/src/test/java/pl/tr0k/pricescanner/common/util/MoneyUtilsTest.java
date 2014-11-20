package pl.tr0k.pricescanner.common.util;

import org.junit.Test;
import pl.tr0k.pricescanner.common.util.MoneyUtils;
import pl.tr0k.pricescanner.common.util.ParameterException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MoneyUtilsTest {
    @Test
    public void testGetAmountFromLong() throws Exception {
        assertEquals("1240.43", MoneyUtils.getAmountFromLong(124043L));
        assertEquals("124.04", MoneyUtils.getAmountFromLong(12404L));
        assertEquals("12.40", MoneyUtils.getAmountFromLong(1240L));
        assertEquals("0.40", MoneyUtils.getAmountFromLong(40L));
        assertEquals("12.44", MoneyUtils.getAmountFromLong(1244L));
        assertEquals("0.12", MoneyUtils.getAmountFromLong(12L));
        assertEquals("18.84", MoneyUtils.getAmountFromLong(1884L));
        assertEquals("100.20", MoneyUtils.getAmountFromLong(10020L));
        assertEquals("1.45", MoneyUtils.getAmountFromLong(145L));
        assertEquals("25.61", MoneyUtils.getAmountFromLong(2561L));
    }

    @Test
    public void testGetAmountInLong() throws Exception {
        assertEquals(1240L, MoneyUtils.getAmountInLong("12,40"));
        assertEquals(40L, MoneyUtils.getAmountInLong("0.4"));
        assertEquals(2L, MoneyUtils.getAmountInLong("0,02"));
        assertEquals(200L, MoneyUtils.getAmountInLong("2"));
        assertEquals(32L, MoneyUtils.getAmountInLong("0.32"));
        assertEquals(32212L, MoneyUtils.getAmountInLong("322.12"));
        assertEquals(14523L, MoneyUtils.getAmountInLong("145.23"));
        assertEquals(324234L, MoneyUtils.getAmountInLong("3242.34"));
        assertEquals(230L, MoneyUtils.getAmountInLong("2.30"));
        assertEquals(100L, MoneyUtils.getAmountInLong("1"));
    }

    @Test (expected = ParameterException.class)
    public void testExceptionIfAmountInLongIsWrong() throws Exception {
        assertNotNull(MoneyUtils.getAmountFromLong(0L));
        assertNotNull(MoneyUtils.getAmountFromLong(-12L));
        assertNotNull(MoneyUtils.getAmountFromLong(-123L));
        assertNotNull(MoneyUtils.getAmountFromLong(-123L));
        assertNotNull(MoneyUtils.getAmountFromLong(-124L));
    }

    @Test (expected = ParameterException.class)
    public void testExceptionIfAmountInStringIsWrong() throws Exception {
        assertNotNull(MoneyUtils.getAmountInLong(null));
        assertNotNull(MoneyUtils.getAmountInLong("24.55.55"));
        assertNotNull(MoneyUtils.getAmountInLong("34..2"));
        assertNotNull(MoneyUtils.getAmountInLong("43.2."));
        assertNotNull(MoneyUtils.getAmountInLong("3..3"));
        assertNotNull(MoneyUtils.getAmountInLong(".3223"));
        assertNotNull(MoneyUtils.getAmountInLong(".3"));
        assertNotNull(MoneyUtils.getAmountInLong("33.333"));
        assertNotNull(MoneyUtils.getAmountInLong("33.12314"));
        assertNotNull(MoneyUtils.getAmountInLong("4.3"));
    }
}