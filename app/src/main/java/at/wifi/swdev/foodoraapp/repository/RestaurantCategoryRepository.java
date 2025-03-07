package at.wifi.swdev.foodoraapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import at.wifi.swdev.foodoraapp.api.ApiClient;
import at.wifi.swdev.foodoraapp.api.FoodoraApiService;
import at.wifi.swdev.foodoraapp.api.model.RestaurantCategory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantCategoryRepository {

    // Zugriff auf den API-Client
    private final FoodoraApiService apiService = ApiClient.getApiService();
    private final MutableLiveData<List<RestaurantCategory>> restaurantCategories = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<RestaurantCategory>> getRestaurantCategories() {

        apiService.getRestaurantCategories().enqueue(new Callback<List<RestaurantCategory>>() {
            @Override
            public void onResponse(@NonNull Call<List<RestaurantCategory>> call, @NonNull Response<List<RestaurantCategory>> response) {
                // Wir haben eine Antwort vom Server erhalten...
                // Es kann aber auch einen Fehler gegeben haben (HTTP 404 oder HTTP 500)
                if (response.isSuccessful()) {
                    // Ergebnis mit LiveData zur Verfügung stellen
                    restaurantCategories.postValue(toSortedList(response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<RestaurantCategory>> call, @NonNull Throwable throwable) {
                // Es kam zu einer Exception (Kein Internet, ...)
                // TODO: Fehlerbehandlung
            }
        });

        return restaurantCategories;
    }

    private List<RestaurantCategory> toSortedList(List<RestaurantCategory> list) {
        return list.stream()
                .sorted(Comparator.comparing(category -> category.name))
                .collect(Collectors.toList());
    }

}
