package android.fastrack.udacity.easybaking.ui.main;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.fastrack.udacity.easybaking.BakingApplication;
import android.fastrack.udacity.easybaking.IdlingResource.SimpleIdlingResource;
import android.fastrack.udacity.easybaking.R;
import android.fastrack.udacity.easybaking.model.Baking;
import android.fastrack.udacity.easybaking.services.BakingServiceFactory;
import android.fastrack.udacity.easybaking.services.BakingServices;
import android.fastrack.udacity.easybaking.utils.ConnectivityReceiver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private BakingAdapter mAdapter;
    private ProgressBar progressBar;
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        progressBar = (ProgressBar) findViewById(R.id.pb_baking);

        progressBar.setVisibility(View.VISIBLE);
        //List<Baking> listOfBaking = FakeUtils.getBaking();
        recyclerView = (RecyclerView) findViewById(R.id.item_list);
        Resources res = getResources();
        int columnCount = res.getInteger(R.integer.column_portrait);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            columnCount = res.getInteger(R.integer.column_portrait);
        } else {
            columnCount = res.getInteger(R.integer.column_land);
        }
        final LinearLayoutManager layoutManager = new GridLayoutManager(this, columnCount);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new BakingAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        //mAdapter.setListOfBaking(listOfBaking);
        getIdlingResource();

        // Comment
        mIdlingResource.setIdleState(false);
        boolean connected = checkConnection();
        showSnack(connected);
        if (connected) {
            BakingServices services = new BakingServiceFactory().create();
            services.getBakingFrom().enqueue(new Callback<List<Baking>>() {
                @Override
                public void onResponse(Call<List<Baking>> call, final Response<List<Baking>> response) {
                    mAdapter.setListOfBaking(response.body());
                    progressBar.setVisibility(View.GONE);
                    mIdlingResource.setIdleState(true);
                }

                @Override
                public void onFailure(Call<List<Baking>> call, Throwable t) {
                    buildSnack(t.getLocalizedMessage(),Color.WHITE);
                    progressBar.setVisibility(View.GONE);
                    mIdlingResource.setIdleState(true);
                }
            });
            progressBar.setVisibility(View.GONE);
        }


    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private boolean checkConnection() {
        return ConnectivityReceiver.isConnected();
    }

    private void showSnack(boolean isConnected) {
        String message = "";
        int color = Color.WHITE;
        if (!isConnected) {
            message = "Sorry! Not connected to internet";
            color = Color.WHITE;
        }else{
            message = "Good! Connected to internet";
            color = Color.WHITE;
        }

        buildSnack(message, color);
    }

    private void buildSnack(String message, int color) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BakingApplication.getInstance().setConnectivityListener(this);
    }
}