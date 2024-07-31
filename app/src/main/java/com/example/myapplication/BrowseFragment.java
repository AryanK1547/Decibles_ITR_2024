package com.example.myapplication;

import android.media.MediaController2;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrowseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrowseFragment extends Fragment {

        private View bottomNavigationView;
         private GestureDetector gestureDetector;
         BottomAppBar bottomAppBar;
    FloatingActionButton floatingActionButton;
    ImageSlider browserSlide;
    VideoView browserVidios;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BrowseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BrowseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BrowseFragment newInstance(String param1, String param2) {
        BrowseFragment fragment = new BrowseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_browse, container, false);

       browserSlide=view.findViewById(R.id.isBrowser_image_slider);
       browserVidios=view.findViewById(R.id.videoViewRelative);

       String VideoPath ="android.resource://"+getActivity().getPackageName()+"/raw/rohit_shrama";

       browserVidios.setVideoPath(VideoPath );
     browserVidios.start();


        ArrayList<SlideModel> slideShowImages=new ArrayList<>();
        slideShowImages.add(new SlideModel(R.drawable.cover_one,"Classical", ScaleTypes.FIT));
        slideShowImages.add(new SlideModel(R.drawable.saaiyaa_cover,"Sufi", ScaleTypes.FIT));
        slideShowImages.add(new SlideModel(R.drawable.behti_hawa_sa_cover,"Relaxed", ScaleTypes.FIT));

        browserSlide.setImageList(slideShowImages);
        browserSlide.setSlideAnimation(AnimationTypes.ZOOM_OUT);

        MediaController mediaController2 = new MediaController(getActivity());
        browserVidios.setMediaController(mediaController2);


       return view;
    }
    private void toggleBottomNavigationVisibility() {
    if (bottomNavigationView.getVisibility() == View.VISIBLE) {
        bottomNavigationView.setVisibility(View.GONE);
        bottomAppBar.setVisibility(View.GONE);
        floatingActionButton.setVisibility(View.GONE);
        hideActionBar(true);
    } else {
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomAppBar.setVisibility(View.VISIBLE);
        floatingActionButton.setVisibility(View.VISIBLE);
        hideActionBar(false);
    }

}
private void hideActionBar(boolean hide) {
    // Check if the activity is not null
    if (getActivity() != null) {
        if (hide) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }
    }
}
}