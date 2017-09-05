package android.fastrack.udacity.easybaking.ui.step;


import android.content.res.Configuration;
import android.content.res.Resources;
import android.fastrack.udacity.easybaking.R;
import android.fastrack.udacity.easybaking.model.Steps;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.parceler.Parcels;

/**
 * Created by winzaldi on 8/30/17.
 */

public class ScreenSlidePageFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    private static final String ARG_PAGE = "page";
    private static final String ARG_STEP = "steps";
    private SimpleExoPlayer player;


    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    private Steps steps;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;
    private SimpleExoPlayerView playerView;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(int pageNumber, Steps steps) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putParcelable(ARG_STEP, Parcels.wrap(steps));
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        steps = Parcels.unwrap(getArguments().getParcelable(ARG_STEP));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
//        ViewGroup rootView = (ViewGroup) inflater
//                .inflate(R.layout.item_steps, container, false);
//
//        ImageView imageView = (ImageView) rootView.findViewById(R.id.img_content_step);
//        imageView.setImageResource(R.drawable.sandwiches);
//        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_title_step);
//        tvTitle.setText(steps.getShortDescription());
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.receip_step, container, false);
        playerView = (SimpleExoPlayerView) rootView.findViewById(R.id.video_player);
        initializePlayer();
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_video__short_description);
        tvTitle.setText(steps.getShortDescription());
        TextView tvDescription = (TextView) rootView.findViewById(R.id.tv_video_description);
        tvDescription.setText(steps.getDescription());

        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }


    private void initializePlayer() {
        if (player == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(player);


            // Prepare the MediaSource.
            // Uri mediaUri = Uri.parse("file:///android_asset/dizzy.mp4"); //for development
            String strUlr = steps.getVideoURL();
            if (!TextUtils.isEmpty(strUlr) && strUlr.length() > 0) {
                Uri mediaUri = Uri.parse(steps.getVideoURL());
                String userAgent = Util.getUserAgent(getContext(), "BakingApp");
                MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                        getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
                player.prepare(mediaSource);
                player.setPlayWhenReady(true);
            }

        }

    }


    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.stop();
            player.release();
            player = null;

        }
    }

    private void hideSystemUi() {
        Resources res = getResources();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        } else {
            playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
}