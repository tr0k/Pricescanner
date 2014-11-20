package pl.tr0k.pricescanner.server.manage.validation;

import com.vaadin.data.util.converter.Converter;
import pl.tr0k.pricescanner.common.util.MoneyUtils;
import pl.tr0k.pricescanner.common.util.ParameterException;

import java.util.Locale;

public class MoneyStringToLongConverter implements Converter<String, Long> {
    @Override
    public Long convertToModel(String value, Class<? extends Long> aClass, Locale locale) throws ConversionException {
        try {
            return MoneyUtils.getAmountInLong(value);
        } catch (ParameterException e) {
            throw new ConversionException("Bad format");
        }
    }

    @Override
    public String convertToPresentation(Long value, Class<? extends String> aClass, Locale locale) throws ConversionException {
        try {
            return MoneyUtils.getAmountFromLong(value);
        } catch (ParameterException e) {
            throw new ConversionException("Error during conversion");
        }
    }

    @Override
    public Class<Long> getModelType() {
        return Long.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
