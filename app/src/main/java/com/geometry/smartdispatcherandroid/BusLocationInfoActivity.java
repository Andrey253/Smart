package com.geometry.smartdispatcherandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import adapters.BusEndItemAdapter;
import api.ApiService;
import api.Client;
import common.SystemConstants;
import helpers.CommonHelper;
import model.Bus;
import model.BusState;
import model.EndStationBusQueueItem;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.geometry.smartdispatcherandroid.R.drawable.gradient_backgroundbackward;
import static com.geometry.smartdispatcherandroid.R.drawable.gradient_backgroundforward;
import static com.geometry.smartdispatcherandroid.R.drawable.gradient_backgroundnorm;

/**
 * Активность для отображения данных о местоположении автобуса
 * @author DushinovSV
 * Created by house on 29.07.2018
 * rebuilded Boyko 22.09.2019
 */
public class BusLocationInfoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{ // Добавлено Бойко А.А. 07,07,2019
    private ListView listqueue; // лист очереди
    private BusEndItemAdapter busEndItemAdapter; //адаптер для очереди

    private LinearLayout transitLinearLayout;
    private LinearLayout linercurrentbus;
    private LinearLayout endStationLinearLayout;

    private TextView textViewBeforeBus;
    private TextView textViewBeforeBusTime;
    private TextView textViewBeforeStationTitle;
    private TextView textViewBeforeStation;
    private TextView textViewBeforeStationTime;

    private TextView textViewAfterBus;
    private TextView textViewAfterBusTime;

    private TextView textViewRouteBusState;
    private TextView textViewRouteBusStateWord;
    private TextView textViewTransitCurrentTimeTitle;
    private TextView textViewTransitCurrentTime;
    private TextView textViewEndStationCurrentTimeTitle;//Текущее время ТЕКСТ
    private TextView textViewEndStationCurrentTime;//Текущее время
    private TextView textViewPositionQueue; // удалить textViewPositionQueue номер очереди


    private LinearLayout busNotRouteLinearLayout;
    private TextView textViewBusNotRouteValue;

    private LinearLayout dataNotFoundLinearLayout;
    private TextView textViewDataNotFoundValue;

    private Typeface typeFace;

    private SharedPreferences sPref;

    /**
     * Активный водитель
     */
    private User activeUser;

    /**
     * Идентификатор активного автобуса
     */
    private String activeBusId;

    private Bus activeBus;

