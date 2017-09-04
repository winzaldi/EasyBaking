package android.fastrack.udacity.easybaking.ui.main;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.fastrack.udacity.easybaking.IdlingResource.SimpleIdlingResource;
import android.fastrack.udacity.easybaking.R;
import android.fastrack.udacity.easybaking.model.Baking;
import android.fastrack.udacity.easybaking.services.BakingServiceFactory;
import android.fastrack.udacity.easybaking.services.BakingServices;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
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

        // Di Comment Dulu
        mIdlingResource.setIdleState(false);
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
                Log.e(TAG, t.getLocalizedMessage());
                progressBar.setVisibility(View.GONE);
                mIdlingResource.setIdleState(true);
            }
        });



    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
