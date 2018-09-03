package com.loanuncle.gm.juke.presenter;

import com.loanuncle.gm.baselibrary.mvpbase.baseimpl.BasePresenterImpl;
import com.loanuncle.gm.baselibrary.retrofit.ExceptionHelper;
import com.loanuncle.gm.juke.bean.request.AgreementRequestBean;
import com.loanuncle.gm.juke.bean.request.LoginRequestBean;
import com.loanuncle.gm.juke.bean.request.VerCodeRequestBean;
import com.loanuncle.gm.juke.bean.response.AgreementResponseBean;
import com.loanuncle.gm.juke.bean.response.LoginResponseBean;
import com.loanuncle.gm.juke.bean.response.VerCodeResponseBean;
import com.loanuncle.gm.juke.contact.LoginContact;
import com.loanuncle.gm.juke.model.Api;
import com.loanuncle.gm.juke.util.ObjectUtil;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by GM on 2018/8/21.
 * @description 登录界面presenter
 */

public class LoginPresenter extends BasePresenterImpl<LoginContact.view> implements  LoginContact.presenter{

    public LoginPresenter(LoginContact.view view) {
        super(view);
    }

    /**
     * 请求获取验证码
     * */
    @Override
    public void requestVerCode(VerCodeRequestBean verCodeRequestBean) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),gson.toJson(verCodeRequestBean));
        Api.getInstance().sendVerCode(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showLoadingDialog("");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VerCodeResponseBean>() {
                    @Override
                    public void accept(VerCodeResponseBean verCodeResponseBean) throws Exception {
                        view.dismissLoadingDialog();
                        view.verCodeResponse(verCodeResponseBean);
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
    public void requestLogin(LoginRequestBean loginRequestBean) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),gson.toJson(loginRequestBean));
        Api.getInstance().login(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showLoadingDialog("");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResponseBean>() {
                    @Override
                    public void accept(LoginResponseBean loginResponseBean) throws Exception {
                        view.dismissLoadingDialog();
                        view.loginResponse(loginResponseBean);
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
    public void requestAgreement(AgreementRequestBean agreementRequestBean) {
        Map<String, Object> map = null;
        map = ObjectUtil.agreementObjectToMap(agreementRequestBean);
        Api.getInstance().agreement(map)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showLoadingDialog("");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AgreementResponseBean>() {
                    @Override
                    public void accept(AgreementResponseBean agreementResponseBean) throws Exception {
                        view.dismissLoadingDialog();
                        view.agreementResponse(agreementResponseBean);
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
