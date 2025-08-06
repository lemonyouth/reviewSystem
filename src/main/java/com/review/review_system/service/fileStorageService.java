package com.review.review_system.service;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class fileStorageService {
    private final Path rootLocation = Paths.get("uploads/reviews");

    public void FileStorageService() throws IOException {
        Files.createDirectories(rootLocation);
    }

    public String store(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path destinationFile = this.rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();
        file.transferTo(destinationFile.toFile());
        return filename; // return the name so you can store it in DB
    }

    public UrlResource load(String filename) throws MalformedURLException {
        Path file = rootLocation.resolve(filename);
        return new UrlResource(file.toUri());
    }
}
