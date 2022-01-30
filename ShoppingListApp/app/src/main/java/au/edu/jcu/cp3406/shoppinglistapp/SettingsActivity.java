package au.edu.jcu.cp3406.shoppinglistapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {
    public static int SETTINGS_REQUEST = 1234; // Request value.
    private EditText editText; // User input.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Creates the activity.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editText = findViewById(R.id.deleteItem);
        SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);

        AppCompatDelegate.setDefaultNightMode(preferences.getInt("mode",
                AppCompatDelegate.MODE_NIGHT_UNSPECIFIED));

        Button button = findViewById(R.id.switchMode);
        button.setOnClickListener(v -> {
            switch (AppCompatDelegate.getDefaultNightMode()) {
                case AppCompatDelegate.MODE_NIGHT_YES:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
                default:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Closes the activity when back is pressed.
        super.onBackPressed();
        finish();
    }

    public void deleteAllClicked(View view) {
        // Saves data that is transferred to the main activity.
        Intent data = new Intent();
        data.putExtra("clear", true);
        setResult(RESULT_OK, data);
    }

    public void unCheckClicked(View view) {
        // Saves data that is transferred to the main activity.
        Intent data = new Intent();
        data.putExtra("unCheck", true);
        setResult(RESULT_OK, data);
    }

    public void deleteChecked(View view) {
        // Saves data that is transferred to the main activity.
        Intent data = new Intent();
        data.putExtra("isChecked", true);
        setResult(RESULT_OK, data);
    }

    public void deleteCheckBox(View view) {
        // Handles user input and transfers data to the main activity.
        String shoppingItem = editText.getText().toString();
        if (!shoppingItem.isEmpty()) {
            Intent data = new Intent();
            data.putExtra("shoppingItem", shoppingItem);
            setResult(RESULT_OK, data);
            editText.setText("");
        }
        else {
            Toast.makeText(this, "Cannot be blank", Toast.LENGTH_SHORT).show();
        }
    }

    public void backClicked(View view) {
        // Closes the activity.
        finish();
    }
}