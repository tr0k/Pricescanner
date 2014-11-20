package pl.tr0k.pricescanner.common.util;

import java.util.regex.Pattern;

public class AmountUtil {

    public static final String AMOUNT_PATTERN =
            "([1-9]\\d*(\\.|\\,)\\d{1,2})|([1-9]\\d*)|([0](\\,|\\.)((\\d{1,2})|(\\d[1-9])))";

    public static boolean isAmountValid(String amount) {
        return amount != null &&
                Pattern.matches(AMOUNT_PATTERN, amount);
    }

    public static boolean isAmountHigherThan(String amount, int limitInPenny) throws ParameterException {
        long amountInLong = MoneyUtils.getAmountInLong(amount);
        return amountInLong > limitInPenny;
    }
}
