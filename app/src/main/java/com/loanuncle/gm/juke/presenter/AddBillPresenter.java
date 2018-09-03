package com.loanuncle.gm.juke.presenter;

import com.loanuncle.gm.baselibrary.mvpbase.baseimpl.BasePresenterImpl;
import com.loanuncle.gm.baselibrary.retrofit.ExceptionHelper;
import com.loanuncle.gm.juke.bean.request.AddBillRequestBean;
import com.loanuncle.gm.juke.bean.response.AddBillResponseBean;
import com.loanuncle.gm.juke.contact.AddBillContact;
import com.loanuncle.gm.juke.model.Api;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by GM on 2018/8/24.
 * @description 添加账单presenter;
 */

public class AddBillPresenter extends BasePresenterImpl<AddBillContact.view> implements AddBillContact.presenter{

    public AddBillPresenter(AddBillContact.view view) {
        super(view);
    }

    @Override
    public void addBillRequest(final AddBillRequestBean addBillRequestBean) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),gson.toJson(addBillRequestBean));
        Api.getInstance().addBill(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showLoadingDialog("");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AddBillResponseBean>() {
                    @Override
                    public void accept(AddBillResponseBean addBillResponseBean) throws Exception {
                        view.dismissLoadingDialog();
                        view.addBillResponse(addBillResponseBean);
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
