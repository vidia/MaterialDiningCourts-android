package com.davidtschida.purduemenu;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by david on 10/26/2015.
 */
public class MenusApi {
    private static MenuService menuService = null;

    public static MenuService getApiService() {
        if (menuService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.hfs.purdue.edu/menus/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            menuService = retrofit.create(MenuService.class);
        }
        return menuService;
    }
}
