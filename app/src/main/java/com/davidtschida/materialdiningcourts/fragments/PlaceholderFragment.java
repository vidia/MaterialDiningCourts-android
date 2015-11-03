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
import com.davidtschida.materialdiningcourts.adapters.MealsListAdapter;
import com.davidtschida.purduemenu.MenusApi;
import com.davidtschida.purduemenu.models.DayMenu;
import com.davidtschida.purduemenu.models.Meal;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_DININGCOURT_NAME = "section_number";
    private static final String TAG = "PlaceholderFragment";
    private RecyclerView mMealsRecycleView;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(String diningCourt) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DININGCOURT_NAME, diningCourt);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MenusApi.getApiService().getDiningMenu(getArguments().getString(ARG_DININGCOURT_NAME), "11-3-15")
                .enqueue(new Callback<DayMenu>() {
                    @Override
                    public void onResponse(Response<DayMenu> response, Retrofit retrofit) {
                        Log.d(TAG, "Got a response for the menu");
                        List<Meal> meals = response.body().getMeals();

                        Log.d(TAG, "There are (" + meals.size() + ") meals.");
                        MealsListAdapter mealsAdapter = new MealsListAdapter(meals);
                        mMealsRecycleView.setAdapter(mealsAdapter);
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meal_view, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getString(ARG_DININGCOURT_NAME)));

        mMealsRecycleView = (RecyclerView) rootView.findViewById(R.id.mealsList);
        mMealsRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }
}