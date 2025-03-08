package at.wifi.swdev.foodoraapp.view.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import at.wifi.swdev.foodoraapp.api.model.RestaurantCategory;
import at.wifi.swdev.foodoraapp.repository.RestaurantCategoryRepository;

public class RestaurantCategoryViewModel extends AndroidViewModel {

    private final RestaurantCategoryRepository categoryRepository;


    public RestaurantCategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new RestaurantCategoryRepository();
    }

    public LiveData<List<RestaurantCategory>> getRestaurantCategories() {
        return categoryRepository.getRestaurantCategories();
    }

    public LiveData<Boolean> createRestaurantCategory(RestaurantCategory category) {
        return categoryRepository.createRestaurantCategory(category);
    }

    public LiveData<Boolean> updateRestaurantCategory(RestaurantCategory category) {
        return categoryRepository.updateRestaurantCategory(category);
    }

}
