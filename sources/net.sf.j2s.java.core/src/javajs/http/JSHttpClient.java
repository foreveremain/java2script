package javajs.http;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * SwingJS implementation of javajs.http.HttpClient and associated classes.
 * 
 * Note that none of this code should ever be run in Java. Java implementations
 * should use their own implementations.
 * 
 * 
 * For more details on HTTP methods see:
 * https://www.w3schools.com/tags/ref_httpmethods.asp
 */
public class JSHttpClient implements HttpClient {

	/**
	 * Template class implementing addFormData. Added here to allow this class to be
	 * placed in Java projects without There is no need to maintain the actual
	 * javajs.util.AjaxURLConnection class. Basically an interface with all the body
	 * methods of an HttpURLConnection or HttpsURLConnection.
	 * 
	 * Can be initialized either directly or via HttpClientFactory.getDefault().
	 * 
	 * @author hansonr
	 *
	 */
	public abstract class AjaxURLConnection extends HttpURLConnection {

		protected AjaxURLConnection(URL u) {
			super(u);
		}

		public abstract void addFormData(String name, Object value, String contentType, String fileName);

		public abstract void getBytesAsync(Consumer<byte[]> whenDone);

	}

	public static HttpClient create() {
		return new JSHttpClient();
	}

	public JSHttpClient() {
	}

	// no OPTIONS, no TRACE

	@Override
	public HttpRequest get(URI uri) {
		return new Request(uri, "GET");
	}

	@Override
	public HttpRequest head(URI uri) {
		return new Request(uri, "HEAD");
	}

	@Override
	public HttpRequest post(URI uri) {
		return new Request(uri, "POST");

	}

	@Override
	public HttpRequest put(URI uri) {
		return new Request(uri, "PUT");
	}

	@Override
	public HttpRequest delete(URI uri) {
		return new Request(uri, "DELETE");
	}

	public class Request implements HttpRequest {

		private URI uri;
		private String method;

		private Map<String, String> htHeaders = new HashMap<>();
		private Map<String, String> htGetParams = new HashMap<>();
		private List<Object[]> listPostFiles = new ArrayList<>();

		private Consumer<? super HttpResponse> succeed;
		private BiConsumer<? super HttpResponse, Throwable> fail;
		private BiConsumer<? super HttpResponse, Throwable> always;

		/**
		 * When TRUE, all parameters and files are transmitted as binary data in
		 * multipart/form-data format.
		 */
		private boolean hasFormBody = false;
		private boolean allowInputStream = true;
		private boolean isAsync;

		AjaxURLConnection conn;

		public Request(URI uri, String method) {
			this.uri = uri;
			this.method = method.toUpperCase();
			switch (method) {
			case "GET":
			case "DELETE":
				hasFormBody = false;
				break;
			case "HEAD":
				hasFormBody = false;
				allowInputStream = false;
				break;
			case "PUT":
			case "POST":
				hasFormBody = true;
				break;
			}
		}

		@Override
		public String getMethod() {
			return method;
		}

		@Override
		public URI getUri() {
			return uri;
		}

		@Override
		public HttpRequest addHeader(String name, String value) {
			htHeaders.put(name, value);
			return this;
		}

		@Override
		public HttpRequest addParameter(String name, String value) {
			htGetParams.put(name, value);
			return this;
		}

		@Override
		public HttpRequest addFile(String name, File file) {
			listPostFiles.add(new Object[] { name, file });
			return this;
		}

		@Override
		public HttpRequest addFile(String name, InputStream stream) {
			listPostFiles.add(new Object[] { name, stream });
			return this;
		}

		@Override
		public HttpResponse execute() throws IOException {
			return executeAsync(null, null, null);
		}

		@Override
		public HttpResponse executeAsync(Consumer<? super HttpResponse> succeed,
				BiConsumer<? super HttpResponse, Throwable> fail, 
				BiConsumer<? super HttpResponse, Throwable> always) {
			isAsync = (succeed != null || fail != null || always != null);
			this.succeed = succeed;
			this.fail = fail;
			this.always = always;
			Response r = new Response();
			Runnable runner = new Runnable() {

				@Override
				public void run() {
					try {
						if (hasFormBody)
							fulfillPost();
						else
							fulfillGet();
					} catch (Exception e) {
						r.handleError(e);
					}
				}

			};
			if (isAsync)
				new Thread(runner).start();
			else
				runner.run();
			return r;
		}


		@SuppressWarnings("resource")
		public Response fulfillGet() throws Exception {
			URI uri = getUri();
			String data = "";
			for (Entry<String, String> e : htGetParams.entrySet()) {
				String val = e.getValue();
				/**
				 * @j2sNative val = encodeURIComponent(val);
				 */
				data += e.getKey() + "=" + val;
			}
			if (data.length() > 0) {
				uri = new URI(uri.toString() + "?" + data);
			}
			getConnection(uri);
			return new Response().getResponse();
		}

