package com.encdata.mvp.core.http;

import com.encdata.mvp.core.bean.BaseResponse;
import com.encdata.mvp.core.bean.FeedArticleListData;
import com.encdata.mvp.core.bean.WorkBusListData;
import com.encdata.mvp.core.http.api.GeeksApis;
import com.encdata.mvp.ui.article.bean.BannerData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * 对外隐藏进行网络请求的实现细节
 *
 * @author quchao
 * @date 2017/11/27
 */

public class HttpHelperImpl implements HttpHelper {

    private GeeksApis mGeeksApis;

    @Inject
    HttpHelperImpl(GeeksApis geeksApis) {
        mGeeksApis = geeksApis;
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getFeedArticleList(int pageNum) {
        return mGeeksApis.getFeedArticleList(pageNum);
    }

    @Override
    public Observable<BaseResponse<WorkBusListData>> getWorkBusList(int pageNum) {
        return mGeeksApis.getWorkBusList();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getSearchList(int pageNum, String k) {
        return mGeeksApis.getSearchList(pageNum, k);
    }

    @Override
    public Observable<BaseResponse<List<BannerData>>> getBannerData() {
        return mGeeksApis.getBannerData();
    }

    @Override
    public Observable<BaseResponse<List<BannerData>>> getBannerDatas() {
        return mGeeksApis.getBannerDatas();
    }
}
