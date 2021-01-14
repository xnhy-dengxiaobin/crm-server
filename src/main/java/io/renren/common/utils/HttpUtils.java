package io.renren.common.utils;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

    public static int CONNECTION_TIMEOUT = 10000; // 默认连接超时时间10s
    public static int SO_TIMEOUT = 20000; // 默认数据传输超时时间20s

    private static void processHeader(HttpRequestBase request, Map<String, String> headers) {
        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
        }
    }

    /**
     * get
     *
     * @param url
     * @param headers
     * @param querys
     * @return
     * @throws Exception
     */
    public static HttpResponse doGet(String url,
                                     Map<String, String> headers,
                                     Map<String, String> querys,
                                     int connectionTimeout,
                                     int soTimeout) {
        try {
            HttpClient httpClient = wrapClient(url, connectionTimeout, soTimeout);

            HttpGet request = new HttpGet(buildUrl(url, "", querys));
            processHeader(request, headers);

            return httpClient.execute(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpResponse doGet(String host) {
        return doGet(host, null, CONNECTION_TIMEOUT, SO_TIMEOUT);
    }

    public static HttpResponse doGet(String host, int connectionTimeout, int soTimeout) {
        return doGet(host, null, null, connectionTimeout, soTimeout);
    }

    public static HttpResponse doGet(String host, Map<String, String> querys) {
        return doGet(host, null, querys);
    }

    public static HttpResponse doGet(String host, Map<String, String> querys, int connectionTimeout, int soTimeout) {
        return doGet(host, null, querys, connectionTimeout, soTimeout);
    }

    public static HttpResponse doGet(String host, Map<String, String> headers, Map<String, String> querys) {
        return doGet(host, headers, querys, CONNECTION_TIMEOUT, SO_TIMEOUT);
    }

    /**
     * post form
     *
     * @param url
     * @param headers
     * @param querys
     * @param bodys
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String url,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      Map<String, String> bodys,
                                      int connectionTimeout,
                                      int soTimeout) {
        try {
            HttpClient httpClient = wrapClient(url, connectionTimeout, soTimeout);

            HttpPost request = new HttpPost(buildUrl(url, "", querys));
            processHeader(request, headers);

            if (bodys != null) {
                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

                for (String key : bodys.keySet()) {
                    nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
                }
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
                formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
                request.setEntity(formEntity);
            }

            return httpClient.execute(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpResponse doPost(String url, Map<String, String> bodys) {
        return doPost(url, null, null, bodys, CONNECTION_TIMEOUT, SO_TIMEOUT);
    }

    public static HttpResponse doPost(String url, Map<String, String> bodys,
                                      int connectionTimeout,
                                      int soTimeout) {
        return doPost(url, null, null, bodys, connectionTimeout, soTimeout);
    }

    public static HttpResponse doPost(String url, Map<String, String> headers, Map<String, String> bodys) {
        return doPost(url, headers, null, bodys, CONNECTION_TIMEOUT, SO_TIMEOUT);
    }

    /**
     * Post String
     *
     * @param url
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String url,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      String body,
                                      int connectionTimeout,
                                      int soTimeout) {
        try {
            HttpClient httpClient = wrapClient(url, connectionTimeout, soTimeout);

            HttpPost request = new HttpPost(buildUrl(url, "", querys));
            processHeader(request, headers);

            if (StringUtils.isNotBlank(body)) {
                request.setEntity(new StringEntity(body, "utf-8"));
            }

            return httpClient.execute(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static HttpResponse doPost(String url, String body) {
        return doPost(url, null, null, body, CONNECTION_TIMEOUT, SO_TIMEOUT);
    }

    public static HttpResponse doPost(String url, String body,int connectionTimeout,int soTimeout) {
        return doPost(url, null, null, body, connectionTimeout, soTimeout);
    }

    public static HttpResponse doPost(String url, Map<String, String> headers, String body) {
        return doPost(url, headers, null, body, CONNECTION_TIMEOUT, SO_TIMEOUT);
    }

    public static void main(String[] args) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        String rawParam = "Agent=1&BoLi=0&BuJiMianCheSun=0&BuJiMianChengKe=0&BuJiMianDaoQiang=0&BuJiMianHuaHen=0&BuJiMianJingShenSunShi=0&BuJiMianSanZhe=0&BuJiMianSheShui=0&BuJiMianSiJi=0&BuJiMianZiRan=0&CarOwnersName=冯XX&CarVin=WAURGB4H8CN034XXX&CheSun=0&ChengKe=0&CityCode=1&CustKey=bihutest520&DaoQiang=0&EngineNo=CMD006XXX&ForceTax=2&HolderIdCard=110104195606080XXX&HolderIdType=1&HolderName=冯XX&HuaHen=0&IdCard=110104195606080XXX&InsuredIdCard=110104195606080XXX&InsuredIdType=1&InsuredName=冯XX&LicenseNo=京XXXXXX&MoldName=奥迪AUDI A8L 6.3FSI QUATTRO轿车&OwnerIdCardType=1&QuoteGroup=1&QuoteParalelConflict=0&RegisterDate=2014-09-22&SanZhe=0&SiJi=0&SubmitGroup=1&ZiRan=0&SecCode=c85ccd0335f0ff060777c2176aecd1f8";
        String[] temp = rawParam.split("&");
        Map<String, String> body = new HashMap();
        for (String kv : temp) {
            String[] arr = kv.split("=");
            if (arr == null || arr.length <= 0) {
                continue;
            }
            String key = arr[0];
            String value = arr.length > 1 ? arr[1] : "";
            body.put(key, value);
        }
        /*Map<String, String> body = new HashMap() {{
            put("status", "SUCCESS");
            put("formId", "OPERATOR_LIANTONGGUANGDONG_DP");
            put("orgCode", "sl2018012900000000");
            put("prodCode", "OPERATOR");
            put("pushType", "COLLECT");
            put("bizType", "BIZ_TYPE");
            put("bizNo", "S20180118101148operb433d0924a834");
            put("orgBizNo", "C2018011815162415087164651700000");
            put("itemCode", "OPERATOR_LIANTONGGUANGDONG");
        }};*/
        try {
            HttpResponse rsp = doPost("http://iu.91bihu.com/api/CarInsurance/PostNewPrecisePrice", headers, null, body, CONNECTION_TIMEOUT, SO_TIMEOUT);
            System.out.println("abc");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Post stream
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String host, String path, String method,
                                      Map<String, String> headers,
                                      Map<String, String> querys,
                                      byte[] body,
                                      int connectionTimeout,
                                      int soTimeout)
            throws Exception {
        HttpClient httpClient = wrapClient(host, connectionTimeout, soTimeout);

        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        processHeader(request, headers);

        if (body != null) {
            request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }

    /**
     * Put String
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPut(String host, String path, String method,
                                     Map<String, String> headers,
                                     Map<String, String> querys,
                                     String body,
                                     int connectionTimeout,
                                     int soTimeout)
            throws Exception {
        HttpClient httpClient = wrapClient(host, connectionTimeout, soTimeout);

        HttpPut request = new HttpPut(buildUrl(host, path, querys));
        processHeader(request, headers);

        if (StringUtils.isNotBlank(body)) {
            request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }

    /**
     * Put stream
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPut(String host, String path, String method,
                                     Map<String, String> headers,
                                     Map<String, String> querys,
                                     byte[] body,
                                     int connectionTimeout,
                                     int soTimeout)
            throws Exception {
        HttpClient httpClient = wrapClient(host, connectionTimeout, soTimeout);

        HttpPut request = new HttpPut(buildUrl(host, path, querys));
        processHeader(request, headers);

        if (body != null) {
            request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }

    /**
     * Delete
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @return
     * @throws Exception
     */
    public static HttpResponse doDelete(String host, String path, String method,
                                        Map<String, String> headers,
                                        Map<String, String> querys,
                                        int connectionTimeout,
                                        int soTimeout)
            throws Exception {
        HttpClient httpClient = wrapClient(host, connectionTimeout, soTimeout);

        HttpDelete request = new HttpDelete(buildUrl(host, path, querys));
        processHeader(request, headers);

        return httpClient.execute(request);
    }

    private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }

        return sbUrl.toString();
    }

    private static HttpClient wrapClient(String host, int connectionTimeout, int soTimeout) {
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
        if (host.startsWith("https://")) {
            sslClient(httpClient);
        }

        return httpClient;
    }

    private static void sslClient(HttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] xcs, String str) {

                }

                public void checkServerTrusted(X509Certificate[] xcs, String str) {

                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String res2String(HttpResponse response) {
        try {
            int stat = response.getStatusLine().getStatusCode();
            if (stat != 200) {
                LOG.error("Http code: {}", stat);
                LOG.error("http header error msg: {}", response.getFirstHeader("X-Ca-Error-Message"));
                LOG.error("Http body error msg: {}", EntityUtils.toString(response.getEntity()));
                return "";
            }

            String res = EntityUtils.toString(response.getEntity());
            return res;
        } catch (Exception e) {
            LOG.error("", e);
        }
        return "";
    }

    public static Map<String, Object> res2Map(HttpResponse response) {
        try {
            String result = res2String(response);
            return JsonUtil.readJsonObject(result, Map.class);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }
}
