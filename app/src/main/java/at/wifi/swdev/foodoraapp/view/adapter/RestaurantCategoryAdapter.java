package at.wifi.swdev.foodoraapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import at.wifi.swdev.foodoraapp.api.model.RestaurantCategory;
import at.wifi.swdev.foodoraapp.databinding.RestaurantCategoryListItemBinding;
import at.wifi.swdev.foodoraapp.view.listener.OnListItemClickListener;

public class RestaurantCategoryAdapter extends RecyclerView.Adapter<RestaurantCategoryAdapter.RestaurantCategoryViewHolder> {

    private RestaurantCategoryListItemBinding binding;
    private List<RestaurantCategory> categories = new ArrayList<>();
    private OnListItemClickListener<RestaurantCategory> onListItemClickListener;

    public void setOnListItemClickListener(OnListItemClickListener<RestaurantCategory> onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    public void setCategories(List<RestaurantCategory> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestaurantCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = RestaurantCategoryListItemBinding.inflate(inflater, parent, false);

        return new RestaurantCategoryViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantCategoryViewHolder holder, int position) {
        // Datensatz dem ViewHolder zuweisen

        RestaurantCategory category = categories.get(position);
        binding.categoryName.setText(category.name);

        // Rufe "unseren" Klickhandler auf, wenn auf ein Listen-Element geklickt wurde
        holder.itemView.setOnClickListener(v -> {
            if (this.onListItemClickListener != null) {
                this.onListItemClickListener.onListItemClick(category, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (categories != null) {
            return categories.size();
        }

        return 0;
    }

    public class RestaurantCategoryViewHolder extends RecyclerView.ViewHolder {

        public RestaurantCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
