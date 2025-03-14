package at.wifi.swdev.foodoraapp.api;

import java.util.List;

import at.wifi.swdev.foodoraapp.api.model.FileData;
import at.wifi.swdev.foodoraapp.api.model.Restaurant;
import at.wifi.swdev.foodoraapp.api.model.RestaurantCategory;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    /*
     * File Data
     */
    @GET("files/")
    Call<List<FileData>> getAllFiles();

    @Multipart
    @POST("files/{relId}")
    Call<FileData> uploadFile(@Path("relId") String relId, @Part("file") MultipartBody.Part file);

    @Multipart
    @PUT("files/{id}")
    Call<FileData> updateFile(@Path("id") String fileId, @Part("file") MultipartBody.Part file);

    @DELETE("files/{relId}")
    Call<Void> deleteFileByRelId(@Path("relId") String relId);

}
