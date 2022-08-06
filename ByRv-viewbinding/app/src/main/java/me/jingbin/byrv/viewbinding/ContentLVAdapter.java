package me.jingbin.byrv.viewbinding;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import me.jingbin.byrv.viewbinding.binding.lv.BaseListVBindingAdapter;
import me.jingbin.byrv.viewbinding.binding.lv.BaseListVBindingHolder;
import me.jingbin.byrv.viewbinding.databinding.ItemContentBinding;

/**
 * Created by jingbin on 8/6/22.
 */
class ContentLVAdapter extends BaseListVBindingAdapter<String, ItemContentBinding> {

    @Override
    protected BaseListVBindingHolder<ItemContentBinding> onCreateViewHolder(ViewGroup parent, int position) {
        return new BaseListVBindingHolder<>(ItemContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    protected void bindView(String bean, ItemContentBinding binding, int position) {
        binding.tvContent.setText(bean + "_LV_" + position);
    }
}
