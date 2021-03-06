package twitter4j.internal.http;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import twitter4j.TwitterException;
import twitter4j.auth.Authorization;
import twitter4j.conf.ConfigurationContext;

public final class HttpClientWrapper
  implements Serializable
{
  private static final long serialVersionUID = -6511977105603119379L;
  private HttpClient http;
  private HttpResponseListener httpResponseListener;
  private final Map<String, String> requestHeaders;
  private final HttpClientWrapperConfiguration wrapperConf;
  
  public HttpClientWrapper()
  {
    this.wrapperConf = ConfigurationContext.getInstance();
    this.requestHeaders = this.wrapperConf.getRequestHeaders();
    this.http = HttpClientFactory.getInstance(this.wrapperConf);
  }
  
  public HttpClientWrapper(HttpClientWrapperConfiguration paramHttpClientWrapperConfiguration)
  {
    this.wrapperConf = paramHttpClientWrapperConfiguration;
    this.requestHeaders = paramHttpClientWrapperConfiguration.getRequestHeaders();
    this.http = HttpClientFactory.getInstance(paramHttpClientWrapperConfiguration);
  }
  
  private HttpResponse request(HttpRequest paramHttpRequest)
    throws TwitterException
  {
    try
    {
      HttpResponse localHttpResponse = this.http.request(paramHttpRequest);
      if (this.httpResponseListener != null) {
        this.httpResponseListener.httpResponseReceived(new HttpResponseEvent(paramHttpRequest, localHttpResponse, null));
      }
      return localHttpResponse;
    }
    catch (TwitterException localTwitterException)
    {
      if (this.httpResponseListener != null) {
        this.httpResponseListener.httpResponseReceived(new HttpResponseEvent(paramHttpRequest, null, localTwitterException));
      }
      throw localTwitterException;
    }
  }
  
  public HttpResponse delete(String paramString)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.DELETE, paramString, null, null, this.requestHeaders));
  }
  
  public HttpResponse delete(String paramString, Authorization paramAuthorization)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.DELETE, paramString, null, paramAuthorization, this.requestHeaders));
  }
  
  public HttpResponse delete(String paramString, HttpParameter[] paramArrayOfHttpParameter)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.DELETE, paramString, paramArrayOfHttpParameter, null, this.requestHeaders));
  }
  
  public HttpResponse delete(String paramString, HttpParameter[] paramArrayOfHttpParameter, Authorization paramAuthorization)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.DELETE, paramString, paramArrayOfHttpParameter, paramAuthorization, this.requestHeaders));
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    for (;;)
    {
      return bool;
      if ((paramObject == null) || (getClass() != paramObject.getClass()))
      {
        bool = false;
      }
      else
      {
        HttpClientWrapper localHttpClientWrapper = (HttpClientWrapper)paramObject;
        if (!this.http.equals(localHttpClientWrapper.http)) {
          bool = false;
        } else if (!this.requestHeaders.equals(localHttpClientWrapper.requestHeaders)) {
          bool = false;
        } else if (!this.wrapperConf.equals(localHttpClientWrapper.wrapperConf)) {
          bool = false;
        }
      }
    }
  }
  
  public HttpResponse get(String paramString)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.GET, paramString, null, null, this.requestHeaders));
  }
  
  public HttpResponse get(String paramString, Authorization paramAuthorization)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.GET, paramString, null, paramAuthorization, this.requestHeaders));
  }
  
  public HttpResponse get(String paramString, HttpParameter[] paramArrayOfHttpParameter)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.GET, paramString, paramArrayOfHttpParameter, null, this.requestHeaders));
  }
  
  public HttpResponse get(String paramString, HttpParameter[] paramArrayOfHttpParameter, Authorization paramAuthorization)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.GET, paramString, paramArrayOfHttpParameter, paramAuthorization, this.requestHeaders));
  }
  
  public int hashCode()
  {
    return 31 * (31 * this.wrapperConf.hashCode() + this.http.hashCode()) + this.requestHeaders.hashCode();
  }
  
  public HttpResponse head(String paramString)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.HEAD, paramString, null, null, this.requestHeaders));
  }
  
  public HttpResponse head(String paramString, Authorization paramAuthorization)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.HEAD, paramString, null, paramAuthorization, this.requestHeaders));
  }
  
  public HttpResponse head(String paramString, HttpParameter[] paramArrayOfHttpParameter)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.HEAD, paramString, paramArrayOfHttpParameter, null, this.requestHeaders));
  }
  
  public HttpResponse head(String paramString, HttpParameter[] paramArrayOfHttpParameter, Authorization paramAuthorization)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.HEAD, paramString, paramArrayOfHttpParameter, paramAuthorization, this.requestHeaders));
  }
  
  public HttpResponse post(String paramString)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.POST, paramString, null, null, this.requestHeaders));
  }
  
  public HttpResponse post(String paramString, Authorization paramAuthorization)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.POST, paramString, null, paramAuthorization, this.requestHeaders));
  }
  
  public HttpResponse post(String paramString, HttpParameter[] paramArrayOfHttpParameter)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.POST, paramString, paramArrayOfHttpParameter, null, this.requestHeaders));
  }
  
  public HttpResponse post(String paramString, HttpParameter[] paramArrayOfHttpParameter, Map<String, String> paramMap)
    throws TwitterException
  {
    HashMap localHashMap = new HashMap(this.requestHeaders);
    if (paramMap != null) {
      localHashMap.putAll(paramMap);
    }
    return request(new HttpRequest(RequestMethod.POST, paramString, paramArrayOfHttpParameter, null, localHashMap));
  }
  
  public HttpResponse post(String paramString, HttpParameter[] paramArrayOfHttpParameter, Authorization paramAuthorization)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.POST, paramString, paramArrayOfHttpParameter, paramAuthorization, this.requestHeaders));
  }
  
  public HttpResponse put(String paramString)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.PUT, paramString, null, null, this.requestHeaders));
  }
  
  public HttpResponse put(String paramString, Authorization paramAuthorization)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.PUT, paramString, null, paramAuthorization, this.requestHeaders));
  }
  
  public HttpResponse put(String paramString, HttpParameter[] paramArrayOfHttpParameter)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.PUT, paramString, paramArrayOfHttpParameter, null, this.requestHeaders));
  }
  
  public HttpResponse put(String paramString, HttpParameter[] paramArrayOfHttpParameter, Authorization paramAuthorization)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.PUT, paramString, paramArrayOfHttpParameter, paramAuthorization, this.requestHeaders));
  }
  
  public void setHttpResponseListener(HttpResponseListener paramHttpResponseListener)
  {
    this.httpResponseListener = paramHttpResponseListener;
  }
  
  public void shutdown()
  {
    this.http.shutdown();
  }
  
  public String toString()
  {
    return "HttpClientWrapper{wrapperConf=" + this.wrapperConf + ", http=" + this.http + ", requestHeaders=" + this.requestHeaders + ", httpResponseListener=" + this.httpResponseListener + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.http.HttpClientWrapper
 * JD-Core Version:    0.7.0.1
 */