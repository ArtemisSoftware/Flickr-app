package com.titan.flickrapp.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

}
