package at.wifi.swdev.foodoraapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import at.wifi.swdev.foodoraapp.api.ApiClient;
import at.wifi.swdev.foodoraapp.api.FoodoraApiService;
import at.wifi.swdev.foodoraapp.api.model.FileData;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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


    public LiveData<FileData> uploadFile(String relId, File file) {
        // File in Part umwandeln
        // TODO: Check if we can use a more specific MIME type
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        apiService.uploadFile(relId, body).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<FileData> call, Response<FileData> response) {
                if (response.isSuccessful()) {

                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<FileData> call, Throwable throwable) {
                // Handle error
            }
        });
    }

}
