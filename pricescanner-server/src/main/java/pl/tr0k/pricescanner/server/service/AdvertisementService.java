package pl.tr0k.pricescanner.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tr0k.pricescanner.server.domain.Advertisement;
import pl.tr0k.pricescanner.server.domain.repositories.AdvertisementRepository;

@Service
public class AdvertisementService implements IAdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Override
    public Iterable<Advertisement> findAllAdvertisements() {
        return advertisementRepository.findAll();
    }

    @Override
    public void deleteAdvertisement(Long advertisementId) {
        advertisementRepository.delete(advertisementId);
    }

    @Override
    public Advertisement findAdvertisement(Long advertisementId) {
        return advertisementRepository.findOne(advertisementId);
    }

    @Override
    public void saveAdvertisement(Advertisement advertisement) {
        advertisementRepository.save(advertisement);
    }
}
