package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Eduardo O'Neill on 6/4/2018.
 */

public class Crime {

    private UUID mID;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
//    private boolean mRequiresPolice;
    private String suspect;

    public Crime(){
        this(UUID.randomUUID());
    }

    public Crime(UUID id){
        mID = id;
        mDate = new Date();
    }

    public UUID getID() {
        return mID;
    }

    public void setID(UUID ID) {
        mID = ID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getSuspect() {
        return suspect;
    }

    public void setSuspect(String suspect) {
        this.suspect = suspect;
    }


//    public boolean isRequiresPolice() {
//        return mRequiresPolice;
//    }
//
//    public void setRequiresPolice(boolean requiresPolice) {
//        mRequiresPolice = requiresPolice;
//    }
}
