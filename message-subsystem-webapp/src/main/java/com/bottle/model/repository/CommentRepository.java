package com.bottle.model.repository;

import com.bottle.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findCommentsByPostId(UUID post_id);
    List<Comment> findAllByIsDeletedIsFalseAndPostId(UUID post_id);

    boolean existsByIdAndUserId(UUID commentId, UUID userId);
}