package me.jingbin.byrv.viewbinding.binding.rv;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import java.util.List;

import me.jingbin.library.adapter.BaseByViewHolder;


/**
 * https://github.com/youlookwhat/ByRecyclerView
 */
public abstract class BaseVBindingHolder<T, B extends ViewBinding> extends BaseByViewHolder<T> {

    public final B binding;

    public BaseVBindingHolder(B binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    protected void onBaseBindView(BaseByViewHolder<T> holder, T bean, int position) {
        onBindingView(this, bean, position);
    }

    @Override
    protected void onBaseBindViewPayloads(BaseByViewHolder<T> holder, T bean, int position, @NonNull List<Object> payloads) {
        onBindingViewPayloads(this, bean, position, payloads);
    }

    protected abstract void onBindingView(BaseVBindingHolder holder, T bean, int position);

    protected void onBindingViewPayloads(BaseVBindingHolder holder, T bean, int position, @NonNull List<Object> payloads) {
        /*
         * fallback to onBindingViewPayloads(holder, bean,position) if app does not override this method.
         * 如果不覆盖 bindViewPayloads() 方法，就走 onBindingView()
         */
        onBindingView(holder, bean, position);
    }
}
