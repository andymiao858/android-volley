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

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author miaohd
 */
public class RabbitGsonRequest<T> extends RabbitRequest<T> {

	/** Default charset for JSON request. */
	protected static final String PROTOCOL_CHARSET = "utf-8";

	/** Content type for request. */
	private static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", PROTOCOL_CHARSET);

	private final Gson gson;

	public RabbitGsonRequest(int method, String url, Response.Listener successListener,
							 Response.ErrorListener errorListener) {
		super(method, url, null, successListener, errorListener);
		gson = new GsonBuilder().create();
	}

	public RabbitGsonRequest(int method, String url, Map<String, String> params, Response.Listener successListener,
							 Response.ErrorListener errorListener) {
		super(method, url, params, successListener, errorListener);
		gson = new GsonBuilder().create();
	}

	public RabbitGsonRequest(int method, String url, Map<String, String> params, Map<String, String> headers, Response.Listener successListener, Response.ErrorListener errorListener) {
		super(method, url, params, headers, successListener, errorListener);
		gson = new GsonBuilder().create();
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String gsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
			T responseDto = gson.fromJson(gsonString, clazz);
			return Response.success(responseDto, HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		} catch (Exception e) {
			return Response.error(new VolleyError(e));
		}
	}

	@Override
	protected void deliverResponse(T response) {
		if (successListener != null) {
			successListener.onResponse(response);
		}
	}

	@Override
	public String getBodyContentType() {
		return PROTOCOL_CONTENT_TYPE;
	}
}
