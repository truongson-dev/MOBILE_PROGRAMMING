package com.phuphuc.cuocduakythu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView txtDiem;
    ImageButton ibtnPlay;
    CheckBox cbOne, cbTwo, cbThree;
    SeekBar skOne, skTwo, skThree;
    int soDiem = 100;
    
    // Thêm danh sách để theo dõi thứ hạng
    List<Integer> ranking = new ArrayList<>();
    boolean isOneFinished, isTwoFinished, isThreeFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        
        skOne.setEnabled(false);
        skTwo.setEnabled(false);
        skThree.setEnabled(false);
        
        txtDiem.setText(String.valueOf(soDiem));

        CountDownTimer timer = new CountDownTimer(60000, 300) {
            @Override
            public void onTick(long l) {
                Random random = new Random();
                int step = 5;

                // Nếu chưa về đích thì tiếp tục chạy
                if (skOne.getProgress() < skOne.getMax()) {
                    skOne.setProgress(skOne.getProgress() + random.nextInt(step));
                } else if (!isOneFinished) {
                    isOneFinished = true;
                    ranking.add(1);
                }

                if (skTwo.getProgress() < skTwo.getMax()) {
                    skTwo.setProgress(skTwo.getProgress() + random.nextInt(step));
                } else if (!isTwoFinished) {
                    isTwoFinished = true;
                    ranking.add(2);
                }

                if (skThree.getProgress() < skThree.getMax()) {
                    skThree.setProgress(skThree.getProgress() + random.nextInt(step));
                } else if (!isThreeFinished) {
                    isThreeFinished = true;
                    ranking.add(3);
                }

                // Khi cả 3 đều đã về đích
                if (isOneFinished && isTwoFinished && isThreeFinished) {
                    this.cancel();
                    handleGameOver();
                }
            }

            @Override
            public void onFinish() {}
        };

        ibtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (soDiem < 10) {
                    Toast.makeText(MainActivity.this, "Bạn đã hết điểm để đặt cược!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cbOne.isChecked() || cbTwo.isChecked() || cbThree.isChecked()) {
                    resetRace();
                    ibtnPlay.setVisibility(View.INVISIBLE);
                    setEnableCheckboxes(false);
                    timer.start();
                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng đặt cược trước khi chơi!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Chỉ cho phép chọn 1 checkbox
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (buttonView.getId() == R.id.checkboxOne) {
                        cbTwo.setChecked(false);
                        cbThree.setChecked(false);
                    } else if (buttonView.getId() == R.id.checkboxTwo) {
                        cbOne.setChecked(false);
                        cbThree.setChecked(false);
                    } else if (buttonView.getId() == R.id.checkboxThree) {
                        cbOne.setChecked(false);
                        cbTwo.setChecked(false);
                    }
                }
            }
        };
        cbOne.setOnCheckedChangeListener(listener);
        cbTwo.setOnCheckedChangeListener(listener);
        cbThree.setOnCheckedChangeListener(listener);
    }

    private void resetRace() {
        skOne.setProgress(0);
        skTwo.setProgress(0);
        skThree.setProgress(0);
        isOneFinished = false;
        isTwoFinished = false;
        isThreeFinished = false;
        ranking.clear();
    }

    private void handleGameOver() {
        ibtnPlay.setVisibility(View.VISIBLE);
        setEnableCheckboxes(true);

        int winner = ranking.get(0);
        int second = ranking.get(1);
        int third = ranking.get(2);

        String resultMsg = "Hạng 1: Con số " + winner + "\nHạng 2: Con số " + second + "\nHạng 3: Con số " + third;
        
        int betValue = 0;
        if (cbOne.isChecked()) betValue = 1;
        if (cbTwo.isChecked()) betValue = 2;
        if (cbThree.isChecked()) betValue = 3;

        if (betValue == winner) {
            soDiem += 20;
            Toast.makeText(this, "Chúc mừng! Bạn đoán trúng hạng NHẤT (+20đ)\n" + resultMsg, Toast.LENGTH_LONG).show();
        } else if (betValue == second) {
            soDiem += 5;
            Toast.makeText(this, "Khá lắm! Bạn đoán trúng hạng NHÌ (+5đ)\n" + resultMsg, Toast.LENGTH_LONG).show();
        } else {
            soDiem -= 10;
            Toast.makeText(this, "Tiếc quá! Bạn đoán sai rồi (-10đ)\n" + resultMsg, Toast.LENGTH_LONG).show();
        }

        txtDiem.setText(String.valueOf(soDiem));
    }

    private void setEnableCheckboxes(boolean b) {
        cbOne.setEnabled(b);
        cbTwo.setEnabled(b);
        cbThree.setEnabled(b);
    }

    private void AnhXa() {
        txtDiem = findViewById(R.id.textviewDiemSo);
        ibtnPlay = findViewById(R.id.buttonPlay);
        cbOne = findViewById(R.id.checkboxOne);
        cbTwo = findViewById(R.id.checkboxTwo);
        cbThree = findViewById(R.id.checkboxThree);
        skOne = findViewById(R.id.seekbarOne);
        skTwo = findViewById(R.id.seekbarTwo);
        skThree = findViewById(R.id.seekbarThree);
    }
}