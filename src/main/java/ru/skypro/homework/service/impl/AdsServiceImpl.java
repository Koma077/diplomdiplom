package ru.skypro.homework.service.impl;

import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.exception.FindNoEntityException;
import ru.skypro.homework.mapping.AdsMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class AdsServiceImpl implements AdsService {
    private final AdsRepository adRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final AdsMapper mapper;

    private ResponseWrapperAdsDto getWrapper(List<Ads> list) {
        List<AdsDto> result = new LinkedList<>();
        list.forEach((entity -> result.add(mapper.entityToAdsDto(entity))));
        return new ResponseWrapperAdsDto(result.size(), result);
    }

    @Override
    public AdsDto add(CreateAdsDto properties, MultipartFile image, String email) throws IOException {
        Ads ad = mapper.createAdsToEntity(properties, userService.getEntity(email));
        ad.setImage(imageService.saveImage(image));
        return mapper.entityToAdsDto(adRepository.save(ad));
    }

    @Override
    public FullAdsDto getFullAdsById(int id) {
        return mapper.entityToFullAdsDto(getEntity(id));
    }

    @Override
    public void delete(int id) throws IOException {
        Image image = getEntity(id).getImage();
        adRepository.deleteById(id);
        imageService.deleteImage(image);
    }

    @Override
    public AdsDto update(int id, CreateAdsDto ads) {
        Ads entity = getEntity(id);
        entity.setTitle(ads.getTitle());
        entity.setDescription(ads.getDescription());
        entity.setPrice(ads.getPrice());
        adRepository.save(entity);
        return mapper.entityToAdsDto(entity);
    }

    @Override
    public Ads getEntity(int id) {
        return adRepository.findById(id).orElseThrow(() -> new FindNoEntityException("объявление"));
    }

    @Override
    public void uploadImage(int id, MultipartFile image) throws IOException {
        Ads adEntity = getEntity(id);
        Image imageEntity = adEntity.getImage();
        adEntity.setImage(imageService.saveImage(image));
        adRepository.save(adEntity);
        if (imageEntity != null) {
            imageService.deleteImage(imageEntity);
        }
    }

    @Override
    public ResponseWrapperAdsDto getAllAds() {
        return getWrapper(adRepository.findAll());
    }

    @Override
    public ResponseWrapperAdsDto getAllMyAds(String name) {
        return getWrapper(adRepository.findAllByAuthorEmail(name));
    }
}
