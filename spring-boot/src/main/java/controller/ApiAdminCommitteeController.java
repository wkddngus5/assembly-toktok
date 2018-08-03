package controller;

import dao.CommitteeDao;
import dao.CongressmenDao;
import domain.Committee;
import domain.User;
import domain.UserCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class ApiAdminCommitteeController {
    @Autowired
    private CommitteeDao committeeDao;

    @Autowired
    private CongressmenDao congressmenDao;

    @RequestMapping(value = "/administrator/committees", method = RequestMethod.POST)
    public ResponseEntity<Committee> addCommittees(@RequestBody Committee request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        String createDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        request.setCreated_at(createDate);
        request.setUpdated_at(createDate);

        Committee committee = committeeDao.save(request);
        if (committee == null) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(committee, headers, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/administrator/committees/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Committee> updateCommittees(@PathVariable Long id, @RequestBody Committee request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Committee updateCommittee = committeeDao.getOne(id);
        updateCommittee.updateCommittee(request);
        updateCommittee = committeeDao.save(updateCommittee);

        if (updateCommittee == null) {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(updateCommittee, headers, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/administrator/committees/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Committee> deleteCommittees(@PathVariable Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        int count = congressmenDao.findByCommitteeId(id);
        if (count > 0) {
            return new ResponseEntity<>(Committee.createError("위원회에 소속된 의원이 있습니다. 의원을 모두 지운 후에 위원회를 삭제할 수 있습니다.", count), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            Committee deleteCommittee = Committee.createCommittee(committeeDao.getOne(id));
            committeeDao.deleteById(id);

            if (deleteCommittee == null) {
                return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(deleteCommittee, headers, HttpStatus.OK);
            }
        }
    }
}