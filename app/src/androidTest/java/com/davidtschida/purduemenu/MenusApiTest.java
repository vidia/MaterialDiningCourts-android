package com.davidtschida.purduemenu;

import android.os.AsyncTask;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.davidtschida.purduemenu.models.DiningLocation;
import com.davidtschida.purduemenu.models.Locations;

import junit.framework.TestCase;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by david on 10/26/2015.
 */
public class MenusApiTest extends InstrumentationTestCase {
    private static final String TAG = "MenusApiTest";
    private MenuService menuService;

    protected void setUp() {
        menuService = MenusApi.getApiService();
    }

    public void testGetDiningLocations() throws Exception {
        // create  a signal to let us know when our task is done.
        final CountDownLatch signal = new CountDownLatch(1);

        Call<Locations> locs = menuService.getDiningLocations();
        locs.enqueue(new Callback<Locations>() {
            @Override
            public void onResponse(Response<Locations> response, Retrofit retrofit) {
                assertEquals("Response success", 200, response.code());
                assertNotNull("List exists", response.body().getLocations());
                assertEquals("All five courts", 5, response.body().getLocations().size());

                //Test that a location has all the right data.
                DiningLocation location = response.body().getLocations().get(0);

                assertNotNull("Location has a name", location.getName());
                assertNotNull("Location has a formalName", location.getFormalName());
                assertNotNull("Location has a address", location.getAddress());
                assertNotNull("Location has a phoneNumber", location.getPhoneNumber());
                assertNotSame("Location has a latitude", 0, location.getLatitude());
                assertNotSame("Location has a longitude", 0, location.getLongitude());
                assertNotNull("Location has a images", location.getImages());
                assertNotNull("Location has a normalHours", location.getNormalHours());

                signal.countDown();
            }

            @Override
            public void onFailure(Throwable t) {
                fail("Retrofit failed with error: " + t.getMessage());
                signal.countDown();
            }
        });

        signal.await(30, TimeUnit.SECONDS);
    }

//    /**
//     * This demonstrates how to test AsyncTasks in android JUnit. Below I used
//     * an in line implementation of a asyncTask, but in real life you would want
//     * to replace that with some task in your application.
//     * @throws Throwable
//     */
//    public void testSomeAsynTask () throws Throwable {
//        // create  a signal to let us know when our task is done.
//        final CountDownLatch signal = new CountDownLatch(1);
//
//    /* Just create an in line implementation of an asynctask. Note this
//     * would normally not be done, and is just here for completeness.
//     * You would just use the task you want to unit test in your project.
//     */
//        final AsyncTask<String, Void, String> myTask = new AsyncTask<String, Void, String>() {
//
//            @Override
//            protected String doInBackground(String... arg0) {
//                //Do something meaningful.
//                return "something happened!";
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                super.onPostExecute(result);
//
//            /* This is the key, normally you would use some type of listener
//             * to notify your activity that the async call was finished.
//             *
//             * In your test method you would subscribe to that and signal
//             * from there instead.
//             */
//                signal.countDown();
//            }
//        };
//
//        // Execute the async task on the UI thread! THIS IS KEY!
//        runTestOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                myTask.execute("Do something");
//            }
//        });
//
//    /* The testing thread will wait here until the UI thread releases it
//     * above with the countDown() or 30 seconds passes and it times out.
//     */
//        signal.await(30, TimeUnit.SECONDS);
//
//        // The task is done, and now you can assert some things!
//        assertTrue("Happiness", true);
//    }
}
