package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Eduardo O'Neill on 6/6/2018.
 */
//This class is a singleton, which means that it is implemented in such a way that only one instance of the class can be created throughout the lifecycle of the application.
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mListOfCrimes;

    public static CrimeLab get(Context context){
        if(sCrimeLab == null)
            sCrimeLab = new CrimeLab(context);
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        mListOfCrimes = new ArrayList<>();
    }

    public List<Crime> getCrimes(){
        return mListOfCrimes;
    }

    public Crime getCrime(UUID id){
        for(Crime x: mListOfCrimes)
            if(x.getID().equals(id))
                return x;
        return null;
    }

    public void add(Crime crime){
        mListOfCrimes.add(crime);
    }
}
