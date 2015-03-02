package com.example.rory.lightningsalefyp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rory on 02/12/2014.
 */
public class Bolt {

    private String id;
    private String Name;
    private String Comment;
    private String location;
    private String store_name;
    private Date expiry;
    private String tags;

    ArrayList<Bolt> setOfBolts = new ArrayList<Bolt>();

    private List<String> list;

    public Bolt(String boltId, String boltName, String boltComment, String boltLocation, String boltStore_name, Date boltExpiry, String boltTags){

        this.id = boltId;
        this.Name = boltName;
        this.Comment = boltComment;
        this.location = boltLocation;
        this.store_name = boltStore_name;
        this.expiry = boltExpiry;
        this.tags = boltTags;

        this.list = new ArrayList<String>();
        this.list.add(boltId);
        this.list.add(boltName);
        this.list.add(boltComment);
        this.list.add(boltLocation);
        this.list.add(boltStore_name);
        this.list.add(boltExpiry.toString());
        this.list.add(boltTags);


    }

    @Override
    public String toString() {
        return "ID: " + getId() +
                "\nName: " + getName() +
                "\nComment: " + getComment() +
                "\nLocation: " + getLocation() +
                "\nstore_name: " + getStore_name() +
                "\nexpiry: " + getExpiry() +
                "\ntags: " + getTags();
    }


    public String getId() {
        return id;
    }

    public void setId(String boltId) {
        this.id = boltId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String boltName) {
        this.Name = boltName;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String boltComment) {
        this.Comment = boltComment;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String boltLocation){
        this.location = boltLocation;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String boltStore_name) {
        this.store_name = boltStore_name;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date boltExpiry) {
        this.expiry = boltExpiry;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String boltTags) {
        this.tags = boltTags;
    }

}
