package com.loanuncle.gm.juke.presenter;

import com.loanuncle.gm.baselibrary.mvpbase.baseimpl.BasePresenterImpl;
import com.loanuncle.gm.baselibrary.retrofit.ExceptionHelper;
import com.loanuncle.gm.juke.bean.request.DeleteBillRequestBean;
import com.loanuncle.gm.juke.bean.request.GetBillListRequestBean;
import com.loanuncle.gm.juke.bean.request.RepayMentRequestBean;
import com.loanuncle.gm.juke.bean.response.DeleteBillResponseBean;
import com.loanuncle.gm.juke.bean.response.GetBillListResponseBean;
import com.loanuncle.gm.juke.bean.response.RepayMentResponseBean;
import com.loanuncle.gm.juke.contact.BillContact;
import com.loanuncle.gm.juke.model.Api;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by GM on 2018/8/24.
 * @description 账单提醒页面presenter
 */

public class BillListPresenter extends BasePresenterImpl<BillContact.view> implements BillContact.presenter{

    public BillListPresenter(BillContact.view view) {
        super(view);
    }

    @Override
    public void getBillListRequest(GetBillListRequestBean getBillListRequestBean) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),gson.toJson(getBillListRequestBean));
        Api.getInstance().getBillList(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showLoadingDialog("");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetBillListResponseBean>() {
                    @Override
                    public void accept(GetBillListResponseBean getBillListResponseBean) throws Exception {
                        view.dismissLoadingDialog();
                        view.getBillListResponse(getBillListResponseBean);
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
    public void deleteBillRequest(DeleteBillRequestBean deleteBillRequestBean) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),gson.toJson(deleteBillRequestBean));
        Api.getInstance().deleteBill(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showLoadingDialog("");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DeleteBillResponseBean>() {
                    @Override
                    public void accept(DeleteBillResponseBean deleteBillResponseBean) throws Exception {
                        view.dismissLoadingDialog();
                        view.deleteBillResponse(deleteBillResponseBean);
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
    public void repayMentRequest(RepayMentRequestBean repayMentRequestBean) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),gson.toJson(repayMentRequestBean));
        Api.getInstance().repayMent(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showLoadingDialog("");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RepayMentResponseBean>() {
                    @Override
                    public void accept(RepayMentResponseBean repayMentResponseBean) throws Exception {
                        view.dismissLoadingDialog();
                        view.repayMentResponse(repayMentResponseBean);
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
