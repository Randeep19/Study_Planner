package com.example.latte.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.latte.R;
import com.example.latte.model.MenuItem;

import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {

    private Context context;
    private List<MenuItem> menuItems;
    private MenuItemClickListener menuItemClickListener;

    // Constructor to initialize context, the menu items list, and the click listener
    public MenuItemAdapter(Context context, List<MenuItem> menuItems, MenuItemClickListener menuItemClickListener) {
        if (context == null || menuItems == null) {
            throw new IllegalArgumentException("Context and MenuItems list cannot be null");
        }
        this.context = context;
        this.menuItems = menuItems;
        this.menuItemClickListener = menuItemClickListener;
    }

    // Creates new views (invoked by the layout manager)
    @Override
    public MenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout for each menu item
        View view = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
        return new MenuItemViewHolder(view);
    }

    // Binds the data to the views in the view holder (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MenuItemViewHolder holder, int position) {
        // Get the menu item at the given position
        MenuItem menuItem = menuItems.get(position);

        // Set the title and icon for the menu item
        holder.title.setText(menuItem.getTitle());
        holder.icon.setImageResource(menuItem.getIconResId());

        // Set the click listener for each item in the list
        holder.itemView.setOnClickListener(v -> {
            // When a menu item is clicked, invoke the listener method
            if (menuItemClickListener != null) {
                menuItemClickListener.onMenuItemClick(menuItem);
            }
        });
    }

    // Returns the total number of items in the data set
    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    // ViewHolder class that holds the views for each menu item
    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public MenuItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.menu_item_title);
            icon = itemView.findViewById(R.id.menu_item_icon);
        }
    }

    // Interface to handle the click events on menu items
    public interface MenuItemClickListener {
        void onMenuItemClick(MenuItem menuItem);
    }
}
