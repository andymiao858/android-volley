/*
 * Copyright [Rabbit]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

import java.util.Map;

/**
 * @author miaohd
 */
public abstract class RabbitRequest<T> extends Request<T> {

	Response.Listener<T> successListener;

	Map<String, String> params;

	Map<String, String> headers;

	public RabbitRequest(int method, String url, Response.Listener successListener,
			Response.ErrorListener errorListener) {
		this(method, url, null, null, successListener, errorListener);
	}

	public RabbitRequest(int method, String url, Map<String, String> params, Response.Listener successListener,
			Response.ErrorListener errorListener) {
		this(method, url, params, null, successListener, errorListener);
	}

	public RabbitRequest(int method, String url, Map<String, String> params, Map<String, String> headers,
			Response.Listener successListener,
			Response.ErrorListener errorListener) {
		super(method, url, errorListener);
		this.successListener = successListener;
		this.params = params;
		this.headers = headers;
	}

	@Override
	public Map<String, String> getParams() {
		return params;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers == null ? super.getHeaders() : headers;
	}

	@Override
	protected void deliverResponse(T response) {
		if (successListener != null) {
			successListener.onResponse(response);
		}
	}

	@Override
	protected void onFinish() {
		super.onFinish();
		successListener = null;
	}

	protected void logStackTrace(Throwable throwable){
		if (throwable == null ){
			return;
		}
		for (StackTraceElement trace : throwable.getStackTrace()){
			VolleyLog.e(trace.toString());
		}
	}

}
