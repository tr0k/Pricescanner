package pl.tr0k.pricescanner.service.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import retrofit.RetrofitError;

/**
 * Created by Emil Kleszcz on 2014-10-16.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class NotFoundProductException extends RuntimeException{
    private final RetrofitError retrofitError;
}
