package com.loanuncle.gm.juke.presenter;

import com.loanuncle.gm.baselibrary.mvpbase.baseimpl.BasePresenterImpl;
import com.loanuncle.gm.baselibrary.retrofit.ExceptionHelper;
import com.loanuncle.gm.juke.bean.request.GetNewUserRequestBean;
import com.loanuncle.gm.juke.bean.response.GetNewUserResponseBean;
import com.loanuncle.gm.juke.contact.GetNewUserContact;
import com.loanuncle.gm.juke.model.Api;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by GM on 2018/8/25.
 * @description 验证用户信息presenter
 */

public class GetNewUserPresenter extends BasePresenterImpl<GetNewUserContact.view> implements GetNewUserContact.presenter{

    public GetNewUserPresenter(GetNewUserContact.view view) {
        super(view);
    }

    @Override
    public void getNewUserInfoRequest(GetNewUserRequestBean getNewUserRequestBean) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),gson.toJson(getNewUserRequestBean));
        Api.getInstance().getNewUserInfo(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showLoadingDialog("");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetNewUserResponseBean>() {
                    @Override
                    public void accept(GetNewUserResponseBean getNewUserResponseBean) throws Exception {
                        view.dismissLoadingDialog();
                        view.getNewUserInfoResponse(getNewUserResponseBean);
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
