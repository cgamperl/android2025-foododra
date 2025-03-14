package at.wifi.swdev.foodoraapp.view.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;

import at.wifi.swdev.foodoraapp.api.model.RestaurantCategory;
import at.wifi.swdev.foodoraapp.databinding.BottomsheetRestaurantCategoryBinding;
import at.wifi.swdev.foodoraapp.view.validation.Validator;
import at.wifi.swdev.foodoraapp.view.viewmodel.FileDataViewModel;
import at.wifi.swdev.foodoraapp.view.viewmodel.RestaurantCategoryViewModel;

public class RestaurantCategoryBottomSheet extends BottomSheetDialogFragment {

    private BottomsheetRestaurantCategoryBinding binding;
    private RestaurantCategoryViewModel categoryViewModel;
    private FileDataViewModel fileDataViewModel;
    private RestaurantCategory categoryToUpdate;

    private File pickedFile;

    private final ActivityResultLauncher<String> getContentLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri uri) {
            if (uri != null) {
                pickedFile = new File(uri.getPath());
            }
        }
    });

    public RestaurantCategoryBottomSheet() {
    }

    public RestaurantCategoryBottomSheet(RestaurantCategory categoryToUpdate) {
        this.categoryToUpdate = categoryToUpdate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity());
        categoryViewModel = viewModelProvider.get(RestaurantCategoryViewModel.class);
        fileDataViewModel = viewModelProvider.get(FileDataViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = BottomsheetRestaurantCategoryBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (categoryToUpdate != null) {
            // Wir bearbeiten eine Kategorie
            binding.categoryNameET.setText(categoryToUpdate.name);
            binding.saveCategoryBtn.setEnabled(true);
        }

        // Validierung einbauen
        // Name darf nicht leer sein
        binding.categoryNameET.addTextChangedListener((Validator) (charSequence, i, i1, i2) -> {
            if (charSequence.toString().trim().isEmpty()) {
                // Leere Eingabe
                binding.textInputLayout.setError("Katgegorie-Name darf nicht leer sein");
                binding.saveCategoryBtn.setEnabled(false);
            } else {
                binding.textInputLayout.setError(null);
                binding.saveCategoryBtn.setEnabled(true);
            }
        });

        binding.pickFileBtn.setOnClickListener(v -> {
            // Let user pick an image
            getContentLauncher.launch("image/*");
        });

        // Beide Möglichkeiten: Erstellen und Bearbeiten

        binding.saveCategoryBtn.setOnClickListener(v -> {

            // Button deaktivieren (verhindert neuen Submit)
            binding.saveCategoryBtn.setEnabled(false);
            // Eventuelle Fehlermeldung ausblenden
            binding.errorMessageTV.setVisibility(View.GONE);

            // Text auslesen
            String categoryName = binding.categoryNameET.getText().toString();

            if (categoryToUpdate != null) {
                // Kategorie aktualisieren
                updateRestaurantCategory(categoryName);
            } else {
                // Neue Kategorie anlegen
                createRestaurantCategory(categoryName);
            }
        });
    }

    private void createRestaurantCategory(String categoryName) {
        // RestaurantCategory erstellen
        RestaurantCategory category = new RestaurantCategory(categoryName);
        // Neue Kategorie speichern
        categoryViewModel.createRestaurantCategory(category).observe(requireActivity(), this::handleResult);
    }

    private void updateRestaurantCategory(String categoryName) {
        // Bestehendes Objekt aktualisieren
        categoryToUpdate.name = categoryName;
        categoryViewModel.updateRestaurantCategory(categoryToUpdate).observe(requireActivity(), this::handleResult);
    }

    private void handleResult(Boolean success) {
        if (success) {
            // Gibt es eine Datei, die wir noch uploaded sollen?
            if (pickedFile != null) {
                // Wir laden die Datei hoch
                fileDataViewModel.uploadFile("XXXXXXX", pickedFile).observe(requireActivity(), fileData -> {
                    // TODO: We might want to patch this into the category
                    dismiss();
                });
            } else {
                // keine Datei -> Bottomsheet schließen
                dismiss();
            }
        } else {
            binding.errorMessageTV.setVisibility(View.VISIBLE);
            binding.saveCategoryBtn.setEnabled(true);
        }
    }

}
