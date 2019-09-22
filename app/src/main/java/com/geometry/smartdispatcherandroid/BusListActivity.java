package com.geometry.smartdispatcherandroid;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.orm.SugarContext;

import java.util.List;

import adapters.BusInfoItemAdapter;
//import controllers.UserController;
import model.Bus;
import model.User;

/**
 * Активность для выбора активного автобуса
 * @author DushinovSV
 * Created by house on 10.12.2017
 */
public class BusListActivity extends AppCompatActivity {
    private ListView busListView;
    private String activeBus;

    //TODO может нужно будет при инициализации формы устанавливать текущий выбранный автобус активным

    /**
     * Адаптер для отображения автобусов в списке
     */
    private BusInfoItemAdapter busInfoItemAdapter;

    /**
     * Список автобусов для отображения
     */
    private List<Bus> buses;

    /**
     * Выбранный в списке автобус
     */
    private Bus selectedBus;

    /**
     * Контроллер для работы пользователей
     */
    //private UserController userController;

    /**
     * Активный водитель
     */
    private User activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);

        SugarContext.init(this);

        User user = new Gson().fromJson(getIntent().getStringExtra("activeUser"), User.class);

        //TODO на весь экран, код может пригодится
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        busListView = findViewById(R.id.busListView); // Лист автобусов активного юзера

        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
/*        userController = new UserController(this, deviceId);
        activeUser = userController.getActiveUser();*/
        //User activeuser = User.findById(User.class,1); // Чтение активного юзера из базы
        //System.out.println("my activeuser "+activeuser);

        buses = user.getBuses(); // Получение от активного юзера лист автобусов
        //buses = activeUser.getBuses();
        busInfoItemAdapter = new BusInfoItemAdapter(this, buses);
        busListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        busListView.setAdapter(busInfoItemAdapter);
        setDefaultSelectedBusNumber();

        busListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                selectedBus = buses.get(position);
            }
        });
    }

    /**
     * Метод устанавливает выбранный автобус по умолчанию
     */
    private void setDefaultSelectedBusNumber() {
        if(!buses.isEmpty()) {
            int selectedPosition = 0;
            selectedBus = buses.get(selectedPosition);
            busListView.setItemChecked(selectedPosition, true);
        }
    }

    /**
     * Обработка клика по кнопке "Выбрать"
     * @param view Представление
     */
    public void onButtonApply (View view) {

        activeBus = new Gson().toJson(selectedBus);

        Intent intent = new Intent(BusListActivity.this, BusLocationInfoActivity.class);
        intent.putExtra("activeBus", activeBus);
        intent.putExtra("activeUser", getIntent().getStringExtra("activeUser"));
        startActivity(intent);

/*        userController.setActiveBusIdFromNumber(selectedBus.getBusState());
        userController.saveActiveBusIdToDb();
        startActivity(new Intent(this, BusLocationInfoActivity.class));*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // userController.closeConnection();
    }
}