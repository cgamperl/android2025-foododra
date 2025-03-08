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

    private final MutableLiveData<Boolean> categoryCreated = new MutableLiveData<>();
    private final MutableLiveData<Boolean> categoryUpdated = new MutableLiveData<>();
    private final MutableLiveData<Boolean> categoryDeleted = new MutableLiveData<>();

    public LiveData<List<RestaurantCategory>> getRestaurantCategories() {

        apiService.getRestaurantCategories().enqueue(new Callback<>() {
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

    public LiveData<Boolean> createRestaurantCategory(RestaurantCategory category) {
        apiService.createRestaurantCategory(category).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<RestaurantCategory> call, Response<RestaurantCategory> response) {
                if (response.isSuccessful()) {
                    RestaurantCategory created = response.body();
                    List<RestaurantCategory> currentCategories = restaurantCategories.getValue();

                    if (currentCategories != null) {
                        List<RestaurantCategory> updatedCategories = new ArrayList<>(currentCategories);
                        updatedCategories.add(created);
                        restaurantCategories.postValue(toSortedList(updatedCategories));
                        categoryCreated.postValue(true);
                    }
                } else {
                    // TODO: Handle error
                    categoryCreated.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<RestaurantCategory> call, Throwable throwable) {
                categoryCreated.postValue(false);
            }
        });

        return categoryCreated;
    }

    public LiveData<Boolean> updateRestaurantCategory(RestaurantCategory category) {
        apiService.updateRestaurantCategory(category.id, category).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<RestaurantCategory> call, Response<RestaurantCategory> response) {
                if (response.isSuccessful()) {
                    // Element in der Liste aktualisieren
                    RestaurantCategory updated = response.body();
                    List<RestaurantCategory> updatedCategories = new ArrayList<>(restaurantCategories.getValue());

                    int position = 0;
                    for (RestaurantCategory restaurantCategory : updatedCategories) {
                        if (updated != null && restaurantCategory.id.equals(updated.id)) {
                            updatedCategories.set(position, updated);
                        }
                        position++;
                    }
                    restaurantCategories.postValue(toSortedList(updatedCategories));


//                    for (int position = 0; position < updatedCategories.size(); position++) {
//                        RestaurantCategory restaurantCategory = updatedCategories.get(position);
//                        if (restaurantCategory.id.equals(updated.id)) {
//                            updatedCategories.set(position, updated);
//                        }
//                    }

                    // Erfolg zurückmelden
                    categoryUpdated.postValue(true);
                } else {
                    categoryUpdated.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<RestaurantCategory> call, Throwable throwable) {
                categoryUpdated.postValue(false);
            }
        });

        return categoryUpdated;
    }

    public LiveData<Boolean> deleteRestaurantCategory(RestaurantCategory categoryToDelete) {

        apiService.deleteRestaurantCategory(categoryToDelete.id).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Element aus Liste löschen und Liste aktualisieren

                    List<RestaurantCategory> currentList = restaurantCategories.getValue();

                    if (currentList != null) {
                        // Filtere das gelöschte Element aus der Liste raus
                        List<RestaurantCategory> updatedCategories = currentList.stream()
                                .filter(c -> !c.id.equals(categoryToDelete.id))
                                .collect(Collectors.toList());

                        restaurantCategories.postValue(updatedCategories);
                        categoryDeleted.postValue(true);
                    }
                } else {
                    categoryDeleted.postValue(false);
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                categoryDeleted.postValue(false);
            }
        });

        return categoryDeleted;
    }


    private List<RestaurantCategory> toSortedList(List<RestaurantCategory> list) {
        return list.stream()
                .sorted(Comparator.comparing(category -> category.name.toLowerCase()))
                .collect(Collectors.toList());
    }

}
