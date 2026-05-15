package com._DM.E_commerce.Service;

import com._DM.E_commerce.Service.Exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
public class ImageStorageService {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp", "gif");
    private static final String PUBLIC_UPLOAD_PATH = "/uploads";

    private final Path uploadRoot;

    public ImageStorageService(@Value("${app.upload.dir:uploads}") String uploadDir) {
        this.uploadRoot = Path.of(uploadDir).toAbsolutePath().normalize();
    }

    public String saveProductImage(UUID productId, MultipartFile file) {
        return saveImage("produtos", productId, file, "Erro ao salvar imagem do produto");
    }

    public String saveUserImage(UUID userId, MultipartFile file) {
        return saveImage("usuarios", userId, file, "Erro ao salvar imagem do usuário");
    }

    private String saveImage(String folderName, UUID ownerId, MultipartFile file, String errorMessage) {
        validateImage(file);

        String extension = getExtension(file);
        String fileName = ownerId + "-" + UUID.randomUUID() + "." + extension;
        Path folder = uploadRoot.resolve(folderName);
        Path destination = folder.resolve(fileName).normalize();

        try {
            Files.createDirectories(folder);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            return PUBLIC_UPLOAD_PATH + "/" + folderName + "/" + fileName;
        } catch (IOException e) {
            throw new DatabaseException(errorMessage);
        }
    }

    public void deleteByPublicUrl(String publicUrl) {
        if (publicUrl == null || !publicUrl.startsWith(PUBLIC_UPLOAD_PATH + "/")) {
            return;
        }

        Path filePath = uploadRoot.resolve(publicUrl.substring(PUBLIC_UPLOAD_PATH.length() + 1)).normalize();
        if (!filePath.startsWith(uploadRoot)) {
            return;
        }

        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new DatabaseException("Erro ao remover imagem anterior do produto");
        }
    }

    private void validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new DatabaseException("Arquivo de imagem vazio");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.toLowerCase(Locale.ROOT).startsWith("image/")) {
            throw new DatabaseException("O arquivo enviado precisa ser uma imagem");
        }

        String extension = getExtension(file);
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new DatabaseException("Formato de imagem não permitido");
        }
    }

    private String getExtension(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || !originalFileName.contains(".")) {
            return extensionFromContentType(file.getContentType());
        }

        return originalFileName.substring(originalFileName.lastIndexOf('.') + 1)
                .toLowerCase(Locale.ROOT);
    }

    private String extensionFromContentType(String contentType) {
        if (contentType == null) {
            return "";
        }

        return switch (contentType.toLowerCase(Locale.ROOT)) {
            case "image/jpeg" -> "jpg";
            case "image/png" -> "png";
            case "image/webp" -> "webp";
            case "image/gif" -> "gif";
            default -> "";
        };
    }
}
