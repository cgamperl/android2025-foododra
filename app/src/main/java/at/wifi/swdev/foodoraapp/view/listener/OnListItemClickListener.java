package at.wifi.swdev.foodoraapp.view.listener;

@FunctionalInterface
public interface OnListItemClickListener<ListItemType> {
    void onListItemClick(ListItemType category, int position);
}
