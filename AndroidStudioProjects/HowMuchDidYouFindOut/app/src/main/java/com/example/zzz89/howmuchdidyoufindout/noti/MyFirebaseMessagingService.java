package com.example.zzz89.howmuchdidyoufindout.noti;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.zzz89.howmuchdidyoufindout.AppMainActivity;
import com.example.zzz89.howmuchdidyoufindout.MainActivity;
import com.example.zzz89.howmuchdidyoufindout.R;
import com.example.zzz89.howmuchdidyoufindout.db.HowMuchSQLHelper;
import com.example.zzz89.howmuchdidyoufindout.db.SaveSharedPreference;
import com.example.zzz89.howmuchdidyoufindout.server_api.ServerRetroInterface;
import com.example.zzz89.howmuchdidyoufindout.server_api.search_info;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private NotificationManager notificationManager;
    private int notiId = 100;
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        Log.d("receive message", "From: " + remoteMessage.getFrom());
        //data payload, 나중에 알람 받고 아이템을 집어 넣을 때 여기서 해야할 듯
        //data payload가 최대 4kb까지 밖에 담지 못한다.
        //서버에서 얼마나 나왔는지 알려주고 REST API를 통해 받아오는 방향으로 한다.

        if(remoteMessage.getData().size() > 0){
            int size = Integer.parseInt(remoteMessage.getData().get("size"));
            String ori_keyword = remoteMessage.getData().get("keyword");

            Log.d("keyword", ori_keyword);
            Log.d("size", String.valueOf(size));
            if (size > 0){
                deleteDB(ori_keyword);
                insertInDB(ori_keyword);
            }

            popNotification(remoteMessage);
        }

        if(remoteMessage.getNotification() != null){
            Log.d("notification body", remoteMessage.getNotification().getBody());
            Log.d("notification title", remoteMessage.getNotification().getTitle());
            popNotification(remoteMessage);
        }
    }

    private void popNotification(RemoteMessage remoteMessage){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("noti", "2");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(remoteMessage.getNotification().getTitle());
        mBuilder.setContentText(remoteMessage.getNotification().getBody());
        mBuilder.setSmallIcon(R.drawable.ic_saved_list);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);
        notificationManager.notify(notiId, mBuilder.build());
    }

    private void insertInDB(final String ori_keyword){
        final HowMuchSQLHelper sqlHelper = new HowMuchSQLHelper(getApplicationContext(), HowMuchSQLHelper.DB_name, null, HowMuchSQLHelper.versionNumber);
        String user_id = SaveSharedPreference.getUserName(MyFirebaseMessagingService.this);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ServerRetroInterface.API_URL).build();
        ServerRetroInterface retroInterface = retrofit.create(ServerRetroInterface.class);
        Call<ResponseBody> call = retroInterface.getSearchItem(user_id, ori_keyword);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    try {
                        String result_body = response.body().string();
                        Log.d("response", result_body);
                        //여기에 고민을 해봐야할 듯
                        //JSONObject objects = new JSONObject(response.body().string());
                        //JSONArray array = objects.getJSONArray("result");
                        JSONArray array = new JSONArray(result_body);
                        String found_item_name[] = new String[array.length()];
                        String item_url[] = new String[array.length()];
                        int price[] = new int[array.length()];
                        String item_image_url[] = new String[array.length()];
                        String mall[] = new String[array.length()];
                        String ori_key[] = new String[array.length()];
                        int read_count[] = new int[array.length()];
                        for(int i = 0; i < array.length(); i++){
                            JSONObject object = array.getJSONObject(i);
                            found_item_name[i] = object.getString("searched_item_name");
                            item_url[i] = object.getString("searched_item_url");
                            price[i] = object.getInt("searched_price");
                            item_image_url[i] = object.getString("searched_thumbnail_url");
                            mall[i] = select_what_mall(object.getInt("searched_mall"));
                            ori_key[i] = ori_keyword;
                            read_count[i] = 0;
                            Log.d("found_item_name", found_item_name[i]);
                            Log.d("found mall", mall[i]);
                        }

                        sqlHelper.addSearch_Info(found_item_name, item_url, price, item_image_url, mall, ori_key, read_count);
                        SaveSharedPreference.setClickItem(MyFirebaseMessagingService.this, ori_keyword, true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
    private void deleteDB(String ori_keyword) {
        final HowMuchSQLHelper sqlHelper = new HowMuchSQLHelper(getApplicationContext(), HowMuchSQLHelper.DB_name, null, HowMuchSQLHelper.versionNumber);
        sqlHelper.deleteSearch_Info(ori_keyword);
    }
    private  String select_what_mall(int index){
        String mall[] = {"Auction", "11번가", "G마켓", "쿠팡", "SSG_COM", "인터파크", "CJ몰"};
        return mall[index];
    }
}
