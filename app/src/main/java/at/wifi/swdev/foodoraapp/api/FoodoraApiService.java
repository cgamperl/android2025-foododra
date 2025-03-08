package at.wifi.swdev.foodoraapp.api;

import java.util.List;

import at.wifi.swdev.foodoraapp.api.model.Restaurant;
import at.wifi.swdev.foodoraapp.api.model.RestaurantCategory;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FoodoraApiService {

    @GET("restaurants/")
    Call<List<Restaurant>> getRestaurants();


    /*
     * Restaurant categories
     */
    @GET("categories/")
    Call<List<RestaurantCategory>> getRestaurantCategories();

    @POST("categories/")
    Call<RestaurantCategory> createRestaurantCategory(@Body RestaurantCategory category);

    @GET("categories/{id}")
    Call<RestaurantCategory> getRestaurantCategory(@Path("id") String restaurantCategoryId);

    @PUT("categories/{id}")
    Call<RestaurantCategory> updateRestaurantCategory(@Path("id") String restaurantCategoryId, @Body RestaurantCategory category);

    @DELETE("categories/{id}")
    Call<Void> deleteRestaurantCategory(@Path("id") String restaurantCategoryId);
}
