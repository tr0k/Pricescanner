package pl.tr0k.pricescanner.server.manage.validation;

import com.vaadin.data.util.converter.Converter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

public class MoneyStringToLongConverterTest {

    private MoneyStringToLongConverter moneyStringToLongConverter;

    @Before
    public void setUp() throws Exception {
        moneyStringToLongConverter = new MoneyStringToLongConverter();
    }

    @Test
    public void testConvertToModel() throws Exception {
        Long expected = 1032L;
        String given = "10.32";
        Long actual = moneyStringToLongConverter.convertToModel(given, Long.class, Locale.getDefault());
        Assert.assertEquals(expected, actual);

        expected = 22L;
        given = "0.22";
        actual = moneyStringToLongConverter.convertToModel(given, Long.class, Locale.getDefault());
        Assert.assertEquals(expected, actual);

        expected = 99L;
        given = "0.99";
        actual = moneyStringToLongConverter.convertToModel(given, Long.class, Locale.getDefault());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testConvertToPresentation() throws Exception {
        String expected = "0.99";
        Long given = 99L;
        String actual = moneyStringToLongConverter.convertToPresentation(given, String.class, Locale.getDefault());
        Assert.assertEquals(expected, actual);

        expected = "15.49";
        given = 1549L;
        actual = moneyStringToLongConverter.convertToPresentation(given, String.class, Locale.getDefault());
        Assert.assertEquals(expected, actual);

        expected = "124.00";
        given = 12400L;
        actual = moneyStringToLongConverter.convertToPresentation(given, String.class, Locale.getDefault());
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = Converter.ConversionException.class)
    public void testConvertingToModelThrowsConversionException() throws Exception {
        moneyStringToLongConverter.convertToModel("22,32", Long.class, Locale.getDefault());
        moneyStringToLongConverter.convertToModel("00.1", Long.class, Locale.getDefault());
        moneyStringToLongConverter.convertToModel("0.0", Long.class, Locale.getDefault());
        moneyStringToLongConverter.convertToModel("-0.0", Long.class, Locale.getDefault());
        moneyStringToLongConverter.convertToModel("-5.0", Long.class, Locale.getDefault());
    }

    @Test(expected = Converter.ConversionException.class)
    public void testConvertingToPresentationThrowsConversionException() throws Exception {
        moneyStringToLongConverter.convertToPresentation(-1L, String.class, Locale.getDefault());
        moneyStringToLongConverter.convertToPresentation(-10L, String.class, Locale.getDefault());
        moneyStringToLongConverter.convertToPresentation(-99L, String.class, Locale.getDefault());
    }

    @Test
    public void testGetModelType() throws Exception {
        Class<Long> modelType = moneyStringToLongConverter.getModelType();
        Assert.assertEquals(Long.class, modelType);
    }

    @Test
    public void testGetPresentationType() throws Exception {
        Class<String> presentationType = moneyStringToLongConverter.getPresentationType();
        Assert.assertEquals(String.class, presentationType);
    }
}