		public Response fulfillPost() throws IOException {
			getConnection(uri);
			Response r = new Response();
			for (int i = 0; i < listPostFiles.size(); i++) {
				Object[] name_data = listPostFiles.get(i);
				String name = (String) name_data[0];
				byte[] data = null;
				String fileName = null;
				if (name_data[1] instanceof File) {
					FileInputStream fis = new FileInputStream((File) name_data[1]);
					fileName = ((File) name_data[1]).getName();
					data = fis.readAllBytes();
					fis.close();
				} else if (name_data[1] instanceof byte[]) {
					data = (byte[]) name_data[1];
				} else if (name_data[1] instanceof InputStream) {
					InputStream is = (InputStream) name_data[1];
					data = is.readAllBytes();
					is.close();
				} else {
					// unlikely, since we only allowed only File and InputStream formats!
					if (!r.handleError(
							new java.io.InvalidObjectException("JSHttpClient file data type error for: " + name)))
						return r;
				}
				conn.addFormData(name, data, null, fileName);
			}
			for (Entry<String, String> e : htGetParams.entrySet()) {
				conn.addFormData(e.getKey(), e.getValue(), null, null);
			}
			return r.getResponse();
		}

		/**
		 * JavaScript only here.
		 * 
		 * Create a connection that will be a javajs.util.AjaxURLConnection.
		 * 
		 * @param uri
		 * @throws IOException
		 */
		private void getConnection(URI uri) throws IOException {
			try {
				conn = (AjaxURLConnection) uri.toURL().openConnection();
				conn.setUseCaches(false);
				if (allowInputStream)
					conn.setDoInput(true);
				if (hasFormBody)
					conn.setDoOutput(true);
				conn.setRequestMethod(method);
				for (Entry<String, String> e : htHeaders.entrySet()) {
					conn.addRequestProperty(e.getKey(), e.getValue());
				}
			} catch (MalformedURLException e) {
				throw new IOException(e + ": " + uri);
			}
		}

		public class Response implements HttpResponse {

			private int state = 0;

			ByteArrayInputStream inputStream;
			
			private Throwable exception;

			/**
			 * The call to get the response code, initiating a connection and possibly
			 * throwing an UnknownHostException.
			 * 
			 * (Despite the fact that URL.openConnection() has already been issued, and
			 * despite what is in the Java JavaDoc, that call actually does not do anything
			 * for a URLConnection. It goes as far as to create an UNRESOLVED InetSocket,
			 * but no attempt to make an actual connection is made until getInputStream() or
			 * getResponseCode() is called.)
			 * 
			 * @throws IOException
			 */
			Response getResponse() throws IOException {
				state = 0;
				if (!isAsync) {
					// possibly throw an exception here such as UnknownHostException, but not
					// FileNotFound
					state = conn.getResponseCode();
					return this;
				}

				new Thread(new Runnable() {

					@Override
					public void run() {
						// asynchronous methods cannot throw an exception.
						if (allowInputStream) {
							conn.getBytesAsync(new Consumer<byte[]>() {

								@Override
								public void accept(byte[] t) {
									doCallback(t != null);
								}

							});
						} else {
							try {
								state = conn.getResponseCode();
							} catch (IOException e) {
								exception = e;
							}
							doCallback(state == 0 || state >= 400);
						}
					}
				}).start();
				return this;
			}

			protected void doCallback(boolean ok) {
				ok &= (exception == null);
				if (ok && succeed != null)
					succeed.accept(this);
				else if (!ok && fail != null)
					fail.accept(this, exception);
				if (always != null)
					always.accept(this, exception);
			}

			@Override
			public int getStatusCode() {
				try {
					return (state != 0 ? state : conn.getResponseCode());
				} catch (Throwable e) {
					handleError(e);
					return state;
				}
			}

			/**
			 * 
			 * @param e
			 * @return true if aSynchronous and has been handled
			 */
			private boolean handleError(Throwable e) {
				exception = e;
				// setting e = null to indicated handled.
				if (isAsync) {
					if (fail != null) {
						fail.accept(this, e);
						e = null;
					}
					if (always != null) {
						always.accept(this, e);
						e = null;
					}
				}
				return e == null;
			}

			@Override
			public Map<String, String> getHeaders() {
				Map<String, String> headers = new LinkedHashMap<>();
				Map<String, List<String>> map = conn.getHeaderFields();
				for (Entry<String, List<String>> e : map.entrySet()) {
					String name = e.getKey();
					List<String> list = e.getValue();
					String val = null;
					for (int i = 0; i < list.size(); i++)
						val = (val == null ? "" : val + ",") + list.get(i);
					if (val != null)
						headers.put(name, val);
				}
				return headers;
			}

			@Override
			public String getText() throws IOException {
				return new String(getContent().readAllBytes());
			}

			/**
			 * In SwingJS, this is always a ByteArrayInputStream.
			 */
			@Override
			public InputStream getContent() throws IOException {
				return (inputStream == null ? (inputStream = (ByteArrayInputStream) conn.getInputStream())
						: inputStream);
			}

			@Override
			public void close() {
				try {
					conn.disconnect();
				} catch (Throwable t) {
					// ignore
				}
				for (int i = 0; i < listPostFiles.size(); i++) {
					Object[] name_data = listPostFiles.get(i);
					if (name_data[1] instanceof InputStream) {
						InputStream is = (InputStream) name_data[1];
						try {
							is.close();
						} catch (Throwable t) {
							// ignore
						}
					}
				}
			}

		}

	}

}
