package com.example.myapplication;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    TextView trackTitle,artistName,initialTime,totalTime;
    SeekBar seekBar,volume_bar;
    ImageView skipBack,skipForwd,secondsBack,secondsForwd,Cover,loopBtn,lyricsBtn,volume;


    private ImageButton playbutton;
    private int currentIndex=0;
    MediaPlayer mediaPlayer;
    private static int sTime=0,tTime=0,oTime=0,bTime=5000,fTime=5000;

    Handler handler =new Handler();
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
                loopBtn=view.findViewById(R.id.loop_song_button);
                lyricsBtn=view.findViewById(R.id.song_lyrics_button);
                volume=view.findViewById(R.id.song_volume_button);
                volume_bar=view.findViewById(R.id.volume_seekbar);

                volume_bar.setVisibility(View.INVISIBLE);



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

        loopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        skipForwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playbutton.setImageResource(R.drawable.pause_button_asset);
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();

                }
                if (currentIndex <= songPlaylist.size()-1) {
                    currentIndex = (currentIndex + 1) % songPlaylist.size();
                    updateUI(currentIndex);
                    playSong(currentIndex);

                }
                else{
                    currentIndex=0;
                    updateUI(currentIndex);
                    playSong(currentIndex);
                }
            }
        });

          skipBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  playbutton.setImageResource(R.drawable.pause_button_asset);
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();

                }

                if (currentIndex > 0) {
                    currentIndex = (currentIndex - 1) % songPlaylist.size();
                    updateUI(currentIndex);
                    playSong(currentIndex);
                }
                else{
                    currentIndex=songPlaylist.size()-1;
                    updateUI(currentIndex);
                    playSong(currentIndex);
                }
            }
        });
          secondsBack.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(sTime > 0){
                      int JumpBack=sTime-bTime;
                      mediaPlayer.seekTo(JumpBack);
                  }
                  else{
                      Toast.makeText(getActivity(), "Cannot go back further", Toast.LENGTH_SHORT).show();
                  }
              }
          });
           secondsForwd.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(sTime > 0){
                      int JumpForwd=sTime+fTime;
                      mediaPlayer.seekTo(JumpForwd);
                  }
                  else{
                      Toast.makeText(getActivity(), "Cannot go forward further ", Toast.LENGTH_SHORT).show();
                  }
              }
          });


          mediaPlayer = MediaPlayer.create(getActivity(),songPlaylist.get(currentIndex));
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               if(fromUser){
                   int jump=progress;
                   mediaPlayer.seekTo(jump);
               }
           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {

           }
       });


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
                 tTime = mediaPlayer.getDuration();
                    sTime = mediaPlayer.getCurrentPosition();
                    if(oTime==0){
                        seekBar.setMax(tTime);
                    }
                     initialTime.setText(String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(sTime), //Total Minutes in minutes
                            TimeUnit.MILLISECONDS.toSeconds(sTime)-  //Remaining Seconds = Total Seconds - Minutes(seconds)
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))
                                    ));
                    totalTime.setText(String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(tTime), //Total Minutes in minutes
                            TimeUnit.MILLISECONDS.toSeconds(tTime)-  //Remaining Seconds = Total Seconds - Minutes(seconds)
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tTime))
                                    ));
                    handler.postDelayed(UpdateSongProgress,1000);
            }
       });
       volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volume_bar.setVisibility(View.VISIBLE);
                showVolumePopup(v);
            }
        });


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
        tTime = mediaPlayer.getDuration();
        sTime = mediaPlayer.getCurrentPosition();
        mediaPlayer.start();
    }
private void showVolumePopup(View anchorView) {
    // Inflate the popup layout

    // Initialize the SeekBar

    volume_bar.setMax(100);
    volume_bar.setProgress(50); // Set initial progress, adjust as necessary

    // Set up SeekBar listener
    volume_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            if (fromUser && mediaPlayer != null) {
                volume.setImageResource(R.drawable.volume_asset_white);

                float Volume= progress / 100f;
                mediaPlayer.setVolume(Volume, Volume);

            }
            if(progress==0){
                volume.setImageResource(R.drawable.mute_asset);
        }
        }


        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // Optional: Add any behavior you want when the user starts dragging
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // Optional: Add any behavior you want when the user stops dragging
        }
    });

}



 private Runnable UpdateSongProgress = new Runnable() {  //Will update Song Progress after 1 seconds
        @Override
        public void run() {
            sTime=mediaPlayer.getCurrentPosition();
            initialTime.setText(
                            String.format("%02d:%02d",TimeUnit.MILLISECONDS.toMinutes(sTime),
                            TimeUnit.MILLISECONDS.toSeconds(sTime)-
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))));
            seekBar.setProgress(sTime);

            handler.postDelayed(this,1000);
        }
    };
}