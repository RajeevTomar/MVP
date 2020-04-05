package com.positivemind.newsapp.headline.list;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.positivemind.newsapp.R;
import com.positivemind.newsapp.databinding.ViewHeadlineListBinding;
import com.positivemind.newsapp.headline.HeadlineListItemListener;
import com.positivemind.newsapp.headline.HeadlineModel;

import java.util.List;

/**
 * Created by Rajeev Tomar on 21,December,2019
 */
public class HeadlineRecyclerViewAdapter extends RecyclerView.Adapter<HeadlineRecyclerViewAdapter.DataViewHolder> {


    private List<HeadlineModel> headlineModelList;
    private HeadlineListItemListener itemListener;

    public HeadlineRecyclerViewAdapter(HeadlineListItemListener itemListener) {
        this.itemListener = itemListener;
    }


    protected void updateData(@Nullable List<HeadlineModel> data) {
        if (data == null)
            return;
        headlineModelList = data;
        notifyDataSetChanged();
    }

    //----------------------------------------------------------------------------------------------
    // Base Implemented methods
    //----------------------------------------------------------------------------------------------
    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_headline_list,
                new FrameLayout(parent.getContext()), false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        final HeadlineModel headlineModel = headlineModelList.get(position);
        holder.setViewModel(headlineModel);
        // item tap listener
        if (itemListener != null)
            holder.binding.getRoot().setOnClickListener(v ->
                    itemListener.onClickHeadlineListItem(headlineModel));
    }

    @Override
    public int getItemCount() {
        if (headlineModelList == null)
            return 0;
        return headlineModelList.size();
    }

    @Override
    public void onViewAttachedToWindow(DataViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.bind();
    }

    @Override
    public void onViewDetachedFromWindow(DataViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    //----------------------------------------------------------------------------------------------
    // DataViewHolder class
    //----------------------------------------------------------------------------------------------
    public static class DataViewHolder extends RecyclerView.ViewHolder {
        public ViewHeadlineListBinding binding;

        DataViewHolder(View itemView) {
            super(itemView);
            bind();
        }

        void bind() {
            if (binding == null) {
                binding = DataBindingUtil.bind(itemView);
            }
        }

        void unbind() {
            if (binding != null) {
                binding.unbind(); // Don't forget to unbind
            }
        }

        void setViewModel(HeadlineModel viewModel) {
            if (binding != null) {
                binding.setItemData(viewModel);
                // load imageView
                loadImage(binding, viewModel);
            }

        }

        private void loadImage(ViewHeadlineListBinding binding, HeadlineModel viewModel) {
            if (viewModel != null) {
                String thumbnailUrl = viewModel.getUrlToImage();
                if (!TextUtils.isEmpty(thumbnailUrl)) {
                    binding.ivArticle.setVisibility(View.VISIBLE);
                    Glide.with(binding.ivArticle).
                            load(thumbnailUrl).
                             transform(new CenterCrop(), new RoundedCorners(16)).
                            //apply(requestOptions).
                            into(binding.ivArticle);

                } else {
                    binding.ivArticle.setVisibility(View.GONE);
                }

            }
        }
    }
}
