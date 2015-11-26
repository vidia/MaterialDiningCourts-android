package com.davidtschida.purduemenu;

import com.davidtschida.purduemenu.models.DayMenu;
import com.davidtschida.purduemenu.models.FoodItem;
import com.davidtschida.purduemenu.models.FoodItemSchedule;
import com.davidtschida.purduemenu.models.Locations;
import com.davidtschida.purduemenu.models.SearchResults;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by david on 10/20/2015.
 */
public interface MenuService {
    @GET("locations")
    Call<Locations> getDiningLocations();

    //@GET("/retail/")
    //Call<List<RetailLocation>> getRetailLocations();

    @GET("locations/{location}/{date}")
        //"mm-dd-yyyy"
    Call<DayMenu> getDiningMenu(@Path("location") String location, @Path("date") String date);

    @GET("/items/search/{terms}/")
    Call<SearchResults> searchItems(@Path("terms") String searchTerms);

    @GET("/items/searchUpcoming/{terms}/")
    Call<SearchResults> searchUpcomingItems(@Path("terms") String searchTerms);

    @GET("/items/{guid}/")
    Call<FoodItem> getItem(@Path("guid") String guid);

    @GET("/items/{guid}/schedule/")
    Call<FoodItemSchedule> getItemSchedule(@Path("guid") String guid);
}
