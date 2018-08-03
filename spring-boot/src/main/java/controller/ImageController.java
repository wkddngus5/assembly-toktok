package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.S3Wrapper;

import java.io.IOException;

@RestController
public class ImageController {
    @Autowired
    private S3Wrapper s3Wrapper;

    @RequestMapping(value = "/images/{key}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> image(@PathVariable String key) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(s3Wrapper.downloadStream(key));
    }
}