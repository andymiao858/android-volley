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
import com.android.volley.Response;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author miaohd
 */
public class RabbitStringRequest extends RabbitRequest<String> {

	public RabbitStringRequest(int method, String url, Response.Listener successListener,
			Response.ErrorListener errorListener) {
		super(method, url, String.class, successListener, errorListener);
	}

	public RabbitStringRequest(int method, String url, Map<String, String> params,
			Response.Listener successListener, Response.ErrorListener errorListener) {
		super(method, url, String.class, params, successListener, errorListener);
	}

	public RabbitStringRequest(int method, String url, Map<String, String> params,
			Map<String, String> headers, Response.Listener successListener, Response.ErrorListener errorListener) {
		super(method, url, String.class, params, headers, successListener, errorListener);
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		String parsed;
		try {
			parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
		} catch (UnsupportedEncodingException e) {
			parsed = new String(response.data);
		}
		return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
	}

}
