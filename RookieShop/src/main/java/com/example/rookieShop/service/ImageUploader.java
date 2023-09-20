package com.example.RookieShop.service;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;


@Service
public class ImageUploader {
    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "rookies-app-batch2",
            "api_key", "954653595438595",
            "api_secret", "FYaQjWgj0ZgIqTatSEuTgdOnu7k"));

    public String uploadImage(String dataUrl) {
        try {
            Map uploadResult  = cloudinary.uploader().upload(dataUrl, ObjectUtils.emptyMap());
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
