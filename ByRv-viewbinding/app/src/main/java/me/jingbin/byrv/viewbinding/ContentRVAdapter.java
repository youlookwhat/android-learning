package me.jingbin.byrv.viewbinding;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import me.jingbin.byrv.viewbinding.binding.rv.BaseVBindingAdapter;
import me.jingbin.byrv.viewbinding.binding.rv.BaseVBindingHolder;
import me.jingbin.byrv.viewbinding.databinding.ItemContentBinding;

/**
 * Created by jingbin on 8/6/22.
 */
class ContentRVAdapter extends BaseVBindingAdapter<String, ItemContentBinding> {

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        return new BaseViewHolder(ItemContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    protected void bindView(@NonNull BaseVBindingHolder holder, @NonNull String bean, @NonNull ItemContentBinding binding, int position) {
        binding.tvContent.setText(bean + "_RV_" + position);
    }

}
