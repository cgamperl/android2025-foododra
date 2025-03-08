package at.wifi.swdev.foodoraapp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import at.wifi.swdev.foodoraapp.api.model.RestaurantCategory;
import at.wifi.swdev.foodoraapp.databinding.BottomsheetRestaurantCategoryBinding;
import at.wifi.swdev.foodoraapp.view.validation.Validator;
import at.wifi.swdev.foodoraapp.view.viewmodel.RestaurantCategoryViewModel;

public class RestaurantCategoryBottomSheet extends BottomSheetDialogFragment {

    private BottomsheetRestaurantCategoryBinding binding;
    private RestaurantCategoryViewModel viewModel;
    private RestaurantCategory categoryToUpdate;

    public RestaurantCategoryBottomSheet() {
    }

    public RestaurantCategoryBottomSheet(RestaurantCategory categoryToUpdate) {
        this.categoryToUpdate = categoryToUpdate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RestaurantCategoryViewModel.class);
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

        if (binding.categoryNameET.getText().toString().isEmpty()) {
            binding.saveCategoryBtn.setEnabled(false);
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

        // Beide Möglichkeiten: Erstellen und Bearbeiten

        binding.saveCategoryBtn.setOnClickListener(v -> {

            // Button deaktivieren (verhindert neuen Submit)
            binding.saveCategoryBtn.setEnabled(false);
            // Eventuelle Fehlermeldung ausblenden
            binding.errorMessageTV.setVisibility(View.GONE);

            // Text auslesen
            String categoryName = binding.categoryNameET.getText().toString();
            // RestaurantCategory erstellen
            RestaurantCategory category = new RestaurantCategory(categoryName);
            // Neue Kategorie speichern
            viewModel.createRestaurantCategory(category).observe(requireActivity(), success -> {
                if (success) {
                    // Bottomsheet schließen
                    dismiss();
                } else {
                    binding.errorMessageTV.setVisibility(View.VISIBLE);
                    binding.saveCategoryBtn.setEnabled(true);
                }
            });
        });
    }
}
