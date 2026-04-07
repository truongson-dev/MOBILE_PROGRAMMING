package com.example.customadapter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AlertDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);

        Button btnSimple = findViewById(R.id.btn_alert_simple);
        Button btnList = findViewById(R.id.btn_alert_list);
        Button btnSingle = findViewById(R.id.btn_alert_single_choice);
        Button btnMulti = findViewById(R.id.btn_alert_multi_choice);
        Button btnCustomTitle = findViewById(R.id.btn_alert_custom_title);

        btnSimple.setOnClickListener(v -> showSimpleDialog());
        btnList.setOnClickListener(v -> showListDialog());
        btnSingle.setOnClickListener(v -> showSingleChoiceDialog());
        btnMulti.setOnClickListener(v -> showMultiChoiceDialog());
        btnCustomTitle.setOnClickListener(v -> showCustomTitleDialog());
    }

    private void showSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận")
                .setMessage("Bạn có muốn thoát ứng dụng không?")
                .setCancelable(false)
                .setPositiveButton("Có", (dialog, id) -> finish())
                .setNegativeButton("Không", (dialog, id) -> dialog.cancel())
                .setNeutralButton("Nhắc sau", (dialog, id) -> Toast.makeText(this, "Sẽ nhắc lại sau", Toast.LENGTH_SHORT).show());
        builder.create().show();
    }

    private void showListDialog() {
        String[] animals = {"Ngựa", "Bò", "Lạc đà", "Cừu", "Dê"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn một con vật")
                .setItems(animals, (dialog, which) -> {
                    Toast.makeText(this, "Bạn chọn: " + animals[which], Toast.LENGTH_SHORT).show();
                });
        builder.create().show();
    }

    private void showSingleChoiceDialog() {
        String[] animals = {"Ngựa", "Bò", "Lạc đà", "Cừu", "Dê"};
        final int[] selected = {0};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn một con vật")
                .setSingleChoiceItems(animals, 0, (dialog, which) -> selected[0] = which)
                .setPositiveButton("OK", (dialog, id) -> {
                    Toast.makeText(this, "Đã chọn: " + animals[selected[0]], Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }

    private void showMultiChoiceDialog() {
        String[] animals = {"Ngựa", "Bò", "Lạc đà", "Cừu", "Dê"};
        boolean[] checkedItems = {false, false, false, false, false};
        ArrayList<Integer> mSelectedItems = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn các con vật")
                .setMultiChoiceItems(animals, checkedItems, (dialog, which, isChecked) -> {
                    if (isChecked) mSelectedItems.add(which);
                    else mSelectedItems.remove(Integer.valueOf(which));
                })
                .setPositiveButton("OK", (dialog, id) -> {
                    StringBuilder result = new StringBuilder("Đã chọn: ");
                    for (int i : mSelectedItems) {
                        result.append(animals[i]).append(", ");
                    }
                    Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }

    private void showCustomTitleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View titleView = inflater.inflate(R.layout.layout_custom_title, null);
        
        builder.setCustomTitle(titleView)
                .setMessage("Đây là AlertDialog với vùng tiêu đề tùy biến.")
                .setPositiveButton("Đóng", (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }
}
