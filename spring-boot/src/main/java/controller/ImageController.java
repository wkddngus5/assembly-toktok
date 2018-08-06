package controller;

import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import service.S3Wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class ImageController {
    @Autowired
    private S3Wrapper s3Wrapper;

    @RequestMapping(value = "/images/{key}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> image(@PathVariable String key) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(s3Wrapper.downloadStream(key));
    }

    @RequestMapping(value = "uploads/**", method = RequestMethod.GET)
    public ResponseEntity<byte[]> uploadGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
        if(path.startsWith("/")) {
            path = path.substring(1, path.length());
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(s3Wrapper.downloadStream(path));
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public List<PutObjectResult> upload(@RequestParam("file") MultipartFile[] multipartFiles) {
        return s3Wrapper.upload(multipartFiles);
    }
}