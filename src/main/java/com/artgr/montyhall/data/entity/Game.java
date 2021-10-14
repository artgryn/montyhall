package com.artgr.montyhall.data.entity;

import lombok.Builder;
import lombok.Data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@Builder
public class Game {

    private ObjectId id;
    private String winningBox;

    // to prevent cheating if user selected initially winning box after request re-trigger to show same box.
    private String emptyBox;
    private String initialBoxSelection;
    private String finalBoxSelection;

    // initial NULL thats why class instead of primitive. This flag kind of redundant because if initialBoxSelection == finalBoxSelection user won the game
    private Boolean isWinner;

    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;

}
