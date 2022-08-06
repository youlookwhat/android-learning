package me.jingbin.byrv.viewbinding.binding.lv;

/*
 * Copyright 2022. Bin Jing (https://github.com/youlookwhat)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import java.util.List;

import me.jingbin.library.adapter.BaseListAdapter;

/**
 * https://github.com/youlookwhat/ByRecyclerView
 */
public abstract class BaseListVBindingAdapter<T, B extends ViewBinding> extends BaseListAdapter<T, BaseListVBindingHolder<B>> {

    public BaseListVBindingAdapter() {
    }

    public BaseListVBindingAdapter(List<T> data) {
        super(data);
    }

    // 这里没有对这个方法进行封装，因为不想使用反射
    @Override
    protected abstract BaseListVBindingHolder<B> onCreateViewHolder(ViewGroup parent, int position);

    @Override
    protected void onBindView(BaseListVBindingHolder<B> holder, T bean, int position) {
        bindView(bean, holder.getBinding(), position);
    }

    protected abstract void bindView(T bean, B binding, int position);
}

