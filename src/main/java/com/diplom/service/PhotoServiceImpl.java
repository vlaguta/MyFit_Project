package com.diplom.service;

import com.diplom.Exceptions.BusinessException;
import com.diplom.model.Photo;
import com.diplom.repository.CustomerRepository;
import com.diplom.repository.PhotoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoServiceImpl implements PhotoService {

    private static final Logger LOGGER = LogManager.getLogger(PostServiceImpl.class);
    private final PhotoRepository photoRepository;
    private final CustomerRepository customerRepository;

    private final Path PhotoDirectoryPath = Paths.get("src/main/resources/static");

    public PhotoServiceImpl(PhotoRepository photoRepository, CustomerRepository customerRepository) {
        this.photoRepository = photoRepository;
        this.customerRepository = customerRepository;
        init();
    }

    @Override
    public void saveOrUpdate(MultipartFile file, String login) {
        try {
            Files.copy(file.getInputStream(), this.PhotoDirectoryPath.resolve(file.getOriginalFilename()));

            Photo photo = photoRepository.findByCustomerLogin(login)
                    .orElseGet(() -> Photo.builder()
                            .customer(customerRepository.findCustomerByLogin(login)
                                    .orElseThrow(() -> new BusinessException("Не удалось найти фото, не существует пользователя с логином:" + login)))
                            .build());
            photo.setName(file.getOriginalFilename());
            photo.setUrl("/" + file.getOriginalFilename());
            photoRepository.save(photo);
        } catch (Exception e) {
            LOGGER.error("Возникла ошибка при сохранении фото", e);
            throw new BusinessException("Не удалось сохранить фото");
        }
    }

    @Override
    public Photo load(String filename) {
        return photoRepository.findByName(filename)
                .orElseThrow(() -> new BusinessException("Не удалось загрузить фото"));
    }

    @Override
    public Photo getPhoto(int customerId) {
        return photoRepository.findByCustomerId(customerId).orElseThrow(() -> new BusinessException("Не удалось найти пользователя customerId:" + customerId));
    }

    @Override
    public Photo save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.PhotoDirectoryPath.resolve(file.getOriginalFilename()));
            Photo photo = new Photo();
            photo.setName(file.getOriginalFilename());
            photo.setUrl("/" + file.getOriginalFilename());
            photoRepository.save(photo);
            return photo;
        } catch (Exception e) {
            throw new RuntimeException("Не удалось сохранить фото");
        }
    }

    private void init() {
        if (Files.exists(PhotoDirectoryPath)) {
            return;
        }
        try {
            Files.createDirectory(PhotoDirectoryPath);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить фото!", e);
        }
    }
}
