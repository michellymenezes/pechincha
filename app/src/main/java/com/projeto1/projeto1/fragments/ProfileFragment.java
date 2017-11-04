package com.projeto1.projeto1.fragments;

import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.projeto1.projeto1.R;
import com.projeto1.projeto1.SharedPreferencesUtils;
import com.projeto1.projeto1.models.User;

/**
 * Created by Laptop on 26/07/2017.
 */

public class ProfileFragment extends Fragment {

    public static final String TAG = "PROFILE_FRAGMENT";
    private View mview;
    private TextView name;
    private ImageView image;
    private TextView createdAt;
    private TextView gender;
    private RatingBar reputation;
    User userToShow;

    public ProfileFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static Fragment getInstance() {
        Fragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_profile, container, false);

        userToShow = SharedPreferencesUtils.getUserSelected(getContext());

        if (userToShow!=null){
            LinearLayout myProfileInf = (LinearLayout) mview.findViewById(R.id.my_profile_inf);

            if (userToShow.equals(SharedPreferencesUtils.getUser(getContext()))){
                mview.findViewById(R.id.my_profile).setVisibility(View.VISIBLE);
                myProfileInf.setVisibility(View.VISIBLE);

                if (userToShow.getBirthday()!=null){
                   // TextView bday = (TextView) mview.findViewById(R.id.bday);
                   // bday.setText(userToShow.getBirthday());
                }

                if (userToShow.getEmail()!=null){
                   // TextView email = (TextView) mview.findViewById(R.id.email);
                    //TODO não quer aparecer o email mas no Log aparece
                   // email.setText(userToShow.getEmail());
                    Log.d(TAG, userToShow.getEmail());
                }

                if (userToShow.getPreferences()!=null){
                    //TextView preferences = (TextView) mview.findViewById(R.id.email);
                    //TODO Ajustar visualização de preferências
                    //preferences.setText(userToShow.getPreferences().toString());
                }

            } else {
                myProfileInf.setVisibility(View.GONE);
            }

            image = (ImageView) mview.findViewById(R.id.image);

            if (userToShow.getName()!=null){
                name = (TextView) mview.findViewById(R.id.user_name);
                name.setText(userToShow.getName());
            }

            if (userToShow.getCreatedAt()!=null){
                createdAt = (TextView) mview.findViewById(R.id.created_at);
                String [] date =  userToShow.getCreatedAt().substring(0,10).split("-");
                if(date.length != 3){
                    date =  userToShow.getCreatedAt().substring(0,10).split(" ");
                }
                String createdate = date[2] +"-"+ date[1] +"-"+ date[0];
                createdAt.setText(createdate);
            }

            if (userToShow.getGender()!=null){
                gender = (TextView) mview.findViewById(R.id.gender);
                gender.setText(userToShow.getGender());
            }

            if (userToShow.getReputation()!=null){
                reputation = (RatingBar) mview.findViewById(R.id.reputation);
                reputation.setRating(Float.valueOf(userToShow.getReputation().toString()));
            }

        }




        return mview;

    }
}
