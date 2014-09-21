package client.httpclient;


public interface IHttpClient {

    Object get(String url);

    Object post(String url, Object data);
}
