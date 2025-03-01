package at.wifi.swdev.foodoraapp.api;

import java.util.List;

import at.wifi.swdev.foodoraapp.api.model.Restaurant;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodoraApiService {

    @GET("restaurants")
    Call<List<Restaurant>> getRestaurants();

}
