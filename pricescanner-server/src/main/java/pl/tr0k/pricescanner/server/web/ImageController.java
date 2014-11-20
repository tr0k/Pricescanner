package pl.tr0k.pricescanner.server.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.tr0k.pricescanner.server.service.IImageService;
import pl.tr0k.pricescanner.server.web.exception.NotFoundException;

/**
 * Created by Emil Kleszcz on 2014-10-01.
 */
@RequestMapping("/images")
@Controller
public class ImageController{

    @Autowired
    private IImageService imageService;

    @RequestMapping(value = "/{imageId}", method = RequestMethod.GET, produces = "image/png")
    public @ResponseBody
    byte[] image(@PathVariable("imageId") Long imageId) {
        byte[] imageBytes = imageService.findImage(imageId);
        if (imageBytes == null) {
            throw new NotFoundException();
        }
        return imageBytes;
    }

}
