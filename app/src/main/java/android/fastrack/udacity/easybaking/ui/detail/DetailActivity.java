package android.fastrack.udacity.easybaking.ui.detail;

import android.fastrack.udacity.easybaking.R;
import android.fastrack.udacity.easybaking.model.Baking;
import android.fastrack.udacity.easybaking.model.BakingConstanta;
import android.fastrack.udacity.easybaking.widget.EasyBakingWidgetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {
    public static final String TAG = DetailActivity.class.getSimpleName();
    private Baking baking;

    private RecyclerView rvIngredient, rvSteps;
    private IngredientAdapter mIngredientAdapter;
    private DetailStepAdapter mDetailStepAdapter;
    private TextView tvTitleIngredient;
    private ProgressBar progressBar;
    public static boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvTitleIngredient = (TextView) findViewById(R.id.tv_title_ingredient);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        if (savedInstanceState == null) {
            baking = Parcels.unwrap(getIntent().getParcelableExtra(BakingConstanta.KEY_BAKING_OBJECT));
        } else {
            baking = Parcels.unwrap(savedInstanceState.getParcelable(BakingConstanta.KEY_BAKING_OBJECT));
        }




        rvIngredient = (RecyclerView) findViewById(R.id.rv_detail_ingredient);
        mIngredientAdapter = new IngredientAdapter(this);
        rvIngredient.setAdapter(mIngredientAdapter);

        rvSteps = (RecyclerView) findViewById(R.id.rv_detail_steps);
        mDetailStepAdapter = new DetailStepAdapter(this, getSupportFragmentManager());
        rvSteps.setAdapter(mDetailStepAdapter);


        if (baking != null) {
            this.setTitle(baking.getName());
            Log.i(TAG, "onCreate: "+ tvTitleIngredient.getText().toString());
            mIngredientAdapter.setListOfIngredients(baking.getIngredients());
            mDetailStepAdapter.setListOfSteps(baking.getSteps());
        }

        if(findViewById(R.id.item_detail_container)!=null){
            mTwoPane = true;
        }


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BakingConstanta.KEY_BAKING_OBJECT, Parcels.wrap(baking));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
         if(id == R.id.action_add_to_widget){
             EasyBakingWidgetManager.updateBakingWidget(getApplicationContext(),baking);
             return true;
         }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
