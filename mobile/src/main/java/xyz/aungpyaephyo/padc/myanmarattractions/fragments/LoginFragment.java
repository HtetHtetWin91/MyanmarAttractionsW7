package xyz.aungpyaephyo.padc.myanmarattractions.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import xyz.aungpyaephyo.padc.myanmarattractions.R;
import xyz.aungpyaephyo.padc.myanmarattractions.controllers.ControllerAccountControl;
import xyz.aungpyaephyo.padc.myanmarattractions.events.DataEvent;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.MyanmarAttractionsConstants;
import xyz.aungpyaephyo.padc.myanmarattractions.views.PasswordVisibilityListener;

/**
 * Created by aung on 7/15/16.
 */
public class LoginFragment extends Fragment {

    @BindView(R.id.lbl_login_title)
    TextView lblLoginTitle;

    @BindView(R.id.et_email)
    TextView etEmail;

    @BindView(R.id.et_password)
    TextView etPassword;

    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.lbl_password_hint)
    TextView lblPasswordHint;

    @BindView(R.id.lbl_register_hint)
    TextView lblRegisterHint;

    private ControllerAccountControl mControllerAccountControl;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);

        etPassword.setOnTouchListener(new PasswordVisibilityListener());


        lblLoginTitle.setText(Html.fromHtml("လြန္ခဲ့ေသာ ၃ ရက္အတြင္း <b> ၿမန္မာ့အလွ </b> သို့ Travel Destination ၇ ခု အသစ္ေရာက္ရိွလာပါသည္"));
        lblPasswordHint.setText(Html.fromHtml("<u>Password ေမ့သြားလို့လား</u>"));
        lblRegisterHint.setText(Html.fromHtml("<u> Register မလုပ္ရေသးပါက ၿပဳလုပ္ရန္</u>"));

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mControllerAccountControl = (ControllerAccountControl) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus eventBus = EventBus.getDefault();
        eventBus.unregister(this);
    }
    @OnClick(R.id.btn_login)
    public void onTapLogin(Button btnLogin) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if ( TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            //One of the required data is empty

            if (TextUtils.isEmpty(email)) {
                etEmail.setError(getString(R.string.error_missing_email));
            }

            if (TextUtils.isEmpty(password)) {
                etPassword.setError(getString(R.string.error_missing_password));
            }


        } else if (!isEmailValid(email)) {
            //Email address is not in the right format.
            etEmail.setError(getString(R.string.error_email_is_not_valid));
        } else {
            //Checking on client side is done here.
            mControllerAccountControl.onLogin( email, password);
        }

    }

    public boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(MyanmarAttractionsConstants.EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

   public void onEventMainThread(DataEvent.RefreshUserLoginStatusEvent event) {
       // tvDateOfBirth.setText(event.getDateOfBrith());
        EventBus.getDefault().register(this);
    }

}