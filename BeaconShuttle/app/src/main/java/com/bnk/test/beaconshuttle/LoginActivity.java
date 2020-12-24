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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
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


    public void successSubmit() {
        String alertTitle = "안내메시지";
        String buttonMessage = "정상적으로 입력되었습니다.";
        String buttonYes = "확인";

        new AlertDialog.Builder(LoginActivity.this)
                .setTitle(alertTitle)
                .setMessage(buttonMessage)
                .setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void coronaClick(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.popup_corona, null);
        builder.setView(layout);
        AlertDialog Ad = builder.create();
        Ad.show();

        Button submitBtn = (Button)layout.findViewById(R.id.coronaSubmit);
        submitBtn.setEnabled(false);
        RadioButton rb1_1 = (RadioButton)layout.findViewById(R.id.cronaq1yes);
        RadioButton rb1_2 = (RadioButton)layout.findViewById(R.id.cronaq1no);
        RadioButton rb2_1 = (RadioButton)layout.findViewById(R.id.cronaq2yes);
        RadioButton rb2_2 = (RadioButton)layout.findViewById(R.id.cronaq2no);
        RadioButton rb3_1 = (RadioButton)layout.findViewById(R.id.cronaq3yes);
        RadioButton rb3_2 = (RadioButton)layout.findViewById(R.id.cronaq3no);
        RadioButton rb4_1 = (RadioButton)layout.findViewById(R.id.cronaq4yes);
        RadioButton rb4_2 = (RadioButton)layout.findViewById(R.id.cronaq4no);
        RadioButton rb5_1 = (RadioButton)layout.findViewById(R.id.cronaq5yes);
        RadioButton rb5_2 = (RadioButton)layout.findViewById(R.id.cronaq5no);
        RadioButton rb6_1 = (RadioButton)layout.findViewById(R.id.cronaq6yes);
        RadioButton rb6_2 = (RadioButton)layout.findViewById(R.id.cronaq6no);
        RadioButton rb7_1 = (RadioButton)layout.findViewById(R.id.cronaq7yes);
        RadioButton rb7_2 = (RadioButton)layout.findViewById(R.id.cronaq7no);

        rb1_1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                   (rb2_1.isChecked() || rb2_2.isChecked()) &&
                   (rb3_1.isChecked() || rb3_2.isChecked()) &&
                   (rb4_1.isChecked() || rb4_2.isChecked()) &&
                   (rb5_1.isChecked() || rb5_2.isChecked()) &&
                   (rb6_1.isChecked() || rb6_2.isChecked()) &&
                   (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                  submitBtn.setEnabled(true);
                }
            }
        });

        rb1_2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }
            }
        });
        rb2_1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });
        rb2_2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });

        rb3_1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });
        rb3_2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });

        rb4_1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });
        rb4_2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });

        rb5_1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });
        rb5_2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });

        rb6_1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });
        rb6_2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });

        rb7_1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });
        rb7_2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Ad.dismiss();
               successSubmit();
            }
        });


    }



}
