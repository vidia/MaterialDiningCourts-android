package com.davidtschida.purduemenu.util;

import com.davidtschida.materialdiningcourts.eventbus.DateChosenEvent;
import com.davidtschida.materialdiningcourts.eventbus.EventBus;
import com.davidtschida.materialdiningcourts.eventbus.MealChosenEvent;
import com.davidtschida.materialdiningcourts.eventbus.ShowSnackbarEvent;
import com.davidtschida.purduemenu.MenusApi;
import com.davidtschida.purduemenu.models.DayMenu;
import com.davidtschida.purduemenu.models.Locations;
import com.davidtschida.purduemenu.models.Meal;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by david on 12/14/2015.
 */
public class DefaultMealChooser implements Action1<ArrayList<DayMenu>> {

    public void initiateDefaultMealSelection() {
        Observable<Locations> locationsObservable = MenusApi.getApiService().getDiningLocationsObservable();

        locationsObservable.flatMapIterable(Locations::getLocations)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .flatMap(diningLocation -> MenusApi.getApiService().getDiningMenuObservable(diningLocation.getName(), "12-4-2015"))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(dayMenu -> {
                    Timber.i("Have a dayMenu for %s", dayMenu.getLocation());
                })
                .collect((Func0<ArrayList<DayMenu>>) ArrayList::new, ArrayList::add)
                .subscribe(this, throwable -> {
                    Timber.e(throwable, "An error was thrown in RxJava or Retrofit.");
                    EventBus.getBus().post(new ShowSnackbarEvent("There was an error choosing a meal based on the time"));
                    EventBus.getBus().post(new DateChosenEvent(LocalDate.now()));
                    EventBus.getBus().post(new MealChosenEvent("Breakfast"));
                });
    }

    @Override
    public void call(ArrayList<DayMenu> dayMenus) {
        Meal currentMeal = getCurrentMeal(dayMenus);
        Meal nextMeal = getNextMeal(dayMenus);

        if (currentMeal != null) {
            EventBus.getBus().post(new MealChosenEvent(currentMeal.getName()));
        } else if (nextMeal != null) {
            EventBus.getBus().post(new MealChosenEvent(nextMeal.getName()));
        } else {
            Timber.w("There were no current or next meals, displaying tomorrow's breakfast instead");
            EventBus.getBus().post(new ShowSnackbarEvent("All meals are closed for the day, here's tomorrow's Breakfast"));
            EventBus.getBus().post(new DateChosenEvent(LocalDate.now().plusDays(1)));
            EventBus.getBus().post(new MealChosenEvent("Breakfast"));
        }
    }

    private Meal getNextMeal(ArrayList<DayMenu> dayMenus) {
        NextMealsCollection nextMeals = new NextMealsCollection();
        for (DayMenu menu : dayMenus) {
            Timber.d("I have a completed menu for %s", menu.getLocation());
            nextMeals.addFromDayMenu(menu, DateTime.now());
        }
        return nextMeals.getNextMeal();
    }

    private Meal getCurrentMeal(ArrayList<DayMenu> dayMenus) {
        CurrentMealCollection currentMeals = new CurrentMealCollection();
        for (DayMenu menu : dayMenus) {
            Timber.d("I have a completed menu for %s", menu.getLocation());
            currentMeals.addFromDayMenu(menu, DateTime.now());
        }
        return currentMeals.getCurrentMeal();
    }
}
