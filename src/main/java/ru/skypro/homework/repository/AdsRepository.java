package ru.skypro.homework.repository;
import ru.skypro.homework.entity.Ads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {
    List<Ads> findAllByAuthorEmail(String email);
}
