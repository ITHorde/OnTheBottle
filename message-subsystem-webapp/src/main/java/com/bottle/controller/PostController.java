package com.bottle.controller;


import com.bottle.model.DTO.*;
import com.bottle.model.entity.*;
import com.bottle.service.post.*;
import com.bottle.service.user.AllUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
public class PostController {
    private PostService postService;
    private AllUserService allUserService;
    private SecurityService securityService;
    private ImageService imageService;
    private CommentService commentService;
    private LikeService likeService;
    private GetterPost getterPost;


    @Autowired
    public PostController(PostService postService, AllUserService allUserService, SecurityService securityService, ImageService imageService, CommentService commentService, LikeService likeService, GetterPost getterPost) {
        this.postService = postService;
        this.allUserService = allUserService;
        this.securityService = securityService;
        this.imageService = imageService;
        this.commentService = commentService;
        this.likeService = likeService;
        this.getterPost = getterPost;
    }

    @RequestMapping(value = "/getPosts", params = "userId", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> listAllPosts(@RequestParam("userId") UUID id) {
        List<Post> posts = getterPost.getPosts( id );

        return new ResponseEntity<>( posts, HttpStatus.OK );


    }

    @RequestMapping(path = "/savePost", method = RequestMethod.POST)
    public ResponseEntity<Void> savePost(@RequestBody RequestDTO requestDTO) {
        Post post = new Post();
        User user = allUserService.getUser( requestDTO.getPostDTO().getUser_id() );
        Security security = securityService.getSecurityByDescription( requestDTO.getPostDTO().getSecurity().getDescription() );
        if (post.getDate() == null)
            post.setDate( new Date() );
        post.setSecurity( security );
        post.setTitle( requestDTO.getPostDTO().getTitle() );
        post.setUser( user );
        post.setText( requestDTO.getPostDTO().getText() );
        post.setIsDeleted( false );
        postService.addPost( post );
        return new ResponseEntity<>( HttpStatus.OK );
    }


    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = allUserService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT );
        }
        return new ResponseEntity<>( users, HttpStatus.OK );
    }

    @RequestMapping(value = "/getSecurities", method = RequestMethod.GET)
    public ResponseEntity<List<Security>> listAllSecurities() {
        List<Security> securities = securityService.getSecurities();
        if (securities.isEmpty()) {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT );
        }
        return new ResponseEntity<>( securities, HttpStatus.OK );
    }

    @RequestMapping(value = "/deletePost", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePost(@RequestParam("id") UUID postId) {
        Post post = postService.getPost( postId );
        if (post == null) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        }
        post.setIsDeleted( true );
        postService.addPost( post );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping(path = "/updatePost", method = RequestMethod.POST)
    public ResponseEntity<Void> updatePost(@RequestBody RequestDTO requestDTO) {
        System.out.println( "Updating Post " );
        Post post = postService.getPost( requestDTO.getPostDTO().getId() );
        if (post == null) {
            System.out.println( "Post with id " + requestDTO.getPostDTO().getId() + " not found" );
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        }
        Security security = securityService.getSecurityByDescription( requestDTO.getPostDTO().getSecurity().getDescription() );
        post.setTitle( requestDTO.getPostDTO().getTitle() );
        post.setText( requestDTO.getPostDTO().getText() );
        post.setSecurity( security );
        post.setId( requestDTO.getPostDTO().getId() );
        postService.addPost( post );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping(path = "/saveComment", method = RequestMethod.POST)
    public ResponseEntity<List<Comment>> saveComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = new Comment();
        User user = allUserService.getUser( commentDTO.getUser_id() );
        Post post = postService.getPost( commentDTO.getPost_id() );
        if (comment.getDate() == null)
            comment.setDate( new Date() );
        comment.setText( commentDTO.getComment() );
        comment.setPost( post );
        comment.setUser( user );
        comment.setIsDeleted( false );
        commentService.addComment( comment );
        List<Comment> comments = commentService.getComments( commentDTO.getPost_id() );
        return new ResponseEntity<>( comments, HttpStatus.OK );
    }

    @RequestMapping(value = "/getComments", params = "postId", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> listAllComments(@RequestParam("postId") UUID postId) {
        List<Comment> comments = commentService.getComments( postId );
        if (comments.isEmpty()) {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT );
        }
        return new ResponseEntity<>( comments, HttpStatus.OK );
    }

    @RequestMapping(value = "/deleteComment", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteComment(@RequestParam("id") UUID commentId) {
        Comment comment = commentService.getCommentById( commentId );
        if (comment == null) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        }
        comment.setIsDeleted( true );
        commentService.addComment( comment );
        return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }

    @RequestMapping(path = "/addLike", method = RequestMethod.POST)
    public ResponseEntity<Void> addLike(@RequestBody LikeDTO likeDTO) {
        Post post = postService.getPost( likeDTO.getPost_id() );
        User user = allUserService.getUser( likeDTO.getUser_id() );
        boolean proverka = likeService.exists( likeDTO.getPost_id(), likeDTO.getUser_id() );
        if (proverka) {
            System.out.println( "Ocenka est" );
            return new ResponseEntity<>( HttpStatus.NO_CONTENT );
        } else {
            System.out.println( "Ocenki net,dobavlu" );
            Like like = new Like();
            like.setStatus( likeDTO.getStatus() );
            like.setPost( post );
            like.setUser( user );
            likeService.save( like );
            return new ResponseEntity<>( HttpStatus.OK );
        }
    }

    @RequestMapping(value = "/getLikes", params = "postId", method = RequestMethod.GET)
    public ResponseEntity<List<Like>> listAllLikes(@RequestParam("postId") UUID postId) {
        List<Like> likes = likeService.findByPostId( postId );
        if (likes.isEmpty()) {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT );
        }
        return new ResponseEntity<>( likes, HttpStatus.OK );
    }
}

