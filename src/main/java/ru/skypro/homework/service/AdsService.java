package ru.skypro.homework.service;

import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.entity.Ads;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface AdsService {
    AdsDto add(CreateAdsDto properties, MultipartFile image, String email) throws IOException;

    FullAdsDto getFullAdsById(int id);

    ResponseWrapperAdsDto getAllAds();

    ResponseWrapperAdsDto getAllMyAds(String name);

    void delete(int id) throws IOException;

    AdsDto update(int id, CreateAdsDto ads);

    Ads getEntity(int id);

    void uploadImage(int id, MultipartFile image) throws IOException;
}
