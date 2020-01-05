package com.titan.flickrapp.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.titan.flickrapp.requests.responses.BaseResponse;
import com.titan.flickrapp.requests.responses.UserSearchResponse;

import static com.titan.flickrapp.util.Status.*;

public class ApiResponse<T> {

    public final Status status;

    @Nullable
    public final T data;


    @Nullable
    public final String message;

    private ApiResponse(Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> loading() {
        return new ApiResponse(LOADING, null, null);
    }

    public static <T> ApiResponse<T> success(@Nullable T data) {
        return new ApiResponse(SUCCESS, data, null);
    }

    public static <T> ApiResponse<T> error(@NonNull String message) {
        return new ApiResponse(ERROR, null, message);
    }


    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != getClass() || obj.getClass() != ApiResponse.class){
            return false;
        }

        ApiResponse<T> resource = (ApiResponse) obj;

        if(resource.status != this.status){
            return false;
        }

        if(this.data != null){
            if(resource.data != this.data){

                if(resource.data instanceof UserSearchResponse & this.data instanceof UserSearchResponse){

                    if((((UserSearchResponse) resource.data).user.nsid.equals(((UserSearchResponse) this.data).user.nsid))== false){
                        return false;
                    }
                }
                else {

                    return false;
                }
            }
        }

        if(resource.message != null){
            if(this.message == null){
                return false;
            }
            if(!resource.message.equals(this.message)){
                return false;
            }
        }

        return true;
    }
}
