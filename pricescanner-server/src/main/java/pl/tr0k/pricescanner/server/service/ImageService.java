package pl.tr0k.pricescanner.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tr0k.pricescanner.server.domain.repositories.ImageRepository;

/**
 * Created by Emil Kleszcz on 2014-10-01.
 */
@Service
public class ImageService implements IImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public byte[] findImage(Long imageId) {
        return imageRepository.findOne(imageId).getBytes();
    }

}
