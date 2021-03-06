package com.bottle.model.repository;

import com.bottle.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findAllByIsDeletedIsFalseAndPostId(UUID postId);

    boolean existsByIdAndUserId(UUID commentId, UUID userId);
}