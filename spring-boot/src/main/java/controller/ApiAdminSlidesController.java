package controller;

import dao.CommitteeDao;
import dao.CongressmenDao;
import dao.MainSlideDao;
import domain.Committee;
import domain.MainSlide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ApiAdminSlidesController {
    @Autowired
    private MainSlideDao mainSlideDao;

    @RequestMapping(value = "/administrator/slides", method = RequestMethod.POST)
    public ResponseEntity<MainSlide> postSlide(@RequestBody MainSlide slide) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        String createDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        slide.setCreated_at(createDate);
        slide.setUpdated_at(createDate);

        System.out.println(slide);
        MainSlide dbSlide = mainSlideDao.save(slide);
        System.out.println(dbSlide);

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
        mainSlideDao.save(dbSlide);

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

