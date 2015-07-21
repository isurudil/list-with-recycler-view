package hms.ajuba.menu_designer_app.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import hms.ajuba.menu_designer_app.R;
import hms.ajuba.menu_designer_app.pojo.Option;
import hms.ajuba.menu_designer_app.util.OptionsUtil;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {
    private ArrayList<Option> optionList;
    private Activity activity;
    private MenuAdapter adapter;
    private Stack<ArrayList<Option>> menuListStack;

    public MenuAdapter(Activity activity, ArrayList<Option> optionList) {
        this.optionList = optionList;
        this.activity = activity;
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

        setRandomImage(imageLoader, imageView);

        textView.setText(Html.fromHtml(option.getTitle()));

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Option clickedOption = (Option) view.getTag();
                if (!clickedOption.isLastOption()) {
                    menuListStack.push((ArrayList<Option>) optionList.clone());
                    optionList.clear();
                    optionList = OptionsUtil.getNextList(clickedOption.getOptionsMap());
                    if (optionList.isEmpty()) {
                        Option backOption = new Option("Back", new LinkedTreeMap<String, LinkedTreeMap>());
                        backOption.setIsLastOption(true);
                        optionList.add(backOption);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    OptionsUtil.handleBackEvent(adapter, activity);
                }
            }
        };

        textView.setOnClickListener(clickListener);
        imageView.setOnClickListener(clickListener);

        textView.setTag(option);
        imageView.setTag(option);

        animate(holder);
    }

    private void setRandomImage(ImageLoader imageLoader, ImageView imageView) {
        int random = (int) (Math.random() * 10);
        String imgUri;
        switch (random){
            case 1:
                imgUri = "drawable://" + R.drawable.img1;
                break;
            case 2:
                imgUri = "drawable://" + R.drawable.img2;
                break;
            case 3:
                imgUri = "drawable://" + R.drawable.img3;
                break;
            case 4:
                imgUri = "drawable://" + R.drawable.img4;
                break;
            case 5:
                imgUri = "drawable://" + R.drawable.img5;
                break;
            case 6:
                imgUri = "drawable://" + R.drawable.img6;
                break;
            case 7:
                imgUri = "drawable://" + R.drawable.img7;
                break;
            case 8:
                imgUri = "drawable://" + R.drawable.img8;
                break;
            case 9:
                imgUri = "drawable://" + R.drawable.img9;
                break;
            default:
                imgUri = "drawable://" + R.drawable.img10;
                break;
        }
        imageLoader.displayImage(imgUri, imageView);
    }

    private void animate(MenuViewHolder holder) {
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.fom_left_to_right);
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
