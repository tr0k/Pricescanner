package pl.tr0k.pricescanner.server.domain.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.tr0k.pricescanner.server.domain.Image;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
