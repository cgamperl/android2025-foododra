package at.wifi.swdev.foodoraapp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import at.wifi.swdev.foodoraapp.databinding.FragmentRestaurantCategoryBinding;
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
}