package pl.tr0k.pricescanner.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;


public class MoneyUtils {
    public static long getAmountInLong(String amountText)
            throws ParameterException {

        if (amountText == null || !AmountUtil.isAmountValid(amountText)) {
            throw new ParameterException("Amount of money should be in the correct format. " +
                    "Amount given: " + amountText);
        }

        amountText = amountText.replaceAll(",", ".");
        BigDecimal amount = new BigDecimal(amountText);
        BigDecimal amountInPenny = amount.multiply(new BigDecimal(100));

        return amountInPenny.longValue();
    }

    public static String getAmountFromLong(long amountInLong)
            throws ParameterException {

        if (amountInLong < 0) {
            throw new ParameterException("Amount of money should be equals or greater than zero. " +
                    "Amount given: " + amountInLong);
        }

        BigDecimal amount = new BigDecimal(amountInLong).divide(new BigDecimal(100));
        DecimalFormat df = new DecimalFormat("0.00");
        String amountWithComma = df.format(amount);

        return amountWithComma.replaceAll(",", ".");
    }
}
