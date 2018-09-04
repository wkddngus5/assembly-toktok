package controller;

import dao.*;
import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class ApiProjectCommentsController {
    private static final long GOAL_COUNT = 1000;

    private UserService userService = new UserService();

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectJoinDao projectJoinDao;

    @Autowired
    private CongressmanDao congressmanDao;

    @Autowired
    private ParticipationsDao participationsDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private LikesDao likesDao;

    @Autowired
    private UserDao userDao;

//    @RequestMapping(value = "/projects/{id}/comments", method = RequestMethod.GET)
//    public ResponseEntity<List<Comment>> getComments(@PathVariable("id") final Long id) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json; charset=utf-8");
//
//        List<Comment> comments = commentDao.findByProjectId(id);
//        for(Comment comment : comments) {
//            comment.setWriter(userDao.findById(comment.getUser_id()).get());
//        }
//
//        return new ResponseEntity<>(comments, headers, HttpStatus.OK);
//    }

    @RequestMapping(value = "/projects/{id}/comments/{index}", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> getCommentsPage(@PathVariable("id") final Long id, @PathVariable("index") final int index) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        Pageable pq = PageRequest.of(index, 10, Sort.Direction.DESC, "created_at");
        List<Comment> comments = commentDao.findByProjectId(id, pq);
        for(Comment comment : comments) {
            comment.setWriter(userDao.findById(comment.getUser_id()).get());
        }

        return new ResponseEntity<>(comments, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/{id}/comments", method = RequestMethod.POST)
    public ResponseEntity<Comment> post(@PathVariable("id") final Long id, @RequestBody Comment comment) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        User sessionedUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        comment.setCommentable_id(id);
        comment.setUser_id(sessionedUser.getId());
        String nowDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        comment.setCreated_at(nowDate);
        comment.setUpdated_at(nowDate);
        comment.setLikes_count(0);
        Comment dbComment = commentDao.save(comment);
        dbComment.setWriter(sessionedUser);
        return new ResponseEntity<>(dbComment, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/projects/{id}/comments/{cid}", method = RequestMethod.DELETE)
    public ResponseEntity<Comment> delete(@PathVariable("id") final Long id, @PathVariable("cid") final Long cid) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        User sessionedUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment dbComment = commentDao.findById(cid).get();
        if(sessionedUser.getId() != dbComment.getUser_id()) {
            return new ResponseEntity<>(dbComment, headers, HttpStatus.NOT_ACCEPTABLE);
        }
        commentDao.delete(dbComment);
        return new ResponseEntity<>(dbComment, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/{id}/comments/{cid}/likes", method = RequestMethod.PATCH)
    public ResponseEntity<Likes> like(@PathVariable("id") final Long id, @PathVariable("cid") final Long cid) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        User sessionedUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Likes dbLikes = likesDao.findByLikable_idAndUser_id(cid, sessionedUser.getId());
        if(dbLikes == null) {
            Likes likes = new Likes();
            String nowDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());

            likes.setLikable_id(cid);
            likes.setUser_id(sessionedUser.getId());
            likes.setCreated_at(nowDate);
            likes.setUpdated_at(nowDate);

            Comment dbComment = commentDao.findById(cid).get();
            dbComment.like();
            commentDao.save(dbComment);
            return new ResponseEntity<>(likesDao.save(likes), headers, HttpStatus.CREATED);
        }

        likesDao.delete(dbLikes);
        Comment dbComment = commentDao.findById(cid).get();
        dbComment.dislike();
        commentDao.save(dbComment);
        return new ResponseEntity<>(dbLikes, headers, HttpStatus.OK);
    }
}
