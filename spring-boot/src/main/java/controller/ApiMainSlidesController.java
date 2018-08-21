package controller;

import dao.MainSlideDao;
import domain.MainSlide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiMainSlidesController {
    @Autowired
    private MainSlideDao mainSlideDao;

    @RequestMapping(value = "/slides", method = RequestMethod.GET)
    public ResponseEntity<List<MainSlide>> getMainSlides() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        List<MainSlide> mainSlideList = mainSlideDao.findAllOrOrderByOrderAsc();

        return new ResponseEntity<>(mainSlideList, headers, HttpStatus.OK);
    }
}
