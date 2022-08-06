package me.jingbin.byrv.viewbinding.binding.rv;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import java.util.List;

import me.jingbin.library.adapter.BaseByRecyclerViewAdapter;

/**
 * 【ViewBinding】单一 item 类型 adapter
 * https://github.com/youlookwhat/ByRecyclerView
 */
public abstract class BaseVBindingAdapter<T, B extends ViewBinding> extends BaseByRecyclerViewAdapter<T, BaseVBindingHolder<T, B>> {

    public BaseVBindingAdapter() {
    }

    public BaseVBindingAdapter(List<T> data) {
        super(data);
    }

    // 这里没有对这个方法进行封装，因为不想使用反射
    @NonNull
    @Override
    public abstract BaseViewHolder onCreateViewHolder(ViewGroup parent, int position);

    public class BaseViewHolder extends BaseVBindingHolder<T, B> {
        public BaseViewHolder(B binding) {
            super(binding);
        }

        @Override
        protected void onBindingView(BaseVBindingHolder holder, T bean, int position) {
            if (holder != null && bean != null && binding != null) {
                bindView(holder, bean, binding, position);
            }
        }

        @Override
        protected void onBindingViewPayloads(BaseVBindingHolder holder, T bean, int position, @NonNull List<Object> payloads) {
            if (holder != null && bean != null && binding != null) {
                bindViewPayloads(holder, bean, binding, position, payloads);
            }
        }
    }

    protected abstract void bindView(@NonNull BaseVBindingHolder holder, @NonNull T bean, @NonNull B binding, int position);

    /**
     * 局部刷新，非必须
     */
    protected void bindViewPayloads(@NonNull BaseVBindingHolder holder, @NonNull T bean, @NonNull B binding, int position, @NonNull List<Object> payloads) {
        /*
         * fallback to onBaseBindView(holder, bean,position) if app does not override this method.
         * 如果不覆盖 bindViewPayloads() 方法，就走 bindView()
         */
        bindView(holder, bean, binding, position);
    }
}

