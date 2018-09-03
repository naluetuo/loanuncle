package com.loanuncle.gm.juke.presenter;

import com.loanuncle.gm.baselibrary.mvpbase.baseimpl.BasePresenterImpl;
import com.loanuncle.gm.baselibrary.retrofit.ExceptionHelper;
import com.loanuncle.gm.juke.bean.request.BaseInfoRequestBean;
import com.loanuncle.gm.juke.bean.response.BaseInfoResponseBean;
import com.loanuncle.gm.juke.contact.SplashContact;
import com.loanuncle.gm.juke.model.Api;
import com.loanuncle.gm.juke.util.ObjectUtil;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by GM on 2018/8/25.
 * @description 闪屏页presenter
 */

public class SplashPresenter extends BasePresenterImpl<SplashContact.view> implements SplashContact.presenter{

    public SplashPresenter(SplashContact.view view) {
        super(view);
    }

    @Override
    public void getBaseInfoRequest(BaseInfoRequestBean baseRequest) {
        Map<String,Object> map = ObjectUtil.baseInfoObjectToMap(baseRequest);
        Api.getInstance().baseInfo(map)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showLoadingDialog("");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseInfoResponseBean>() {
                    @Override
                    public void accept(BaseInfoResponseBean baseInfoResponseBean) throws Exception {
                        view.dismissLoadingDialog();
                        view.getBaseInfoResponse(baseInfoResponseBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.dismissLoadingDialog();
                        String error = ExceptionHelper.handleException(throwable);
                    }
                });
    }
}
