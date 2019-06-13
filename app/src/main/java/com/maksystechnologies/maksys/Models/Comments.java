package com.maksystechnologies.maksys.Models;

public class Comments {

    String comment,id,created;

    public Comments(String comment, String id, String created) {
        this.comment = comment;
        this.id = id;
        this.created = created;
    }

    public Comments() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
