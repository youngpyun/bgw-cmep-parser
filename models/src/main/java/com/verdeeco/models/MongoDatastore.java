package com.verdeeco.models;

import java.lang.annotation.Annotation;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Entity;

import com.mongodb.MongoClient;

public class MongoDatastore {

    /** Datastore name */
    private static final String MONGO_DATASTORE_NAME = "verdeeco_test";

    /** MongoDB server name/addr */
    private static final String MONGODB_SERVER_NAME = "192.168.99.99";

    /** MongoDB server port */
    private static final int MONGODB_SERVER_PORT = 27017;

    /** Mongo client */
    private static MongoClient mongo;

    /** Morphia handle */
    private static Morphia morphia;

    /** Datastore */
    private static Datastore ds;

    /** Keep track of classes that have been mapped */
    private static HashSet<Class> mappedClasses = new HashSet();

    static {
        try {
            mongo = new MongoClient(MONGODB_SERVER_NAME, MONGODB_SERVER_PORT);
            morphia = new Morphia();
            ds = morphia.createDatastore(mongo, MONGO_DATASTORE_NAME);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /** Make the constructor private to enforce use of static methods */
    private MongoDatastore() {
    }

    /**
     * Save an object to Mongo datastore
     *
     * @param m
     *            Object to save
     */
    public static void save(Object m) {
        Class clz = m.getClass();

        // check if the class is annotated by Morphia
        // There are other classes to check against, but not many
        Annotation annotation = clz.getAnnotation(Entity.class);
        if (annotation != null) {
            addClass(clz);
            ds.save(m);
        } else {
            // signal this object is not mapped to MongoDb
        }
    }

    /**
     * Keep track of which class has been added to Morphia
     *
     * @param clz
     *            The class
     */
    private static synchronized void addClass(Class clz) {
        if (mappedClasses.contains(clz)) {
            return;
        }
        morphia.map(clz);
        mappedClasses.add(clz);
    }
}
