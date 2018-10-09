package com.loanuncle.gm.juke.presenter;

import com.google.gson.Gson;
import com.loanuncle.gm.baselibrary.mvpbase.baseimpl.BasePresenterImpl;
import com.loanuncle.gm.baselibrary.retrofit.ExceptionHelper;
import com.loanuncle.gm.juke.bean.request.FeedBackRequestBean;
import com.loanuncle.gm.juke.bean.request.LogoutRequestBean;
import com.loanuncle.gm.juke.bean.response.FeedBackResponseBean;
import com.loanuncle.gm.juke.bean.response.LogoutResponseBean;
import com.loanuncle.gm.juke.contact.PersonCenterContact;
import com.loanuncle.gm.juke.model.Api;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by GM on 2018/8/24.
 * @description 个人中心presenter
 */

public class PersonCenterPresenter extends BasePresenterImpl<PersonCenterContact.view> implements PersonCenterContact.presenter{

    private Gson gson;

    public PersonCenterPresenter(PersonCenterContact.view view) {
        super(view);
        gson = new Gson();
    }

    @Override
    public void logoutRequest(LogoutRequestBean logoutRequestBean) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),gson.toJson(logoutRequestBean));
        Api.getInstance().logout(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showLoadingDialog("");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LogoutResponseBean>() {
                    @Override
                    public void accept(LogoutResponseBean logoutResponseBean) throws Exception {
                        view.dismissLoadingDialog();
                        view.logoutResponse(logoutResponseBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.dismissLoadingDialog();
                        ExceptionHelper.handleException(throwable);
                    }
                });
    }

    @Override
    public void feedBackRequest(FeedBackRequestBean feedBackRequestBean) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),gson.toJson(feedBackRequestBean));
        Api.getInstance().feedBack(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showLoadingDialog("");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FeedBackResponseBean>() {
                    @Override
                    public void accept(FeedBackResponseBean feedBackResponseBean) throws Exception {
                        view.dismissLoadingDialog();
                        view.feedBackResponse(feedBackResponseBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.dismissLoadingDialog();
                        ExceptionHelper.handleException(throwable);
                    }
                });
    }
}
