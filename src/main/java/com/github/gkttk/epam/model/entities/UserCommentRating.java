package com.github.gkttk.epam.model.entities;

import java.util.Objects;

/**
 * Current class contains data of some comment which was liked/disliked by some user. This is necessary to control
 * multiple evaluations of the comment.
 */
public class UserCommentRating extends Entity {

    private final Long userId;
    private final Long commentId;
    private final boolean liked;

    public UserCommentRating(Long userId, Long commentId, boolean liked) {
        super(3 * userId + commentId);//todo stub
        this.userId = userId;
        this.commentId = commentId;
        this.liked = liked;
    }


    public Long getUserId() {
        return userId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public boolean isLiked() {
        return liked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserCommentRating that = (UserCommentRating) o;
        return liked == that.liked &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(commentId, that.commentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, commentId, liked);
    }
}
