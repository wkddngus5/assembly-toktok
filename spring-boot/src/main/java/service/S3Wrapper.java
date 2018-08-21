package service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class S3Wrapper {
    @Autowired
    private AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(String filePath, String uploadKey) throws FileNotFoundException {
        return upload(new FileInputStream(filePath), uploadKey);
    }

//    private PutObjectResult upload(InputStream inputStream, String uploadKey) {
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uploadKey, inputStream, new ObjectMetadata());
//        PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);
//        IOUtils.closeQuietly(inputStream);
//        return putObjectResult;
//    }

    public String upload(InputStream inputStream, String uploadKey) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uploadKey, inputStream, new ObjectMetadata());
        PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);
        IOUtils.closeQuietly(inputStream);
        return uploadKey;
    }

    public List<String> upload(MultipartFile[] multipartFiles) {
        List<String> putObjectResults = new ArrayList<>();
        Arrays.stream(multipartFiles)
                .filter(multipartFile -> !StringUtils.isEmpty(multipartFile.getOriginalFilename()))
                .forEach(multipartFile -> {
                    String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
                    String newFilename = Utils.getMd5(System.currentTimeMillis() + multipartFile.getOriginalFilename());
                    newFilename = newFilename + "." + ext;
                    try {
                        putObjectResults.add(upload(multipartFile.getInputStream(), newFilename));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        return putObjectResults;
    }

    public ResponseEntity<byte[]> download(String key) throws IOException {
        byte[] bytes = downloadStream(key);
        String fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<>(downloadStream(key), httpHeaders, HttpStatus.OK);
    }

    public byte[] downloadStream(String key) throws IOException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
        S3Object s3Object = amazonS3Client.getObject(getObjectRequest);
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);
        return bytes;
    }

    public List<S3ObjectSummary> list() {
        ObjectListing objectListing = amazonS3Client.listObjects(new ListObjectsRequest().withBucketName(bucket));
        List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();
        return s3ObjectSummaries;
    }

    public String uploadProfileImage(String url, String fileName) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        byte[] image = restTemplate.getForObject(url, byte[].class);
        if (image == null) {
            return null;
        }
        String filePath = Paths.get(fileName).toAbsolutePath().toString();

        java.nio.file.Files.write(Paths.get(fileName), image);
        File downloadFile = new File(filePath);
        String ext = FilenameUtils.getExtension(filePath);
        String newFileName = UUID.randomUUID().toString();
        String path = newFileName + "." + ext;
        upload(filePath, path);

        downloadFile.delete();
        return path;
    }

    public void updateImage(String tempImage, String imagePath) {
        try {
            byte[] bytes = downloadStream(tempImage);
            upload(new ByteArrayInputStream(bytes), imagePath);
            delete(tempImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(String key) {
        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
