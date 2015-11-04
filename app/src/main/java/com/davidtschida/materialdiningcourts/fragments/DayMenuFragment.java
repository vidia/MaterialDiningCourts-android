package com.davidtschida.materialdiningcourts.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidtschida.materialdiningcourts.R;
import com.davidtschida.materialdiningcourts.adapters.FoodItemsAdapter;
import com.davidtschida.purduemenu.MenusApi;
import com.davidtschida.purduemenu.models.DayMenu;
import com.davidtschida.purduemenu.models.FoodItem;
import com.davidtschida.purduemenu.models.Meal;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A placeholder fragment containing a simple view.
 */
public class DayMenuFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_DININGCOURT_NAME = "ARG_DININGCOURT_NAME";
    private static final String TAG = "DayMenuFragment";
    private static final String ARG_MEAL_NAME = "ARG_MEAL_NAME";
    private static final String ARG_DATE_STRING = "ARG_DATE_STRING";

    private RecyclerView mEntreeItemsRecycleView;
    private String mDiningCourt;
    private String mDateString;
    private String mMealName;

    private boolean haveQueriedApi = false;
    private Meal mMeal;
    private List<FoodItem> mFoodItems;
    private FoodItemsAdapter mFoodItemsAdapter;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DayMenuFragment newInstance(String diningCourt, String meal, String dateString) {
        DayMenuFragment fragment = new DayMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DININGCOURT_NAME, diningCourt);
        args.putString(ARG_MEAL_NAME, meal);
        args.putString(ARG_DATE_STRING, dateString);
        fragment.setArguments(args);
        return fragment;
    }

    public DayMenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDiningCourt = getArguments().getString(ARG_DININGCOURT_NAME);
        mMealName = getArguments().getString(ARG_MEAL_NAME);
        mDateString = getArguments().getString(ARG_DATE_STRING);

        if (mDiningCourt == null || mMealName == null || mDateString == null) {
            //We need all this information to run this fragment properly.
            throw new RuntimeException("Arguments that are required for this fragment were not provided.");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Fragment " + mDiningCourt + " onStart()");

        if(!haveQueriedApi)
            populateDataFromApi();
    }

    private void populateDataFromApi() {
        Log.i(TAG, "Requesting getDiningMenu(" + mDiningCourt + ", " + mDateString + ")");
        MenusApi.getApiService().getDiningMenu(mDiningCourt, mDateString)
                .enqueue(new Callback<DayMenu>() {
                    @Override
                    public void onResponse(Response<DayMenu> response, Retrofit retrofit) {
                        Log.d(TAG, "Got a response for the menu");
                        haveQueriedApi = true;
                        mMeal = response.body().getMealByName(mMealName);

                        mFoodItems = mMeal.getAllFoodItems();
                        Log.d(TAG, "There are (" + mFoodItems.size() + ") total foods.");

                        setAdapterForFoodItems();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(TAG, "Menus API threw an error after requesting getDiningMenu(" + mDiningCourt + ", " + mDateString + ")", t);
                    }
                });
    }

    private void setAdapterForFoodItems() {
        if(mFoodItemsAdapter == null) {
            mFoodItemsAdapter = new FoodItemsAdapter(mFoodItems);
        }
        mEntreeItemsRecycleView.setAdapter(mFoodItemsAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Fragment " + mDiningCourt + " onResume()");

        if(haveQueriedApi) {
            setAdapterForFoodItems();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Fragment " + mDiningCourt + " onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_meal_view, container, false);

        mEntreeItemsRecycleView = (RecyclerView) rootView.findViewById(R.id.entreesList);
        mEntreeItemsRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }
}