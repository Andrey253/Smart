package com.geometry.smartdispatcherandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orm.SugarContext;

import api.ApiService;
import api.Client;
import common.SystemConstants;
//import controllers.UserController;
import helpers.CommonHelper;
import helpers.StringHelper;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Активность для аутентификации пользователей
 * @author DushinovSV
 * Created by house on 09.12.2017
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Максимальное количество попыток авторизации пользователя
     */
    private final int MAX_COUNT_ATTEMPS = 6;

    private TextView loginTextBox;
    private TextView passwordTextBox;
    private TextView messageView;
    private Button buttonLogin;
    private CheckBox rememberPasswordCheckbox;
    private TextView versionNameTV;

    /**
     * Объект для сохранения настроек
     */
    private SharedPreferences sPref;

    /**
     * Контроллер для работы пользователей
     */
    //private UserController userController;

    /**
     * Счетчик количества попыток авторизации пользователя
     */
    private int counterAttemps;

    /**
     * Отправляется запрос на авторизацию
     */
    private boolean isRequestSending = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SugarContext.init(this);

        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        loginTextBox = findViewById(R.id.loginTextBox);
        passwordTextBox = findViewById(R.id.passwordTextBox);
        messageView = findViewById(R.id.messageView);
        buttonLogin = findViewById(R.id.buttonLogin);
        rememberPasswordCheckbox = findViewById(R.id.rememberPasswordCheckbox);

        String versionName = "Версия "+ BuildConfig.VERSION_NAME;// версия программы
        versionNameTV = findViewById(R.id.versionNameTV);
        versionNameTV.setText(versionName);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        userController = new UserController(this, deviceId);
        userController.loadDataFromDb();*/

        resetCounterAttemps();
        loadPreferences();
        //loadPropertyPasswordToUi();
    }

    /**
     * Асинхронный запрос для получения данных с сервера по аутентификации
     */
    class
    LoginRequest extends AsyncTask<String, Void, Object[]> {
        @Override
        protected Object[] doInBackground(String... arg) {
            try {
                String login = arg[0];
                String password = arg[1];

                final ApiService api = Client.getApiService();
                Call<User> call = api.getLoginData(login, password);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {

                            isRequestSending = false;

                            if (!response.body().isEmptyUser()){

                                Intent intent = new Intent(LoginActivity.this, BusListActivity.class);
                                intent.putExtra("activeUser", new Gson().toJson(response.body()));
                                startActivity(intent);
                            }else {
                                showErrorMessage("Не удалось получить информацию о пользователе");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        System.out.println("my даннык не получены ");
                    }
                });

                return new Object[] { SystemConstants.EMPTY_STRING, "" };
            } catch(Exception ex) {
                String errorMessage = "Error send http login request";
                showErrorMessage(errorMessage);
                Log.e(SystemConstants.LOG_TAG, errorMessage + ex.getMessage(), ex);
                return new Object[] { errorMessage, null };
            }
        }
        @Override
        protected void onPostExecute(Object[] responce) {
            super.onPostExecute(responce);
        }
    }

    /**
     * Обработка клика по кнопке войти
     * @param view Представление
     */
    public void onClickLogin (View view) {
        if(!isOnline()) {
            showErrorMessage("Нет подключения к интернету");
            return;
        }
        if(CommonHelper.isNull(loginTextBox.getText()) ||
            CommonHelper.isNull(passwordTextBox.getText())) {
            showErrorMessage("Логин или пароль незаданы");
            return;
        }
        String login = loginTextBox.getText().toString();
        String password = passwordTextBox.getText().toString();
        if(StringHelper.isEmpty(login)) {
            showErrorMessage("Логин незадан");
            return;
        }
        if(StringHelper.isEmpty(password)) {
            showErrorMessage("Пароль незадан");
            return;
        }
        incCounterAttemps();
        if(isMaxCounterAttemps()) {
            executeCountDownTimer();
        } else {
            if(!isRequestSending) {
                isRequestSending = true;
                new LoginRequest().execute(login, password);
            }
        }
    }

    /**
     * Метод проверяет подключение к интернету
     * @return boolean Результат проверки
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (!CommonHelper.isNull(netInfo) && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Метод отображает сообщение с ошибкой
     * @param message Сообщение для отображения
     */
    private void showErrorMessage(String message) {
        messageView.setVisibility(View.VISIBLE);
        messageView.setTextColor(Color.RED);
        messageView.setText(message);
    }

    /**
     * Метод увеличивает счетчик попыток авторизации
     */
    private void incCounterAttemps() {
        counterAttemps += 1;
    }

    /**
     * Метод проверяет значение счетчика попыток авторизации на равенство критическому
     */
    private boolean isMaxCounterAttemps() {
        return counterAttemps == MAX_COUNT_ATTEMPS;
    }

    /**
     * Метод обнуляет счетчик попыток авторизации
     */
    private void resetCounterAttemps() {
        counterAttemps = 0;
    }

    /**
     * Метод запускает счетчик обратного отчета для кнопки с авторизацией
     */
    private void executeCountDownTimer() {
        buttonLogin.setEnabled(false);

        //Создаем таймер обратного отсчета на 20 секунд с шагом отсчета
        //в 1 секунду (задаем значения в миллисекундах):

        int intervalSeconds = 20;
        int stepSeconds = 1;
        new CountDownTimer(intervalSeconds * 1000, stepSeconds * 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int)(millisUntilFinished / 1000);
                showErrorMessage("Осталось подождать: " + seconds);
            }
            public void onFinish() {
                buttonLogin.setEnabled(true);
                resetCounterAttemps();
                showErrorMessage(SystemConstants.EMPTY_STRING);
            }
        }.start();
    }

    /**
     * Метод сохраняет настройки интерфейса в хранилище
     */
    private void savePreferences() {
        sPref = getSharedPreferences(SystemConstants.APPLICATION_SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();

        editor.putString("loginActivity_loginTextBox", CommonHelper.getValueOrEmptyString(loginTextBox));
        editor.putString("loginActivity_passwordTextBox", CommonHelper.getValueOrEmptyString(passwordTextBox));
        editor.putString("loginActivity_messageView", CommonHelper.getValueOrEmptyString(messageView));

        editor.commit();
    }

    /**
     * Метод загружает настройки интерфейса из хранилища
     */
    private void loadPreferences() {
        sPref = getSharedPreferences(SystemConstants.APPLICATION_SHARED_PREFERENCES, MODE_PRIVATE);
        System.out.println("my loginActivity_passwordTextBox = " + sPref.getString("loginActivity_passwordTextBox", SystemConstants.EMPTY_STRING));
        loginTextBox.setText(sPref.getString("loginActivity_loginTextBox", SystemConstants.EMPTY_STRING));
        passwordTextBox.setText(sPref.getString("loginActivity_passwordTextBox", SystemConstants.EMPTY_STRING));
        messageView.setText(sPref.getString("loginActivity_messageView", SystemConstants.EMPTY_STRING));
    }

    /**
     * Метод обновляет UI в зависимости от настроек по паролю пользователя в базе
     */
/*    private void loadPropertyPasswordToUi() {
        rememberPasswordCheckbox.setChecked(userController.getIsRememberPassword());
        loginTextBox.setText(CommonHelper.getValueOrEmptyString(userController.getUserLogin()));
        passwordTextBox.setText(CommonHelper.getValueOrEmptyString(userController.getUserPassword()));
    }*/

    /**
     * Метод сохраняет настройки по паролю пользователя в базу
     */
/*    private void savePropertyPasswordFromUi() {
        if(rememberPasswordCheckbox.isChecked()) {
            Log.d(SystemConstants.LOG_TAG, "***** 111111");
            userController.setIsRememberPassword(rememberPasswordCheckbox.isChecked());
            userController.saveIsRememberPassword();

            userController.setUserLogin(loginTextBox.getText().toString());
            userController.saveUserLogin();

            userController.setUserPassword(passwordTextBox.getText().toString());
            userController.saveUserPassword();
        } else {
            Log.d(SystemConstants.LOG_TAG, "***** 222222");
            userController.clearUserLogin();
            userController.clearUserPassword();
            userController.clearRememberPassword();
        }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        savePreferences();
        //userController.closeConnection();
    }
}