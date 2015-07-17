package hms.ajuba.menu_designer_app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import hms.ajuba.menu_designer_app.R;

public class MenuViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private TextView textView;

    public MenuViewHolder(View view) {
        super(view);
        this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
        this.textView = (TextView) view.findViewById(R.id.title);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextView() {
        return textView;
    }
}
