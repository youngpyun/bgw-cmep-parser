package com.verdeeco.models.device;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.verdeeco.models.loadprofile.TaggedObject;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.bson.types.ObjectId;

@Entity(value = "alarms", noClassnameStored = true)
public class DeviceAlarm extends TaggedObject implements Serializable {

    @Id
    private ObjectId id;

    @Property("a")
    private String alarm;

    public DeviceAlarm(final String deviceNumber, final String alarmType, final Date insertTime) {

        super(deviceNumber, insertTime);

        alarm = alarmType;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }
}
