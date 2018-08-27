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
    private boolean mRequiresPolice;

    public Crime(){
        mID = UUID.randomUUID();
        mDate = new Date();
        Random rand = new Random();
        mRequiresPolice = (rand.nextInt(2) == 0);
        rand = null;
    }

    public Crime(boolean requiresPolice){
        this();
        mRequiresPolice = requiresPolice;
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

    public boolean isRequiresPolice() {
        return mRequiresPolice;
    }

    public void setRequiresPolice(boolean requiresPolice) {
        mRequiresPolice = requiresPolice;
    }
}
