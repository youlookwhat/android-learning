package com.pandaq.mvpdemo.ui.IViewBind;


import com.pandaq.mvpdemo.model.zhihu.ZhihuStoryContent;

/**
 * Created by PandaQ on 2016/10/10.
 * email : 767807368@qq.com
 */

public interface IZhihuStoryInfoActivity {
    void showProgressBar();

    void hideProgressBar();

    void loadZhihuStory();

    void loadFail(String errmsg);

    void loadSuccess(ZhihuStoryContent zhihuStory);
}
