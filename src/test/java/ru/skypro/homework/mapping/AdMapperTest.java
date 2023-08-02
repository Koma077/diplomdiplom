package ru.skypro.homework.mapping;

import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import org.junit.jupiter.api.Test;

import java.util.Objects;


class AdMapperTest {
    private final AdsMapper mapper = new AdsMapper();
    private final int pk = 1;
    private final int id = 10;
    private final int price = 100;
    private final String title = "bim bom";
    private final String description = "bim bom bim";


    @Test
    void entityToAdsDtoTest() {
        User user = new User();
        user.setId(id);
        Ads entity = new Ads(pk, user, title, price, description, new Image());
        AdsDto ads = mapper.entityToAdsDto(entity);
        assert ads.getPk() == pk;
        assert ads.getAuthor() == id;
        assert ads.getTitle().equals(title);
        assert ads.getPrice() == price;
        assert Objects.equals(ads.getImage(), "/ads/image/" + pk);
    }

    @Test
    void entityToFullAdsDto() {
        String authorFirstName = "Zxc";
        String authorLastName = "Zxc";
        String email = "xxx@ya.ru";
        String phone = "++79898989898";
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setPhone(phone);
        user.setFirstName(authorFirstName);
        user.setLastName(authorLastName);
        Ads entity = new Ads(pk, user, title, price, description, new Image());
        FullAdsDto fullAds = mapper.entityToFullAdsDto(entity);
        assert fullAds.getPk() == pk;
        assert fullAds.getAuthorFirstName().equals(authorFirstName);
        assert fullAds.getAuthorLastName().equals(authorLastName);
        assert fullAds.getDescription().equals(description);
        assert fullAds.getImage().equals("/ads/image/" + pk);
        assert fullAds.getPhone().equals(phone);
        assert fullAds.getEmail().equals(email);
        assert fullAds.getPrice() == price;
    }

    @Test
    void createAdsToEntity() {
        CreateAdsDto createAds = new CreateAdsDto();
        createAds.setDescription(description);
        createAds.setPrice(price);
        createAds.setTitle(title);
        User user = new User();
        Ads adEntity = mapper.createAdsToEntity(createAds, user);
        assert adEntity.getAuthor() == user;
        assert adEntity.getPrice() == price;
        assert adEntity.getDescription().equals(description);
        assert adEntity.getTitle().equals(title);
    }
}