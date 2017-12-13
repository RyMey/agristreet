package id.agristreet.agristreetapp.presenter;

import android.content.Context;

/**
 * Created by RyMey on 12/13/17.
 */

public class VerifyPhonePresenter extends BasePresenter<VerifyPhonePresenter.View> {

    private final Context context;

    public VerifyPhonePresenter(Context context, View view) {
        super(view);
        this.context = context;
    }

    public void login(String phoneNumber) {
        view.showLoading();
        view.onVerifyPhoneSuccess();
        view.dismissLoading();
    }

    public interface View extends BasePresenter.View {
        void onVerifyPhoneSuccess();
    }
}
