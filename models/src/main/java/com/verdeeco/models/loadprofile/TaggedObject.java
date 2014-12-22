package com.verdeeco.models.loadprofile;

import java.util.Date;
import java.util.Map;

import org.mongodb.morphia.annotations.*;
import org.bson.types.ObjectId;

public abstract class TaggedObject {

    @Property("d")
    private final String device;

    @Property("eT")
    private final Date endTime;

    @Transient
    private String idString;

    @Embedded("t")
    private Map<String, String> tags;

    public TaggedObject(final String device, final Date endTime) {
        this.device = device;
        this.endTime = endTime;
    }

    public String getDevice() {
        return device;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }


}
