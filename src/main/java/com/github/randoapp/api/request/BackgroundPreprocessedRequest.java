package com.github.randoapp.api.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

public class BackgroundPreprocessedRequest extends JsonObjectRequest {

    private Map<String, String> mHeaders;
    private Response.Listener<JSONObject> mBackgroundListener;
    private Response.Listener<JSONObject> mListener;

    public BackgroundPreprocessedRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> backgroundListener, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.mListener = listener;
        this.mBackgroundListener = backgroundListener;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        Response<JSONObject> jsonObject = super.parseNetworkResponse(response);
        if (mBackgroundListener != null) {
            mBackgroundListener.onResponse(jsonObject.result);
        }
        return jsonObject;
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        if (mListener != null) {
            super.deliverResponse(response);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (mHeaders == null || mHeaders.isEmpty()) {
            return super.getHeaders();
        } else {
            return mHeaders;
        }
    }

    public void setHeaders(Map<String, String> mHeaders) {
        this.mHeaders = mHeaders;
    }
}
