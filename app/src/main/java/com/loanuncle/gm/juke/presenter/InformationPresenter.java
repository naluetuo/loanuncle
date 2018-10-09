package com.loanuncle.gm.juke.presenter;

import com.loanuncle.gm.baselibrary.mvpbase.baseimpl.BasePresenterImpl;
import com.loanuncle.gm.baselibrary.retrofit.ExceptionHelper;
import com.loanuncle.gm.juke.bean.request.EditInformationRequestBean;
import com.loanuncle.gm.juke.bean.request.GetInformationRequestBean;
import com.loanuncle.gm.juke.bean.request.LogoutRequestBean;
import com.loanuncle.gm.juke.bean.response.EditInformationResponseBean;
import com.loanuncle.gm.juke.bean.response.GetInformationResponseBean;
import com.loanuncle.gm.juke.bean.response.LogoutResponseBean;
import com.loanuncle.gm.juke.contact.InformationContact;
import com.loanuncle.gm.juke.model.Api;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by GM on 2018/9/16.
 * @descripotion 个人资料页面的presenter
 */

public class InformationPresenter extends BasePresenterImpl<InformationContact.view> implements InformationContact.presenter{


    public InformationPresenter(InformationContact.view view) {
        super(view);
    }

    @Override
    public void getInformationRequest(GetInformationRequestBean getInformationRequestBean) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),gson.toJson(getInformationRequestBean));
        Api.getInstance().getInformation(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showLoadingDialog("");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetInformationResponseBean>() {
                    @Override
                    public void accept(GetInformationResponseBean getInformationResponseBean) throws Exception {
                        view.dismissLoadingDialog();
                        view.getInformationResponse(getInformationResponseBean);
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
    public void editInformationRequest(EditInformationRequestBean editInformationRequestBean) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),gson.toJson(editInformationRequestBean));
        Api.getInstance().editInformation(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showLoadingDialog("");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EditInformationResponseBean>() {
                    @Override
                    public void accept(EditInformationResponseBean editInformationResponseBean) throws Exception {
                        view.dismissLoadingDialog();
                        view.ediInformationResponse(editInformationResponseBean);
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
}
