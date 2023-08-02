package ru.skypro.homework.service;

import ru.skypro.homework.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ImageService {
    Image saveImage(MultipartFile image) throws IOException;

    byte[] getImage(long id) throws IOException;

    void deleteImage(Image entity) throws IOException;

    Image getEntity(long id);
}
