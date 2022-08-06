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

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;


import me.jingbin.library.adapter.BaseListHolder;


/**
 * https://github.com/youlookwhat/ByRecyclerView
 */
public class BaseListVBindingHolder<B extends ViewBinding> extends BaseListHolder {

    @NonNull
    public final B binding;

    public BaseListVBindingHolder(@NonNull B binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @NonNull
    public B getBinding() {
        return binding;
    }
}
