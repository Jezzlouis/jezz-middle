package com.jezz.session.api;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Hugo.Wwg
 * @Since 2017-06-06
 */
public class APIResponse<T> implements Serializable {

    private static final long serialVersionUID = 5241526151768786394L;

    private final String version = "1.0.0";
    private boolean result;
    private String message = "";
    private int code;
    private T data;
    private Long serverTime;//服务器时间
    //private String requestId = MDC.get(Constants.TRACE_ID); // 请求ID, 取自MDC Constants.TRACE_ID

    public APIResponse() {
        this.setApiResponseEnum(ApiResponseEnum.SUCCESS);
    }

    private APIResponse(T t) {
        this();
        this.data = t;
    }

    private APIResponse(ApiResponseEnum result) {
        this.setApiResponseEnum(result);
    }

    private APIResponse(BaseEnum result, T t) {
        this.setApiResponseEnum(result);
        this.data = t;
    }

    private APIResponse(String message, T t) {
        this.message = message;
        this.data = t;
    }

    public static <T> APIResponse<T> returnSuccess() {
        return new APIResponse();
    }

    public static <T> APIResponse<T> returnSuccess(T data) {
        return new APIResponse(ApiResponseEnum.SUCCESS, data);
    }

    public static <T> APIResponse<T> returnSuccess(T data, BaseEnum result) {
        return new APIResponse(result, data);
    }

    public static <T> APIResponse<T> returnSuccess(T data, String errorMessage) {
        return new APIResponse(errorMessage, data);
    }

    public static <T> APIResponse<T> returnSuccess(BaseEnum result) {
        return new APIResponse(result);
    }

    public static <T> APIResponse<T> returnFail(
            BaseEnum result, String appendErrorMessage, T data) {
        APIResponse apiResponse = returnFail(result, appendErrorMessage);
        apiResponse.setData(data);
        return apiResponse;
    }

    public static <T> APIResponse<T> returnFail(BaseEnum result, String appendErrorMessage) {
        APIResponse apiResponse = returnFail(result);
        if (appendErrorMessage != null) {
            apiResponse.message = apiResponse.message + "（" + appendErrorMessage + "）";
        }
        return apiResponse;
    }

    public static <T> APIResponse<T> returnFail(BaseEnum result) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.result = false;
        apiResponse.message = result.getCode();
        apiResponse.code = result.getId();
        return apiResponse;
    }


    public static <T> APIResponse<T> returnFail(String errorMessage) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.result = false;
        apiResponse.message = errorMessage;
        apiResponse.code = ApiResponseEnum.FAIL.getId();
        return apiResponse;
    }

    public static <T> APIResponse<T> returnFail(T data, BaseEnum result) {
        return returnSuccess(data, result);
    }

    public static <T> APIResponse<T> returnFail(T data, String errorMessage) {
        return returnSuccess(data, errorMessage);
    }

    public static <T> APIResponse<T> returnFail(int errorCode, String errorMessage) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.code = errorCode;
        apiResponse.message = errorMessage;
        apiResponse.result = false;
        return apiResponse;
    }

    public static <T> APIResponse<T> returnSuccess(int errorCode, String errorMessage, T data) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.code = errorCode;
        apiResponse.message = errorMessage;
        apiResponse.result = true;
        apiResponse.setData(data);

        return apiResponse;
    }

    public Long getServerTime() {
        return serverTime;
    }

    public void setServerTime(Long serverTime) {
        this.serverTime = new Date().getTime();
    }

    public String getVersion() {
        return version;
    }

    public boolean isResult() {
        return result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setApiResponseEnum(BaseEnum apiResponseEnum) {
        this.result = apiResponseEnum.success();
        this.code = apiResponseEnum.getId();
        this.message = apiResponseEnum.getCode();
        this.serverTime = System.currentTimeMillis();
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((StringUtils.isEmpty(message)) ? 0 : message.hashCode());
        result = prime * result + (this.result ? 1231 : 1237);
        result = prime * result + code;
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        APIResponse<?> that = (APIResponse<?>) o;

        if (code != that.code) {
            return false;
        }
        if (result != that.result) {
            return false;
        }
        if (data != null ? !data.equals(that.data) : that.data != null) {
            return false;
        }
        if (message != null ? !message.equals(that.message) : that.message != null) {
            return false;
        }
        return !(version != null ? !version.equals(that.version) : that.version != null);

    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


}
