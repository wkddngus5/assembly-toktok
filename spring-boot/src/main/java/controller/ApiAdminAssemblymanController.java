package controller;

import dao.CongressmenDao;
import domain.Congressmen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class ApiAdminAssemblymanController {

    @Autowired
    private CongressmenDao congressmenDao;

    @RequestMapping(value = "/administrator/assemblyman", method = RequestMethod.POST)
    public ResponseEntity<Congressmen> addAssemblyman(@RequestBody Congressmen request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        String createDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        request.setCreated_at(createDate);
        request.setUpdated_at(createDate);
        request.setCommittee_id(request.getCommittee());
        request.setImage(request.getProfile());

        Congressmen congressmen = congressmenDao.save(request);
        if (congressmen == null) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(congressmen, headers, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/administrator/assemblyman/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Congressmen> updateAssemblyman(@PathVariable Long id, @RequestBody Congressmen request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Congressmen updateCongressmen = congressmenDao.getOne(id);
        updateCongressmen.updateCongressmen(request);
        updateCongressmen = congressmenDao.save(updateCongressmen);

        if (updateCongressmen == null) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(updateCongressmen, headers, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/administrator/assemblyman/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Congressmen> deleteAssemblyman(@PathVariable Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Congressmen deleteCommittee = Congressmen.createCommittee(congressmenDao.getOne(id));
        congressmenDao.deleteById(id);

        if (deleteCommittee == null) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(deleteCommittee, headers, HttpStatus.OK);
        }
    }
}
