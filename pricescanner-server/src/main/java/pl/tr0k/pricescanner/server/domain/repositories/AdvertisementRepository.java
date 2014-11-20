package pl.tr0k.pricescanner.server.domain.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.tr0k.pricescanner.server.domain.Advertisement;

public interface AdvertisementRepository extends CrudRepository<Advertisement, Long> {
}
