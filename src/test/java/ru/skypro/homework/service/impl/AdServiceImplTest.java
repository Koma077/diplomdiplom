package ru.skypro.homework.service.impl;

import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapping.AdsMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AdServiceImplTest {
    @InjectMocks
    private AdsServiceImpl adService;
    @Mock
    private AdsRepository adRepository;
    @Mock
    private UserService userService;
    @Mock
    private ImageService imageService;
    @Mock
    private AdsMapper mapper;
    @Mock
    private User user;
    private final int price = 100;
    private final int pk = 1;
    private final int id = 10;
    private final String title = "bim bom";
    private final String description = "bim bom bim";
    private final String email = "xxx@mail.ru";
    private final String imagePath = "ads/image/" + pk;
    private Ads adEntity;

    @BeforeEach
    void setUp() {
        adEntity = new Ads(pk, user, title, price, description, null);
    }

    @Test
    void addTest() throws IOException {
        byte[] inputArray = "Test".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("fileName", inputArray);
        CreateAdsDto properties = new CreateAdsDto();
        properties.setTitle(title);
        properties.setPrice(price);
        properties.setDescription(description);
        when(userService.getEntity(email)).thenReturn(user);
        when(mapper.createAdsToEntity(properties, user)).thenReturn(adEntity);
        when(adRepository.save(adEntity)).thenReturn(adEntity);
        when(mapper.entityToAdsDto(adEntity)).thenReturn(new AdsDto(id, imagePath, pk, price, title));

        AdsDto ad = adService.add(properties, mockMultipartFile, email);
        verify(imageService).saveImage(mockMultipartFile);
        assert ad.getAuthor() == id;
        assert ad.getTitle().equals(title);
        assert ad.getPk() == pk;
        assert ad.getPrice() == price;
        assert ad.getImage().equals("ads/image/" + pk);
    }

    @Test
    void getFullAdsByIdTest() {
        when(adRepository.findById(pk)).thenReturn(Optional.of(adEntity));
        String fName = "Oleg";
        String lName = "Olegov";
        String phone = "+78008889922";
        when(mapper.entityToFullAdsDto(adEntity)).thenReturn(new FullAdsDto(pk, fName, lName, description,
                email, imagePath, phone, price, title));

        FullAdsDto fullAds = adService.getFullAdsById(pk);
        assert fullAds.getPk() == pk;
        assert fullAds.getAuthorFirstName().equals(fName);
        assert fullAds.getAuthorLastName().equals(lName);
        assert fullAds.getDescription().equals(description);
        assert fullAds.getEmail().equals(email);
        assert fullAds.getImage().equals(imagePath);
        assert fullAds.getPhone().equals(phone);
        assert fullAds.getPrice() == price;
        assert fullAds.getTitle().equals(title);
    }

    @Test
    void deleteTest() throws IOException {
        adEntity.setImage(new Image());
        when(adRepository.findById(pk)).thenReturn(Optional.of(adEntity));
        adService.delete(pk);
        verify(adRepository).deleteById(pk);
        verify(imageService).deleteImage(adEntity.getImage());
    }

    @Test
    void updateTest() {
        String newDescription = "newDesc";
        String newTitle = "newTitle";
        int newPrice = 200;
        when(adRepository.findById(pk)).thenReturn(Optional.of(adEntity));
        CreateAdsDto ads = new CreateAdsDto();
        ads.setDescription(newDescription);
        ads.setTitle(newTitle);
        ads.setPrice(newPrice);
        Ads newEntity = new Ads(pk, user, newTitle, newPrice, newDescription, null);
        when(mapper.entityToAdsDto(newEntity)).thenReturn(new AdsDto(id, imagePath, pk, newPrice, newTitle));

        AdsDto ad = adService.update(pk, ads);
        verify(adRepository).save(newEntity);
        assert ad.getTitle().equals(newTitle);
        assert ad.getPrice() == newPrice;
    }

    @Test
    void uploadImageTest() throws IOException {
        byte[] inputArray = "Test".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("fileName", inputArray);
        Image imageEntity = new Image(id);
        adEntity.setImage(imageEntity);
        when(adRepository.findById(pk)).thenReturn(Optional.of(adEntity));

        adService.uploadImage(pk, mockMultipartFile);
        verify(imageService).saveImage(mockMultipartFile);
        verify(imageService).deleteImage(imageEntity);

    }

    @Test
    void getAllAdsTest() {
        Ads adEntity1 = new Ads();
        Ads adEntity2 = new Ads();
        AdsDto ad = new AdsDto(id, imagePath, pk, price, title);
        when(adRepository.findAll()).thenReturn(List.of(adEntity1, adEntity, adEntity2));
        when(mapper.entityToAdsDto(adEntity)).thenReturn(ad);
        when(mapper.entityToAdsDto(adEntity1)).thenReturn(new AdsDto());
        when(mapper.entityToAdsDto(adEntity2)).thenReturn(new AdsDto());

        ResponseWrapperAdsDto wrapperAds = adService.getAllAds();
        assert wrapperAds.getCount() == 3;
        Assertions.assertTrue(wrapperAds.getResults().contains(ad));
    }

    @Test
    void getAllMyAdsTest() {
        Ads adEntity1 = new Ads();
        AdsDto ad = new AdsDto(id, imagePath, pk, price, title);
        when(adRepository.findAllByAuthorEmail(email)).thenReturn(List.of(adEntity1, adEntity));
        when(mapper.entityToAdsDto(adEntity)).thenReturn(ad);
        when(mapper.entityToAdsDto(adEntity1)).thenReturn(new AdsDto());

        ResponseWrapperAdsDto wrapperAds = adService.getAllMyAds(email);
        assert wrapperAds.getCount() == 2;
        Assertions.assertTrue(wrapperAds.getResults().contains(ad));
    }
}