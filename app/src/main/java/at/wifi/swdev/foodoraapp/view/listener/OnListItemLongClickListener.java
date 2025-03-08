package at.wifi.swdev.foodoraapp.view.listener;

@FunctionalInterface
public interface OnListItemLongClickListener<ListItemType> {
    void onListItemLongClick(ListItemType category, int position);
}
