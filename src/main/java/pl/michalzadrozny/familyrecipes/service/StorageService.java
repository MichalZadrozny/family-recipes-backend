package pl.michalzadrozny.familyrecipes.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@Service
public class StorageService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;

    @Autowired
    public StorageService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public void uploadFile(MultipartFile multipartFile, String name) throws IOException {

        File file = convertMultipartFileToFile(multipartFile, name);

        s3Client.putObject(new PutObjectRequest(bucketName, name, file));

        //noinspection ResultOfMethodCallIgnored
        file.delete();
    }

    private File convertMultipartFileToFile(MultipartFile file, String name) throws IOException {

        if (file == null) throw new IOException("Provided MultipartFile is null");

        File convertedFile = new File(name);

        try (FileOutputStream fileOutputStream = new FileOutputStream(convertedFile)) {
            fileOutputStream.write(file.getBytes());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return convertedFile;
    }

    public String getMediaURL(String mediaName) {
        return String.valueOf(s3Client.getUrl(bucketName, mediaName));
    }
}
