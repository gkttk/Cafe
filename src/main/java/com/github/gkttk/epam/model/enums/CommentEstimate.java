package com.github.gkttk.epam.model.enums;

public enum CommentEstimate {
    LIKE{
        public int changeRating(int oldRating){
            return ++oldRating;
        }
    },
    DISLIKE{
        public int changeRating(int oldRating){
            return --oldRating;
        }
    };

    public abstract int changeRating(int oldRating);


}
