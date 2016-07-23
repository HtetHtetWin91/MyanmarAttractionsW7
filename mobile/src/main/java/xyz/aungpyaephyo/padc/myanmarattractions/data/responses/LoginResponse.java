package xyz.aungpyaephyo.padc.myanmarattractions.data.responses;

import com.google.gson.annotations.SerializedName;

import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.UserVO;

/**
 * Created by user8 on 7/19/2016.
 */
public class LoginResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("login_user")
    private UserVO loginUser;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public UserVO getLoginUser() {
        return loginUser;
    }
}
