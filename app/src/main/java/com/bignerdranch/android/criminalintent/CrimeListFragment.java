package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Eduardo O'Neill on 6/7/2018.
 */
//View chapter 8 to review everything here.
public class CrimeListFragment extends Fragment {

    private static final String SUBTITLE_VISIBILITY = "subtitle";

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            savedInstanceState.getBoolean(SUBTITLE_VISIBILITY, false);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle instanceState){
        instanceState.putBoolean(SUBTITLE_VISIBILITY, mSubtitleVisible);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); //TODO Search for what a layout manager actually does.

        updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem item = menu.findItem(R.id.show_subtitle);
        if(mSubtitleVisible){
            item.setTitle(R.string.hied_subtitle);
        } else {
            item.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).add(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getID());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if(mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.setCrimes(crimes);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private void updateSubtitle(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);

        if(!mSubtitleVisible){
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);

    }

    private class NormalCrimeHolder extends CrimeHolder{

        public NormalCrimeHolder(LayoutInflater layoutInflater, ViewGroup parent){
            super(layoutInflater.inflate(R.layout.list_item_crime, parent, false));
        }
    }

    private class SeriousCrimeHolder extends CrimeHolder{
        private Button mCallPolice;

        public SeriousCrimeHolder(LayoutInflater layoutInflater, ViewGroup parent) {
            super(layoutInflater.inflate(R.layout.list_item_serious_crime, parent, false));

            mCallPolice = itemView.findViewById(R.id.call_police_button);
        }
    }

    private abstract class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImage;
        private Crime mCrime;

        public CrimeHolder(View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.crime_title);
            mDateTextView = itemView.findViewById(R.id.crime_date);
            mSolvedImage = itemView.findViewById(R.id.crime_solved);

            itemView.setOnClickListener(this);
        }

        public void bind(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            if(mCrime.isSolved())
                mSolvedImage.setVisibility(View.VISIBLE);
            else
                mSolvedImage.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onClick(View view){
            //Toast.makeText(getActivity(), mCrime.getTitle()+" clicked!", Toast.LENGTH_LONG).show();
            //Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getID()); To start activity without the pager
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getID());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//            switch (viewType){
//                case 0: return new NormalCrimeHolder(layoutInflater, parent);
//                case 1: return new NormalCrimeHolder(layoutInflater, parent);//return new SeriousCrimeHolder(layoutInflater, parent); Uncomment this line to add the Call 911 functionality.
//            }
            return new NormalCrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        public void setCrimes(List<Crime> crimes){
            mCrimes = crimes;
        }

//        @Override
//        public int getItemViewType(int position){
//           if(mCrimes.get(position).isRequiresPolice())
//               return 1;
//           else return 0;
//        }
    }
}