    //final Handler uiHandler = new Handler();
    private Timer myTimer;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        myTimer.cancel();
        //myTimer=null;
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_location_info);
/*        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // Отключение угасания экрана
        NavigationView navigationView = findViewById(R.id.toolbar); //Вешаем слушатель на боковое меню
        navigationView.setNavigationItemSelectedListener(this);     // Бойко А.А. 07,07,2019

        typeFace = Typeface.createFromAsset(getAssets(), "fonts/Helvetica CE Regular.ttf");

        transitLinearLayout = findViewById(R.id.transitView);
        linercurrentbus = findViewById(R.id.linercurrentbus); // Подсвечиваем линер
        textViewBeforeBus = findViewById(R.id.textViewBeforeBus);
        textViewBeforeBusTime = findViewById(R.id.textViewBeforeBusTime);
        textViewAfterBus = findViewById(R.id.textViewAfterBus);               // Сзади идущий
        textViewAfterBusTime = findViewById(R.id.textViewAfterBusTime);
        textViewBeforeStationTitle = findViewById(R.id.textViewBeforeStationTitle);
        textViewBeforeStation = findViewById(R.id.textViewBeforeStation);
        textViewBeforeStationTime = findViewById(R.id.textViewBeforeStationTime);
        textViewRouteBusState = findViewById(R.id.textViewRouteBusState);
        textViewRouteBusStateWord = findViewById(R.id.textViewRouteBusStateWord);
        textViewTransitCurrentTimeTitle = findViewById(R.id.textViewTransitCurrentTimeTitle);
        textViewTransitCurrentTime = findViewById(R.id.textViewTransitCurrentTime);

        endStationLinearLayout = findViewById(R.id.endStationView);
        textViewEndStationCurrentTimeTitle = findViewById(R.id.textViewEndStationCurrentTimeTitle);//Текущее время ТЕКСТ
        textViewEndStationCurrentTime = findViewById(R.id.textViewEndStationCurrentTime);//Текущее время
        textViewPositionQueue = findViewById(R.id.textViewPositionQueue); // удалить textViewPositionQueue номер очереди

        busNotRouteLinearLayout = findViewById(R.id.busNotRouteView);
        textViewBusNotRouteValue = findViewById(R.id.textViewBusNotRouteValue);

        dataNotFoundLinearLayout = findViewById(R.id.dataNotFoundView);
        textViewDataNotFoundValue = findViewById(R.id.textViewDataNotFoundValue);

        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        //userController = new UserController(this, deviceId);


        activeUser  = new Gson().fromJson(getIntent().getStringExtra("activeUser"), User.class);
        activeBus   = new Gson().fromJson(getIntent().getStringExtra("activeBus" ), Bus.class);

        activeBusId = activeBus.getBusId();

//        setFont();
        hideAllControls();
        loadPreferences();

        myTimer = new Timer();
        final Handler uiHandler = new Handler();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                uiHandler.post(new Runnable() {
                    public void run() {
                        new BusLocationInfoActivity.BusLocationRequest().execute(activeUser.getToken(), activeBusId);
                    }
                });
            }
        },0L, SystemConstants.UPDATE_DATA_SECONDS * 1000);
    }

    /**
     * Метод устанавливает шрифты для экрана
     */
    private void setFont() {

        textViewBeforeBus.setTypeface(typeFace);
        textViewBeforeBusTime.setTypeface(typeFace);
        textViewBeforeStationTitle.setTypeface(typeFace);
        textViewBeforeStation.setTypeface(typeFace);
        textViewBeforeStationTime.setTypeface(typeFace);
        textViewRouteBusState.setTypeface(typeFace);
        textViewRouteBusStateWord.setTypeface(typeFace);
        textViewTransitCurrentTimeTitle.setTypeface(typeFace);
        textViewTransitCurrentTime.setTypeface(typeFace);
        textViewEndStationCurrentTimeTitle.setTypeface(typeFace);//Текущее время ТЕКСТ
        textViewEndStationCurrentTime.setTypeface(typeFace);//Текущее время
        textViewBusNotRouteValue.setTypeface(typeFace);
        textViewDataNotFoundValue.setTypeface(typeFace);
    }

    /**
     * Асинхронный запрос для получения текущего местоположения водителя
     */
    class BusLocationRequest extends AsyncTask<String, Void, Object[]> {
        @Override
        protected Object[] doInBackground(String... arg) {
            try {
                String token = arg[0];
                String busId = arg[1];

                final ApiService api = Client.getApiService();
                Call<BusState> call = api.getBusLocationData(token, busId);
                call.enqueue(new Callback<BusState>() {
                    @Override
                    public void onResponse(Call<BusState> call, Response<BusState> response) {
                        if (response.isSuccessful()) {
                            System.out.println("my response "+response);
                            if(CommonHelper.isNull(response.body())) {
                                Log.e(SystemConstants.LOG_TAG, "Error send http location data request null result");}
                            if(response.body().getBusLocationId() == SystemConstants.BUS_END_STATION_LOCATION) {
                                setEndStationInfo(response.body());
                            }
                            if (response.body().getBusLocationId() == SystemConstants.BUS_ROUTE_LOCATION) {
                                setTransitInfo(response.body());
                            }
                            if (response.body().getBusLocationId() == SystemConstants.BUS_NOT_FOUND_ROUTE_LOCATION) {
                                setBusNotFoundRouteInfo(response.body());
                            }
                            if (response.body().getBusLocationId() == Integer.MAX_VALUE) {
                                showErrorMessage();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BusState> call, Throwable t) {
                        System.out.println("my данныe не получены ");
                    }
                });

            } catch(Exception ex) {
                String errorMessage = "Error send http location data request";
                Log.e(SystemConstants.LOG_TAG, errorMessage, ex);
            }
            return new Object[] { Integer.MAX_VALUE, null };
        }

        @Override
        protected void onPostExecute(Object[] responce) {
            super.onPostExecute(responce);
        }
    }

    /**
     * Асинхронный запрос для выхода из приложения
     */
    class LogoutRequest extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... arg) {
            try {
                String token = arg[0];
                String parameters = String.format("/smartdispatcher/android/logout/%s", token);

                HttpGet http = new HttpGet(CommonHelper.getHostName() + parameters);
                DefaultHttpClient hc = new DefaultHttpClient();
                ResponseHandler response = new BasicResponseHandler();
                hc.execute(http, response);

                return true;
            } catch(Exception ex) {
                String errorMessage = "Error send http logout request";
                Log.e(SystemConstants.LOG_TAG, errorMessage, ex);
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result) {
                startActivity(new Intent(BusLocationInfoActivity.this, LoginActivity.class));
            }
        }
    }

    /**
     * Метод делает невидимым все представления на экране
     */
    public void hideAllControls() {
        setTitle(SystemConstants.SPACE_STRING);
        endStationLinearLayout.setVisibility(View.GONE);
        transitLinearLayout.setVisibility(View.GONE);
        busNotRouteLinearLayout.setVisibility(View.GONE);
        dataNotFoundLinearLayout.setVisibility(View.GONE);
    }

    /**
     * Метод устанавливает данные на конечной остановке для отображения в представлении
     * @param busState Объект с данными для отображения
     */
    public void setEndStationInfo(BusState busState) {
        setTitle(SystemConstants.PHRASE_END_STATION_LOCATION_TITLE);

        endStationLinearLayout.setVisibility(View.VISIBLE);
        transitLinearLayout.setVisibility(View.GONE);
        busNotRouteLinearLayout.setVisibility(View.GONE);
        dataNotFoundLinearLayout.setVisibility(View.GONE);

        textViewEndStationCurrentTime.setText(busState.getCurrentRealTime());//Текущее время

        //TODO сдедлать правильное отображение
        /*
        textViewDeparturePlanTime.setText(endStationInfo.getBusDepartureTime());
        textViewPositionQueue.setText(String.valueOf(endStationInfo.getNumberQueue()));
        textViewDeparturePlanTimeInterval.setText(endStationInfo.getBusDepartureTimeInterval());
        */

       // clearBusQueue();
        addBusQueue(busState);
    }

    /**
     * Метод очищает очередь автобусов
     * @param busState Объект с данными для отображения
     */
    private void addBusQueue(BusState busState) {
        //TODO этот код наверное совсем не нужен
        List<EndStationBusQueueItem> queueBuses = busState.getQueueBuses();
        System.out.println(queueBuses);
        listqueue = findViewById(R.id.listqueue);

        //String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        busEndItemAdapter = new BusEndItemAdapter(this, queueBuses);
        listqueue.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listqueue.setAdapter(busEndItemAdapter);
        //queueBuses

    }

    /**
     * Метод устанавливает данные в пути для отображения в представлении
     * @param busState Объект с данными для отображения
     */
    public void setTransitInfo(BusState busState) {
        setTitle(SystemConstants.PHRASE_ROUTE_LOCATION_TITLE);

        transitLinearLayout.setVisibility(View.VISIBLE);
        endStationLinearLayout.setVisibility(View.GONE);
        busNotRouteLinearLayout.setVisibility(View.GONE);
        dataNotFoundLinearLayout.setVisibility(View.GONE);

        textViewBeforeBus.setText(busState.getBeforeBusNumber());
        textViewBeforeBusTime.setText(busState.getBeforeBusTimeInterval());
        textViewAfterBus.setText(busState.getAfterBusNumber());           // after bus
        textViewAfterBusTime.setText(busState.getAfterBusTimeInterval());
        textViewBeforeStation.setText(busState.getBeforeStationName());
        textViewBeforeStationTime.setText(busState.getBeforeStationArrivalTime());

        setBusStateInfo(busState);
        textViewTransitCurrentTime.setText(busState.getCurrentRealTime());
    }

    /**
     * Метод устанавливает значение времени с текущей ситуацией автобуса на маршруте
     * @param busState Объект с данными для отображения
     */
    public void setBusStateInfo(BusState busState) {
        textViewRouteBusState.setText(busState.getBusState());
        switch(busState.getBusStateCode()) {
            case 1:
                textViewRouteBusStateWord.setText(SystemConstants.PHRASE_YOU_HURRY);
                linercurrentbus.setBackgroundDrawable(getResources().getDrawable(gradient_backgroundforward));
                break;
            case 0:
                textViewRouteBusStateWord.setText(SystemConstants.PHRASE_YOU_NORM);
                linercurrentbus.setBackgroundDrawable(getResources().getDrawable(gradient_backgroundnorm));
                break;
            case -1:
                textViewRouteBusStateWord.setText(SystemConstants.PHRASE_YOU_LATENESS);
                linercurrentbus.setBackgroundDrawable(getResources().getDrawable(gradient_backgroundbackward));
                break;
            default:
                textViewRouteBusStateWord.setText(SystemConstants.EMPTY_STRING);
                break;
        }
    }

    /**
     * Метод устанавливает данные "не на маршруте" отображения в представлении
     * @param busState Объект с данными для отображения
     */
    public void setBusNotFoundRouteInfo(BusState busState) {
        setTitle(SystemConstants.PHRASE_INFORMATION_TITLE);

        transitLinearLayout.setVisibility(View.GONE);
        endStationLinearLayout.setVisibility(View.GONE);
        busNotRouteLinearLayout.setVisibility(View.VISIBLE);
        dataNotFoundLinearLayout.setVisibility(View.GONE);
    }

    /**
     * Метод отображает сообщение с ошибкой
     */
    public void showErrorMessage() {
        transitLinearLayout.setVisibility(View.GONE);
        endStationLinearLayout.setVisibility(View.GONE);
        busNotRouteLinearLayout.setVisibility(View.GONE);
        dataNotFoundLinearLayout.setVisibility(View.VISIBLE);
    }

