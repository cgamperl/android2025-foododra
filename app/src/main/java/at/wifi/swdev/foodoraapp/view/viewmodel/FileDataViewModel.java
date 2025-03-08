package at.wifi.swdev.foodoraapp.view.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import at.wifi.swdev.foodoraapp.api.model.FileData;
import at.wifi.swdev.foodoraapp.repository.FileDataRepository;

public class FileDataViewModel extends AndroidViewModel {
    private FileDataRepository repository;

    public FileDataViewModel(@NonNull Application application) {
        super(application);
        repository = new FileDataRepository();
    }

    public LiveData<List<FileData>> getAllFiles() {
        return repository.getAllFiles();
    }

}
