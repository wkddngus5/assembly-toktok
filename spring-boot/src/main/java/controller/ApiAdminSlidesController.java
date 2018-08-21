package controller;

import com.google.gson.Gson;
import dao.MainSlideDao;
import domain.Congressmen;
import domain.MainSlide;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.S3Wrapper;
import utils.ImageUploadUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class ApiAdminSlidesController {

    @Autowired
    private MainSlideDao mainSlideDao;
    @Autowired
    private S3Wrapper s3Wrapper;

    @RequestMapping(value = "/administrator/slides", method = RequestMethod.POST)
    public ResponseEntity<MainSlide> postSlide(@RequestBody MainSlide slide) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        String createDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        slide.setCreated_at(createDate);
        slide.setUpdated_at(createDate);

        System.out.println("main : " + new Gson().toJson(slide));
        MainSlide dbSlide = mainSlideDao.save(slide);
        if (!StringUtils.isEmpty(dbSlide.getImage())) {
            s3Wrapper.updateImage(dbSlide.getImage(), ImageUploadUtil.saveImagePath(MainSlide.class.getSimpleName(), String.valueOf(dbSlide.getId()), dbSlide.getImage()));
            dbSlide.setImage(ImageUploadUtil.getImagePath(MainSlide.class.getSimpleName(), String.valueOf(dbSlide.getId()), dbSlide.getImage()));
        }

        return new ResponseEntity<>(dbSlide, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/administrator/slides/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MainSlide> updateSlide(@PathVariable Long id, @RequestBody MainSlide slide) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        MainSlide dbSlide = mainSlideDao.getOne(id);

        if (dbSlide == null) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }

        dbSlide.updateSlide(slide);
        dbSlide = mainSlideDao.save(dbSlide);
        if (!StringUtils.isEmpty(slide.getImage())) {
            s3Wrapper.updateImage(dbSlide.getImage(), ImageUploadUtil.saveImagePath(MainSlide.class.getSimpleName(), String.valueOf(dbSlide.getId()), dbSlide.getImage()));
        }
        dbSlide.setImage(ImageUploadUtil.getImagePath(MainSlide.class.getSimpleName(), String.valueOf(dbSlide.getId()), dbSlide.getImage()));
        return new ResponseEntity<>(dbSlide, headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/administrator/slides/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MainSlide> deleteSlide(@PathVariable Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        MainSlide dbSlide = mainSlideDao.findById(id).get();
        if(dbSlide == null) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }

        mainSlideDao.delete(dbSlide);
        return new ResponseEntity<>(dbSlide, headers, HttpStatus.OK);
    }
}

