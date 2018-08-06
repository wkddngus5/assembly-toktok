package controller;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.S3Wrapper;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/aws/s3")
public class UploadController {
    @Autowired
    private S3Wrapper s3Wrapper;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public List<PutObjectResult> upload(@RequestParam("file") MultipartFile[] multipartFiles) {
        return s3Wrapper.upload(multipartFiles);
    }



    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(@RequestParam String key) throws IOException {
        return s3Wrapper.download(key);
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<S3ObjectSummary> list() throws IOException {
        return s3Wrapper.list();
    }

    @RequestMapping(value = "/image/{key}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> image(@PathVariable String key) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(s3Wrapper.downloadStream(key));
    }
}