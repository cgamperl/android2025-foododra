package at.wifi.swdev.foodoraapp.repository;

import android.util.Log;

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
    private final MutableLiveData<FileData> createdFile = new MutableLiveData<>();
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


    public LiveData<FileData> uploadFile(String relId, File file, String mimeType) {
        // Use a fallback, if mime type was not recognized
        if (mimeType == null) {
            mimeType = "image/*";
        }

        // File in Part umwandeln
        RequestBody requestBody = RequestBody.create(MediaType.parse(mimeType), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        apiService.uploadFile(relId, body).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<FileData> call, Response<FileData> response) {
                if (response.isSuccessful()) {
                    createdFile.postValue(response.body());
                } else {
                    // Handle error
                    Log.e("", "Error creating file");
                }
            }

            @Override
            public void onFailure(Call<FileData> call, Throwable throwable) {
                // Handle error
                Log.e("", "Error creating file");
            }
        });

        return createdFile;
    }

}
