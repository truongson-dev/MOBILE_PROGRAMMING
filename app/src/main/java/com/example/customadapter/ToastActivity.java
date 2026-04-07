package com.example.customadapter;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class ToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);

        Button btnSimple = findViewById(R.id.btn_simple_toast);
        Button btnPosition = findViewById(R.id.btn_position_toast);
        Button btnCustom = findViewById(R.id.btn_custom_toast);
        Button btnSnackbar = findViewById(R.id.btn_snackbar);

        // 1. Simple Toast
        btnSimple.setOnClickListener(v -> {
            Toast.makeText(ToastActivity.this, "Data has been saved successfully!", Toast.LENGTH_SHORT).show();
        });

        // 2. Toast Position
        btnPosition.setOnClickListener(v -> {
            Toast toast = Toast.makeText(ToastActivity.this, "This is a Top Center Toast!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
        });

        // 3. Custom Toast
        btnCustom.setOnClickListener(v -> {
            showCustomToast();
        });

        // 4. Snackbar
        btnSnackbar.setOnClickListener(v -> {
            Snackbar snackbar = Snackbar.make(v, "Message deleted", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", view -> {
                Toast.makeText(ToastActivity.this, "Undo clicked!", Toast.LENGTH_SHORT).show();
            });
            snackbar.show();
        });
    }

    private void showCustomToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_layout, (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = layout.findViewById(R.id.text_custom);
        text.setText("Đây là Toast tùy biến!");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
