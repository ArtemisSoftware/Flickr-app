package com.titan.flickrapp.requests.responses;

import com.titan.flickrapp.util.ApiResponse;

public class CheckApiResponse {

    public static ApiResponse validate(BaseResponse response){

        switch (response.getCode()){

            case 1:
            case 100:
            case 105:
            case 116:

                return ApiResponse.error(response.getMessage());


            default:

                return ApiResponse.success(response);
        }

    }

}
//TODO: Check for error responses specific to the api
            /*

1: User not found
No user with the supplied username was found.
100: Invalid API Key
The API key passed was not valid or has expired.
105: Service currently unavailable
The requested service is temporarily unavailable.
106: Write operation failed
The requested operation failed due to a temporary issue.
111: Format "xxx" not found
The requested response format was not found.
112: Method "xxx" not found
The requested method was not found.
114: Invalid SOAP envelope
The SOAP envelope send in the request could not be parsed.
115: Invalid XML-RPC Method Call
The XML-RPC request document could not be parsed.
116: Bad URL found
One or more arguments contained a URL that has been used for abuse on Flickr.

             */