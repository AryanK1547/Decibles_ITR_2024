package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YourLibraryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YourLibraryFragment extends Fragment {
    VideoView libraryView;
      BottomAppBar bottomAppBar;
    FloatingActionButton floatingActionButton;
    private View bottomNavigationView;
    private GestureDetector gestureDetector;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public YourLibraryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YourLibraryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YourLibraryFragment newInstance(String param1, String param2) {
        YourLibraryFragment fragment = new YourLibraryFragment();
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
                 gestureDetector = new GestureDetector(getActivity(), new DoubleTapGestureListener(this::toggleBottomNavigationVisibility));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_your_library, container, false);
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView); // Assuming your bottom navigation has this ID
        bottomAppBar=getActivity().findViewById(R.id.bottomAppBar);
        //floatingActionButton=getActivity().findViewById(R.id.itfloatingbtnHome);
        libraryView=view.findViewById(R.id.videoViewRelative);
        view.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

         String VideoPath ="android.resource://"+getActivity().getPackageName()+"/raw/worcup_journey_india";

       libraryView.setVideoPath(VideoPath );
         libraryView.start();

        MediaController mediaController2 = new MediaController(getActivity());
        libraryView.setMediaController(mediaController2);



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