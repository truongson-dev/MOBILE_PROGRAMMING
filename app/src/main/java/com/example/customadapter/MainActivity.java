package com.example.customadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button buttonOpenDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.buttonOpenDialog = (Button) this.findViewById(R.id.button_openDialog);

        this.buttonOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOpenDialogClicked();
            }
        });
    }

    private void buttonOpenDialogClicked()  {
        CustomDialog.FullNameListener listener = new CustomDialog.FullNameListener() {
            @Override
            public void fullNameEntered(String fullName) {
                Toast.makeText(MainActivity.this, "Full name: " + fullName, Toast.LENGTH_LONG).show();
            }
        };
        final CustomDialog dialog = new CustomDialog(this, listener);

        dialog.show();
    }
}
