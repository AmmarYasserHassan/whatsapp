package models;


import java.util.ArrayList;
import java.util.Date;

public class Story {

    /**
     * Story constructor
     */

    private String ownerMobile;
    private String type;
    private String link;
    private int duration;
    private Date expirationDate;
    private ArrayList viewedBy;

    public Story(String ownerMobile, String type, String source, int duration, Date expirationDate, ArrayList viewedBy){

        this.ownerMobile = ownerMobile;
        this.type = type;
        this.link = source;
        this.duration = duration;
        this.expirationDate = expirationDate;
        this.viewedBy = viewedBy;


    }

    public String getOwnerMobile() {
        return ownerMobile;
    }

    public void setOwnerMobile(String ownerMobile) {
        this.ownerMobile = ownerMobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public ArrayList getViewedBy() {
        return viewedBy;
    }

    public void setViewedBy(ArrayList viewedBy) {
        this.viewedBy = viewedBy;
    }

}
