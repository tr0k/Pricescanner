package pl.tr0k.pricescanner.server.service;

import pl.tr0k.pricescanner.server.domain.Advertisement;

public interface IAdvertisementService {

    Iterable<Advertisement> findAllAdvertisements();

    void deleteAdvertisement(Long advertisementId);

    Advertisement findAdvertisement(Long advertisementId);

    void saveAdvertisement(Advertisement advertisement);
}
