package wags.gravatar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class itemsBackend extends RecyclerView.Adapter<itemsBackend.itemsViewHolder> {
    private Context itemContext;
    private ArrayList<items> ItemsList;

    public itemsBackend(Context context, ArrayList<items> itemList) {
        itemContext = context;
        ItemsList = itemList;
    }

    @Override
    public itemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(itemContext).inflate(R.layout.item, parent, false);
        return new itemsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(itemsViewHolder holder, int position) {
        items currentItem = ItemsList.get(position);
        String gravatarUrl = currentItem.getGravatarUrl();
        String username = currentItem.getUsername();
        String badges = currentItem.getBadges();

        holder.TextviewUser.setText(username);
        holder.TextviewBadges.setText(String.format("Badges: %s", badges));
        Picasso.with(itemContext).load(gravatarUrl).fit().centerInside().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return ItemsList.size();
    }

    public class itemsViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView TextviewUser;
        public TextView TextviewBadges;

        public itemsViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gravatar);
            TextviewUser = itemView.findViewById(R.id.username);
            TextviewBadges = itemView.findViewById(R.id.badges);
        }
    }
}
