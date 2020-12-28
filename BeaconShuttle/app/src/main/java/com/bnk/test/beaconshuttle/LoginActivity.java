package com.bnk.test.beaconshuttle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText loginId, loginPassword;
    CheckBox autoLogin, saveId;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Boolean loginChecked;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginId = (EditText) findViewById(R.id.loginId);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        autoLogin = (CheckBox) findViewById(R.id.autoLogin);
        saveId = (CheckBox) findViewById(R.id.saveId);
        pref = getSharedPreferences("login_info", 0);
        editor = pref.edit();
        loginId.setText(pref.getString("id", ""));
        saveId.setChecked(pref.getBoolean("saveId", false));

        // [START SPINNER]
        final String[] comGroups = {
                "그룹사선택",
                "BNK금융지주",
                "BNK부산은행",
                "BNK경남은행",
                "BNK캐피탈",
                "BNK투자증권",
                "BNK저축은행",
                "BNK자산운용",
                "BNK신용정보",
                "BNK시스템",
                "BNK벤처투자",
                "기타"};

        Spinner spinner = (Spinner) findViewById(R.id.comGroupSpinner);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, comGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setFocusable(true);
        spinner.setFocusableInTouchMode(true);
        spinner.requestFocus();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "그룹사 : " + comGroups[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // [END SPINNER]

        loginChecked = pref.getBoolean("autoLogin", false);
        if(!loginChecked) {
            loginId.setText(pref.getString("id", ""));
            autoLogin.setChecked(false);
        } else {
            String id = loginId.getText().toString();
            String password = loginPassword.getText().toString();
            Boolean validation = loginValidation(id, password);

            if(validation){
                Toast.makeText(LoginActivity.this, "자동 로그인중", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
            }
        }

        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    loginChecked = true;
                } else {
                    // if unChecked, removeAll
                    loginChecked = false;
                    editor.clear();
                    editor.apply();
                }
            }
        });

    }

    public void loginClick(View v){
        Boolean check = loginValCheck(loginId.getText().toString(), loginPassword.getText().toString());
        if(check) {
            if(autoLogin.isChecked()){
                editor.putString("id", loginId.getText().toString());
                editor.putString("password", loginPassword.getText().toString());
                editor.putBoolean("autoLogin", true);
                editor.apply();
            } else {
                editor.clear();
                editor.apply();
            }

            if(saveId.isChecked()) {
                editor.putString("id", loginId.getText().toString());
                editor.putBoolean("saveId", true);
                editor.apply();

            }

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(v.getContext(), "아이디 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean loginValCheck(String id, String password) {
        if(id.equals("") || password.equals("")){
            return false;
        }
        return true;
    }

    private boolean loginValidation(String id, String password) {
        if(pref.getString("id", "").equals(id) && pref.getString("pw", "").equals(password)){
            return true;
        } else if (pref.getString("id", "").equals(null)){
            Toast.makeText(LoginActivity.this, "Please Sing in first", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return false;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                String alertTitle = "안내메시지";
                String buttonMessage = "로그아웃 하시겠습니까?";
                String buttonYes = "확인";
                String buttonNo = "취소";

                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle(alertTitle)
                        .setMessage(buttonMessage)
                        .setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                moveTaskToBack(true);
                                finish();
                            }
                        })
                        .setNegativeButton(buttonNo, null)
                        .show();
                break;
        }
        return true;
    }






}
