package nohi.test.httpclient;

import okhttp3.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-03-25 21:16
 **/
public class Testhttp {
    @Test
    public void testhttpclient(){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String url = "http://www.baidu.com";
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testOkhttp() throws InterruptedException {
        String url = "http://www.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()// 默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println("onResponse....");
            }

            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("onFailure");
            }
        });

        Thread.sleep(2000);
        System.out.println("=====");
    }
}
