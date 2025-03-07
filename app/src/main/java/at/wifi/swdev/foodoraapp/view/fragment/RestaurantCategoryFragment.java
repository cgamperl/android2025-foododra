package at.wifi.swdev.foodoraapp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import at.wifi.swdev.foodoraapp.databinding.FragmentRestaurantCategoryBinding;
import at.wifi.swdev.foodoraapp.view.adapter.RestaurantCategoryAdapter;
import at.wifi.swdev.foodoraapp.view.viewmodel.RestaurantCategoryViewModel;

public class RestaurantCategoryFragment extends Fragment {

    private FragmentRestaurantCategoryBinding binding;
    private RestaurantCategoryViewModel viewModel;

    public RestaurantCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RestaurantCategoryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRestaurantCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // hier passiert alles (circa wie bei onCreate() bei Activity)

        // 1. Adapter für RecyclerView erstellen
        RestaurantCategoryAdapter adapter = new RestaurantCategoryAdapter();
        // 2. RecyclerView erstellen / einrichten
        // 2.1 RecyclerView mit Adapter verbinden
        binding.categoryList.setAdapter(adapter);
        // 2.2 LayoutManager setzen
        binding.categoryList.setLayoutManager(new LinearLayoutManager(requireContext()));
        // 3. Daten von Server (=API) laden
        viewModel.getRestaurantCategories().observe(getViewLifecycleOwner(), restaurantCategories -> {
            // 4. Adapter mit Daten aktualisieren
            adapter.setCategories(restaurantCategories);
        });


    }
}