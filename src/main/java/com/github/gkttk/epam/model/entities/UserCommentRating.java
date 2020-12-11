package com.github.gkttk.epam.model.entities;

import com.github.gkttk.epam.model.enums.CommentEstimate;

import java.util.Objects;

public class UserCommentRating extends Entity {

    private final Long userId;
    private final Long commentId;
    private final CommentEstimate estimate;

    public UserCommentRating(Long userId, Long commentId, CommentEstimate estimate) {
        super(3 * userId + commentId);//todo stub
        this.userId = userId;
        this.commentId = commentId;
        this.estimate = estimate;
    }


    public Long getUserId() {
        return userId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public CommentEstimate getEstimate() {
        return estimate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        UserCommentRating that = (UserCommentRating) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(commentId, that.commentId) &&
                estimate == that.estimate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, commentId, estimate);
    }
}
