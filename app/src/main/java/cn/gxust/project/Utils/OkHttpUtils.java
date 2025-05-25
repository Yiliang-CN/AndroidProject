package cn.gxust.project.Utils;

import android.os.Looper;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import android.os.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpUtils {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final Handler handler;
    private final OkHttpClient okHttpClient;
    private static final OkHttpUtils instance = new OkHttpUtils();

    private OkHttpUtils() {
        handler = new Handler(Looper.getMainLooper());

        // 创建OkHttpClient实例
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpUtils getInstance() {
        return instance;
    }

    public void doGet(String url, OkHttpCallback okHttpCallback) {
        // 构建请求
        Request request = new Request.Builder().
                url(url).
                build();

        httpRequest(request, okHttpCallback);
    }

    public void doPost(String url, String jsonRequestBody, OkHttpCallback okHttpCallback) {
        // 构建请求体
        RequestBody body;
        if (jsonRequestBody != null) {
            body = RequestBody.create(jsonRequestBody, JSON);
        }else{
            body = RequestBody.create("{}", JSON);
        }
        
        // 构建请求
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        httpRequest(request, okHttpCallback);
    }

    private void httpRequest(Request request, OkHttpCallback okHttpCallback) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handler.post(() -> okHttpCallback.onFailure(e));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // 处理响应
                if (response.isSuccessful()) {
                    // 获取响应体
                    ResponseBody body = response.body();
                    if (body != null) {
                        // 转换为字符串
                        String bodyStr = body.string();
                        // 在UI线程中处理数据
                        handler.post(() -> okHttpCallback.onResponse(bodyStr));
                    } else {
                        handler.post(() -> okHttpCallback.onFailure(new IOException("响应体为空")));
                    }
                } else {
                    handler.post(() -> okHttpCallback.onFailure(new IOException("请求失败 " + response.code())));
                }

            }
        });
    }


    public interface OkHttpCallback {
        void onResponse(String body);

        void onFailure(IOException e);
    }
}
