package pl.tr0k.pricescanner.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.tr0k.pricescanner.common.dto.ProductDTO;
import roboguice.activity.RoboActivity;

/**
 * Created by Emil Kleszcz on 2014-10-08.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class GetProductDTOEvent {
    private ProductDTO productDTO;
    private RoboActivity destActivity;
}
