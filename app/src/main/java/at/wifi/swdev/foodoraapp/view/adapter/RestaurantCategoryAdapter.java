package at.wifi.swdev.foodoraapp.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import at.wifi.swdev.foodoraapp.api.model.RestaurantCategory;
import at.wifi.swdev.foodoraapp.databinding.RestaurantCategoryListItemBinding;
import at.wifi.swdev.foodoraapp.view.listener.OnListItemClickListener;
import at.wifi.swdev.foodoraapp.view.listener.OnListItemLongClickListener;

public class RestaurantCategoryAdapter extends RecyclerView.Adapter<RestaurantCategoryAdapter.RestaurantCategoryViewHolder> {

    private List<RestaurantCategory> categories = new ArrayList<>();
    private OnListItemClickListener<RestaurantCategory> onListItemClickListener;
    private OnListItemLongClickListener<RestaurantCategory> onListItemLongClickListener;

    public void setOnListItemClickListener(OnListItemClickListener<RestaurantCategory> onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }

    public void setOnListItemLongClickListener(OnListItemLongClickListener<RestaurantCategory> onListItemLongClickListener) {
        this.onListItemLongClickListener = onListItemLongClickListener;
    }

    public void setCategories(List<RestaurantCategory> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestaurantCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RestaurantCategoryListItemBinding binding = RestaurantCategoryListItemBinding.inflate(inflater, parent, false);

        return new RestaurantCategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantCategoryViewHolder holder, int position) {
        // Datensatz dem ViewHolder zuweisen

        RestaurantCategory category = categories.get(position);
        holder.binding.categoryName.setText(category.name);

        // Rufe "unseren" Klickhandler auf, wenn auf ein Listen-Element geklickt wurde
        holder.itemView.setOnClickListener(v -> {
            if (this.onListItemClickListener != null) {
                this.onListItemClickListener.onListItemClick(category, position);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (this.onListItemLongClickListener != null) {
                this.onListItemLongClickListener.onListItemLongClick(category, position);
            }

            return true;
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

        private final RestaurantCategoryListItemBinding binding;

        public RestaurantCategoryViewHolder(@NonNull RestaurantCategoryListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
