package com.loanuncle.gm.juke.presenter;

import com.loanuncle.gm.baselibrary.mvpbase.baseimpl.BasePresenterImpl;
import com.loanuncle.gm.baselibrary.retrofit.ExceptionHelper;
import com.loanuncle.gm.juke.bean.request.GetBillDoneListRequestBean;
import com.loanuncle.gm.juke.bean.response.GetBillDoneListResponseBean;
import com.loanuncle.gm.juke.contact.BillCompletedContact;
import com.loanuncle.gm.juke.model.Api;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by GM on 2018/8/25.
 * @description 已完成账单页面presenter
 */

public class BillDoneListPresenter extends BasePresenterImpl<BillCompletedContact.view> implements BillCompletedContact.presenter{

    public BillDoneListPresenter(BillCompletedContact.view view) {
        super(view);
    }

    @Override
    public void getBillDoneListRequest(GetBillDoneListRequestBean getBillDoneListRequestBean) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),gson.toJson(getBillDoneListRequestBean));
        Api.getInstance().getBillDoneList(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showLoadingDialog("");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetBillDoneListResponseBean>() {
                    @Override
                    public void accept(GetBillDoneListResponseBean getBillDoneListResponseBean) throws Exception {
                        view.dismissLoadingDialog();
                        view.getBillDoneListResponse(getBillDoneListResponseBean);
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
