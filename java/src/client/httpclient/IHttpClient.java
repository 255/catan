package client.httpclient;


public interface IHttpClient {

    /**
     *
     * @param url
     * @return JSON string as server response
     */
    Object get(String url);

    /**
     *
     * @param url
     * @param obj
     * @return JSON string as server response
     */
    Object post(String url, Object obj);
}
