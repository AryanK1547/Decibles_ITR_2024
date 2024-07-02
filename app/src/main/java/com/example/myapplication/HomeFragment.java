package com.example.myapplication;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    TextView trackTitle,artistName,initialTime,totalTime;
    SeekBar seekBar;
    ImageView skipBack,skipForwd,secondsBack,secondsForwd,Cover;

    private ImageButton playbutton;
    private int currentIndex=0;
    MediaPlayer mediaPlayer;
    private ArrayList<Integer> songPlaylist;
    private ArrayList<Integer> coverList;
    private ArrayList<Integer> trackTitleList;
    private ArrayList<Integer> trackArtistList;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment(ArrayList<Integer> trackTitleList, ArrayList<Integer> trackArtistList) {
        // Required empty public constructor
        this.trackTitleList = trackTitleList;
        this.trackArtistList = trackArtistList;
    }

    public HomeFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
         playbutton = view.findViewById(R.id.play_button);
          trackTitle = view.findViewById(R.id.track_title);
           artistName = view.findViewById(R.id.artist_name);
            secondsBack = view.findViewById(R.id.seconds_backward_btn);
             secondsForwd = view.findViewById(R.id.seconds_forward_btn);
              skipBack = view.findViewById(R.id.skip_previous_button);
             skipForwd = view.findViewById(R.id.skip_next_button);
              initialTime = view.findViewById(R.id.tvInitialTime);
               totalTime = view.findViewById(R.id.tvTotalTime);
                seekBar = view.findViewById(R.id.playback_seekbar);
                Cover = view.findViewById(R.id.album_art);


        songPlaylist = new ArrayList<>();
        songPlaylist.add(0,R.raw.rag_bhairavi_punjabi);
        songPlaylist.add(1,R.raw.lehra_do);
        songPlaylist.add(2,R.raw.saaiyaan);
        songPlaylist.add(3,R.raw.behti_hawa_sa_tha_vo);
        songPlaylist.add(4,R.raw.surili_akhiyo_wale);


        coverList =new ArrayList<>();
        coverList.add(0,R.drawable.cover_one);
        coverList.add(1,R.drawable.lehra_do_cover);
        coverList.add(2,R.drawable.saaiyaa_cover);
        coverList.add(3,R.drawable.behti_hawa_sa_cover);
        coverList.add(4,R.drawable.surili_akhiyo_wale_cover);

        trackTitleList = new ArrayList<>();
        trackTitleList.add(R.string.india_s_classical);
        trackTitleList.add(R.string.lehraDo);
        trackTitleList.add(R.string.saayiaa);
        trackTitleList.add(R.string.behtihawasa);
        trackTitleList.add(R.string.suriliakhiyowale);

        trackArtistList = new ArrayList<>();
        trackArtistList.add(R.string.aryan_khode);
        trackArtistList.add(R.string.artistlehraDo);
        trackArtistList.add(R.string.artistsaayiaa);
        trackArtistList.add(R.string.artistbehtihawasa);
        trackArtistList.add(R.string.artistsuriliakhiyowale);

        updateUI(currentIndex);
        skipForwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex+1)%songPlaylist.size();
                updateUI(currentIndex);
                playSong(currentIndex);
            }
        });

          skipBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex-1)%songPlaylist.size();
                updateUI(currentIndex);
                playSong(currentIndex);
            }
        });



        mediaPlayer = MediaPlayer.create(getActivity(),songPlaylist.get(currentIndex));

       playbutton.setImageResource(R.drawable.play_button_asset);
       playbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                if (mediaPlayer!=null && mediaPlayer.isPlaying()) {
                    // Set to pause button image

                     playbutton.setImageResource(R.drawable.play_button_asset);
                   mediaPlayer.pause();
                } else {
                    // Set to play button image
                   playbutton.setImageResource(R.drawable.pause_button_asset);
                    mediaPlayer.start();
                }
            }
       });

        // Inflate the layout for this fragment

      return view;
    }
      private void updateUI(int index) {
        Cover.setImageResource(coverList.get(index));
        trackTitle.setText(trackTitleList.get(index));
        artistName.setText(trackArtistList.get(index));
    }
     private void playSong(int index) {
        // Release any existing media player
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        // Create a new media player and start playback
        mediaPlayer = MediaPlayer.create(getActivity(), songPlaylist.get(index));
        mediaPlayer.start();
    }
}