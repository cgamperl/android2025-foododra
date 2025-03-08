package at.wifi.swdev.foodoraapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import at.wifi.swdev.foodoraapp.api.ApiClient;
import at.wifi.swdev.foodoraapp.api.FoodoraApiService;
import at.wifi.swdev.foodoraapp.api.model.FileData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileDataRepository {

    private final MutableLiveData<List<FileData>> files = new MutableLiveData<>(new ArrayList<>());
    private final FoodoraApiService apiService = ApiClient.getApiService();

    public LiveData<List<FileData>> getAllFiles() {
        apiService.getAllFiles().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<FileData>> call, Response<List<FileData>> response) {
                if (response.isSuccessful()) {
                    files.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<FileData>> call, Throwable throwable) {

            }
        });

        return files;
    }


}
