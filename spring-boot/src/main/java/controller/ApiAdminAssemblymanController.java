package controller;

import dao.CongressmanDao;
import domain.Congressman;
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
public class ApiAdminAssemblymanController {

    @Autowired
    private CongressmanDao congressmanDao;
    @Autowired
    private S3Wrapper s3Wrapper;

    @RequestMapping(value = "/administrator/assemblyman", method = RequestMethod.POST)
    public ResponseEntity<Congressman> addAssemblyman(@RequestBody Congressman request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        String createDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        request.setCreated_at(createDate);
        request.setUpdated_at(createDate);
        request.setCommittee_id(request.getCommittee());
        request.setImage(request.getProfile());

        Congressman congressman = congressmanDao.save(request);
        if (congressman == null) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            if (!StringUtils.isEmpty(request.getProfile())) {
                s3Wrapper.updateImage(congressman.getImage(), ImageUploadUtil.saveImagePath(Congressman.class.getSimpleName(), String.valueOf(congressman.getId()), congressman.getImage()));
                congressman.setImage(ImageUploadUtil.getImagePath(Congressman.class.getSimpleName(), String.valueOf(congressman.getId()), congressman.getImage()));
            }
            return new ResponseEntity<>(congressman, headers, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/administrator/assemblyman/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Congressman> updateAssemblyman(@PathVariable Long id, @RequestBody Congressman request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Congressman updateCongressman = congressmanDao.getOne(id);
        updateCongressman.updateCongressmen(request);
        updateCongressman = congressmanDao.save(updateCongressman);

        if (updateCongressman == null) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            if (!StringUtils.isEmpty(request.getProfile())) {
                s3Wrapper.updateImage(request.getProfile(), ImageUploadUtil.saveImagePath(Congressman.class.getSimpleName(), String.valueOf(updateCongressman.getId()), updateCongressman.getImage()));
            }
            updateCongressman.setImage(ImageUploadUtil.getImagePath(Congressman.class.getSimpleName(), String.valueOf(updateCongressman.getId()), updateCongressman.getImage()));
            return new ResponseEntity<>(updateCongressman, headers, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/administrator/assemblyman/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Congressman> deleteAssemblyman(@PathVariable Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Congressman deleteCommittee = Congressman.createCommittee(congressmanDao.getOne(id));
        congressmanDao.deleteById(id);

        if (deleteCommittee == null) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(deleteCommittee, headers, HttpStatus.OK);
        }
    }
}