@SuppressWarnings("StatementWithEmptyBody") // Обработчик бокового меню Бойко А.А. 07,07,2019
@Override
public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.action_select_bus) {
        Toast toast =  Toast.makeText(getApplicationContext(),R.string.action_select_bus,Toast.LENGTH_LONG);
        toast.show();
        startActivity(new Intent(this, BusListActivity.class));
        return true;
    } else if (id == R.id.action_logout) {
        //new BusLocationInfoActivity.LogoutRequest().execute(activeUser.getToken(), activeBusId); // раньше переходили на логин активити
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        System.exit(0);
        return true;
    } else if (id == R.id.update) {
        Intent browserIntent = new
                Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/open?id=1gCQKDM6g1uaSoKPNeWwKpVwkXBOiP_yF"));
        startActivity(browserIntent);

    }
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
}
    /**
     * Метод сохраняет настройки интерфейса в хранилище
     */
    private void savePreferences() {
        sPref = getSharedPreferences(SystemConstants.APPLICATION_SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();

        editor.putString("busLocationActivity_textViewBeforeBus", CommonHelper.getValueOrEmptyString(textViewBeforeBus));
        editor.putString("busLocationActivity_textViewBeforeBusTime", CommonHelper.getValueOrEmptyString(textViewBeforeBusTime));
        editor.putString("busLocationActivity_textViewBeforeStation", CommonHelper.getValueOrEmptyString(textViewBeforeStation));
        editor.putString("busLocationActivity_textViewRouteBusState", CommonHelper.getValueOrEmptyString(textViewRouteBusState));
        editor.putString("busLocationActivity_textViewRouteBusStateWord", CommonHelper.getValueOrEmptyString(textViewRouteBusStateWord));
        editor.putString("busLocationActivity_textViewTransitCurrentTime", CommonHelper.getValueOrEmptyString(textViewTransitCurrentTime));
        editor.putString("busLocationActivity_textViewEndStationCurrentTime", CommonHelper.getValueOrEmptyString(textViewEndStationCurrentTime));//Текущее время

        editor.commit();
    }

    /**
     * Метод загружает настройки интерфейса из хранилища
     */
    private void loadPreferences() {
        sPref = getSharedPreferences(SystemConstants.APPLICATION_SHARED_PREFERENCES, MODE_PRIVATE);

        textViewBeforeBus.setText(sPref.getString("busLocationActivity_textViewBeforeBus", SystemConstants.EMPTY_STRING));
        textViewBeforeBusTime.setText(sPref.getString("busLocationActivity_textViewBeforeBusTime", SystemConstants.EMPTY_STRING));
/*        textViewAfterBus.setText(sPref.getString("busLocationActivity_textViewBeforeBus", SystemConstants.EMPTY_STRING));    // Сзади
        textViewAfterBusTime.setText(sPref.getString("busLocationActivity_textViewBeforeBusTime", SystemConstants.EMPTY_STRING));*/
        textViewBeforeStation.setText(sPref.getString("busLocationActivity_textViewBeforeStation", SystemConstants.EMPTY_STRING));
        textViewBeforeStationTime.setText(sPref.getString("busLocationActivity_textViewBeforeStationTime", SystemConstants.EMPTY_STRING));
        textViewRouteBusState.setText(sPref.getString("busLocationActivity_textViewRouteBusState", SystemConstants.EMPTY_STRING));
        textViewRouteBusStateWord.setText(sPref.getString("busLocationActivity_textViewRouteBusStateWord", SystemConstants.EMPTY_STRING));
        textViewTransitCurrentTime.setText(sPref.getString("busLocationActivity_textViewTransitCurrentTime", SystemConstants.EMPTY_STRING));
        textViewEndStationCurrentTime.setText(sPref.getString("busLocationActivity_textViewEndStationCurrentTime", SystemConstants.EMPTY_STRING));//Текущее время
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        savePreferences();
       // userController.closeConnection();
    }
}