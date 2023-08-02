package ru.skypro.homework.mapping;

import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.User;
import org.springframework.stereotype.Component;


@Component
public class AdsMapper {
    public AdsDto entityToAdsDto(Ads entity) {
        return new AdsDto(entity.getAuthor().getId(), entity.getImagePath(),
                entity.getPk(), entity.getPrice(), entity.getTitle());
    }

    public FullAdsDto entityToFullAdsDto(Ads entity) {
        return new FullAdsDto(entity.getPk(), entity.getAuthor().getFirstName(), entity.getAuthor().getLastName(),
                entity.getDescription(), entity.getAuthor().getEmail(), entity.getImagePath(),
                entity.getAuthor().getPhone(), entity.getPrice(), entity.getTitle());
    }

    public Ads createAdsToEntity(CreateAdsDto ads, User author) {
        return new Ads(author, ads.getTitle(), ads.getPrice(), ads.getDescription());
    }
}
