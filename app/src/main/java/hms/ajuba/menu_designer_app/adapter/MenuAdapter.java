package hms.ajuba.menu_designer_app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Stack;

import hms.ajuba.menu_designer_app.R;
import hms.ajuba.menu_designer_app.pojo.Option;
import hms.ajuba.menu_designer_app.util.OptionsUtil;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {
    private ArrayList<Option> optionList;
    private Context context;
    private MenuAdapter adapter;
    private Stack<ArrayList<Option>> menuListStack;

    public MenuAdapter(Context context, ArrayList<Option> optionList) {
        this.optionList = optionList;
        this.context = context;
        this.adapter = this;
        this.menuListStack = new Stack<>();
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, null);
        return new MenuViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MenuViewHolder holder, int position) {
        final Option option = optionList.get(position);

        ImageLoader imageLoader = ImageLoader.getInstance();
        ImageView imageView = holder.getImageView();
        TextView textView = holder.getTextView();

        imageLoader.displayImage("http://oi39.tinypic.com/33zb76p.jpg", imageView);

        textView.setText(Html.fromHtml(option.getTitle()));

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Option clickedOption = (Option) view.getTag();
                menuListStack.push((ArrayList<Option>) optionList.clone());
                optionList.clear();
                optionList = OptionsUtil.getNextList(clickedOption.getOptionsMap());
                adapter.notifyDataSetChanged();
            }
        };

        textView.setOnClickListener(clickListener);
        imageView.setOnClickListener(clickListener);

        textView.setTag(option);
        imageView.setTag(option);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fom_left_to_right);
        View itemView = holder.itemView;
        itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return (null != optionList ? optionList.size() : 0);
    }

    public Stack<ArrayList<Option>> getMenuListStack() {
        return menuListStack;
    }

    public void setOptionList(ArrayList<Option> optionList) {
        this.optionList = optionList;
    }
}
