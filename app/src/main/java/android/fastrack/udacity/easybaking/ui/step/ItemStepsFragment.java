package android.fastrack.udacity.easybaking.ui.step;

import android.annotation.SuppressLint;
import android.fastrack.udacity.easybaking.R;
import android.fastrack.udacity.easybaking.model.BakingConstanta;
import android.fastrack.udacity.easybaking.model.Steps;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by winzaldi on 8/31/17.
 */

public class ItemStepsFragment extends Fragment {


//    private TextView textView;
//    private ImageView imageView;

    private Steps steps;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;
    private SimpleExoPlayerView playerView;
    private SimpleExoPlayer player;


    public ItemStepsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        steps = Parcels.unwrap(getArguments().getParcelable(BakingConstanta.KEY_STEPS_OBJECT));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.receip_step, container, false);

        /**  OLD CODE
         *     imageView = (ImageView)rootView.findViewById(R.id.img_content_step);
         imageView.setImageResource(R.drawable.sandwiches);
         textView = (TextView)rootView.findViewById(R.id.tv_title_step);
         textView.setText(steps.getShortDescription()); */

        playerView = (SimpleExoPlayerView) rootView.findViewById(R.id.video_player);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_video__short_description);
        tvTitle.setText(steps.getShortDescription());
        TextView tvDescription = (TextView) rootView.findViewById(R.id.tv_video_description);
        tvDescription.setText(steps.getDescription());

        return rootView;

    }

    private void initializePlayer() {
        if (player == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(player);


            // Prepare the MediaSource.
            //Uri mediaUri = Uri.parse("file:///android_asset/dizzy.mp4"); // for development
            String strUlr = steps.getVideoURL();
            if (!TextUtils.isEmpty(strUlr)) {
                Uri mediaUri = Uri.parse(strUlr);
                String userAgent = Util.getUserAgent(getContext(), "BakingApp");
                MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                        getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
                player.prepare(mediaSource);
                player.setPlayWhenReady(true);
            } else {
                Toast.makeText(getContext(), "Video Not Available", Toast.LENGTH_LONG).show();
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

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initializePlayer();
        }

    }
}
