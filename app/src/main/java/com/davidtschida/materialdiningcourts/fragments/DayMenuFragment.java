package com.davidtschida.materialdiningcourts.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davidtschida.materialdiningcourts.R;
import com.davidtschida.materialdiningcourts.adapters.FoodItemsAdapter;
import com.davidtschida.purduemenu.MenusApi;
import com.davidtschida.purduemenu.models.DayMenu;
import com.davidtschida.purduemenu.models.FoodItem;
import com.davidtschida.purduemenu.models.Meal;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class DayMenuFragment extends Fragment implements Callback<DayMenu> {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_DININGCOURT_NAME = "ARG_DININGCOURT_NAME";
    private static final String TAG = "DayMenuFragment";
    private static final String ARG_MEAL_NAME = "ARG_MEAL_NAME";
    private static final String ARG_DATE = "ARG_DATE";

    private RecyclerView mEntreeItemsRecycleView;
    private String mDiningCourt;
    private LocalDate mLocalDate;
    private String mMealName;

    private boolean haveQueriedApi = false;
    private Meal mMeal;
    private List<FoodItem> mFoodItems;
    private FoodItemsAdapter mFoodItemsAdapter;
    private String mHoursDisplayText;
    private String mMealString;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DayMenuFragment newInstance(String diningCourt, String meal, LocalDate LocalDate) {
        DayMenuFragment fragment = new DayMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DININGCOURT_NAME, diningCourt);
        if(meal != null)
            args.putString(ARG_MEAL_NAME, meal);
        args.putSerializable(ARG_DATE, LocalDate);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Returns a new instance of the fragment that will use the current time to choose which meal is being displayed.
     * @param diningCourt the dining court to pull info for
     * @param LocalDate the date
     * @return a new fragment instance with args assigned.
     */
    public static DayMenuFragment newInstance(String diningCourt, LocalDate LocalDate) {
        return newInstance(diningCourt, null, LocalDate);
    }

    public DayMenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDiningCourt = getArguments().getString(ARG_DININGCOURT_NAME);
        mMealName = getArguments().getString(ARG_MEAL_NAME); //can be null;
        mLocalDate = (LocalDate) getArguments().getSerializable(ARG_DATE);

        if (mDiningCourt == null || mLocalDate == null) { //mealname can be null and will be calculated based on time
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
        if(mMeal == null) {
            Timber.i("Requesting getDiningMenu(" + mDiningCourt + ", " + mLocalDate.toString("MM-dd-yyyy") + ")");
            MenusApi.getApiService().getDiningMenu(mDiningCourt, mLocalDate.toString("MM-dd-yyyy"))
                    .enqueue(this);
        }
    }

    private void setAdapterForFoodItems() {
        if(mFoodItemsAdapter == null && mFoodItems != null && mFoodItems.size() > 0) {
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
            refreshDisplayText();
        }
    }

    private void refreshDisplayText() {
        if(getView() != null) {
            TextView hoursDisplay = (TextView) getView().findViewById(R.id.hours_display);
            hoursDisplay.setText(mHoursDisplayText);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Fragment " + mDiningCourt + " onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_meal_view, container, false);

        mEntreeItemsRecycleView = (RecyclerView) rootView.findViewById(R.id.entreesList);
        mEntreeItemsRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //mEntreeItemsRecycleView.setNestedScrollingEnabled(false);
        mEntreeItemsRecycleView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onResponse(Response<DayMenu> response, Retrofit retrofit) {
        Log.d(TAG, "Got a response for the menu");
        haveQueriedApi = true;

        if(mMealName == null) {
            Log.d(TAG + mDiningCourt, "No meal was given, getting meal from time");
            mMeal = response.body().getMealForTime(LocalTime.now());
            Log.d(TAG + mDiningCourt, "Received meal " + ((mMeal == null) ? "NULL" : mMeal.getName()));

            if(mMeal == null) {


                //This will loops infinitely without a day context attached to meals.
                /*LocalDate nextDay = mLocalDate.plusDays(1);
                MenusApi.getApiService().getDiningMenu(mDiningCourt, mLocalDate.toString("MM-dd-yyyy"))
                        .enqueue(this);*/
                if(getView() != null) {
                    setHoursDisplayText(mDiningCourt + " has no more meals today");
                }
                return;
            }
        } else {
            Log.d(TAG + mDiningCourt, "Using given meal");
            mMeal = response.body().getMealByName(mMealName);
        }
        if(getView() != null) {
            if(mMeal.startsAfter(LocalTime.now())) {
                setHoursDisplayText(mMeal.getName() + " opens at " + mMeal.getHours().getStartLocalTime().toString("HH:mm"));
            } else if (mMeal.containsTime(LocalTime.now())) {
                setHoursDisplayText(mMeal.getName() + " ends at " + mMeal.getHours().getStartLocalTime().toString("HH:mm"));
            }
        }

        mFoodItems = mMeal.getAllFoodItems();
        Log.d(TAG, "There are (" + mFoodItems.size() + ") total foods.");
        setAdapterForFoodItems();
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e(TAG, "Menus API threw an error after requesting getDiningMenu(" + mDiningCourt + ", " + mLocalDate.toString("MM-dd-yyyy") + ")", t);
    }

    public void setHoursDisplayText(String hoursDisplayString) {
        this.mHoursDisplayText = hoursDisplayString;
        refreshDisplayText();
    }

    public void setMeal(String meal) {
        mMealString = meal;
        mMeal = null;
        mFoodItems = null;
        mHoursDisplayText = null;

        if(haveQueriedApi) {
            populateDataFromApi();
        }
    }
}