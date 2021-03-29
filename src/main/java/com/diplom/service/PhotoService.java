package com.diplom.service;

import com.diplom.model.Photo;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {

    void saveOrUpdate(MultipartFile file, String login);

    Photo load(String filename);

    Photo getPhoto(int customerId);

    Photo save(MultipartFile file);

}
