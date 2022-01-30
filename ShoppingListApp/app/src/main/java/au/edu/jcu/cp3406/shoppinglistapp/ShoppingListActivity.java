package au.edu.jcu.cp3406.shoppinglistapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class ShoppingListActivity extends AppCompatActivity {
    private LinearLayout.LayoutParams params; // Holds parameter values.
    private LinearLayout linearLayout; // Container to hold shopping list items.
    private EditText editText; // User input.
    private SharedPreferences preferences; // Handles dark and light mode.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Creates the activity.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.list);
        editText = findViewById(R.id.shoppingItem);
        preferences = getSharedPreferences("config", MODE_PRIVATE);

        AppCompatDelegate.setDefaultNightMode(preferences.getInt("mode",
                AppCompatDelegate.MODE_NIGHT_UNSPECIFIED));
    }

    @Override
    protected void onDestroy() {
        // Saves preferences before closing the app.
        preferences.edit().putInt("mode", AppCompatDelegate.getDefaultNightMode()).apply();
        super.onDestroy();
    }


    public void addClicked(View view) {
        // Handles user input before adding a checkbox.
        String shoppingItem = editText.getText().toString();
        if (!shoppingItem.isEmpty()) {
            addCheckBox(shoppingItem);
        }
        else {
            Toast.makeText(this, "Cannot be blank", Toast.LENGTH_SHORT).show();
        }
    }

    private void addCheckBox(String shoppingItem) {
        // Adds a checkbox into the shopping list.
        setParameters();
        CheckBox checkBox = createCheckBox(shoppingItem);

        linearLayout.addView(checkBox, params);
        editText.setText("");
    }

    private void setParameters() {
        // Sets parameters.
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
    }

    private CheckBox createCheckBox(String shoppingItem) {
        // Generates a checkbox sets its values.
        CheckBox checkBox = new CheckBox(ShoppingListActivity.this);
        checkBox.setId(R.id.listedItem);
        checkBox.setText(shoppingItem);
        checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        return checkBox;
    }

    public void settingsClicked(View view) {
        // Opens the Settings page.
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, SettingsActivity.SETTINGS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Retrieves data from Settings.
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SettingsActivity.SETTINGS_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                String shoppingItem = data.getStringExtra("shoppingItem");
                boolean isChecked = data.getBooleanExtra("isChecked", false);
                boolean listCleared = data.getBooleanExtra("clear", false);
                boolean unCheck = data.getBooleanExtra("unCheck", false);

                removeCheckBox(shoppingItem);

                if (isChecked)
                    deleteCheckedCheckBoxes();

                if (listCleared)
                    deleteAllCheckBoxes();

                if (unCheck)
                    unCheckAllCheckBoxes();
            }
        }
    }

    private void removeCheckBox(String shoppingItem) {
        // Removes the specified shopping item.
        for(int i = 0; i < linearLayout.getChildCount(); i++) {
            if (((CheckBox) linearLayout.getChildAt(i)).getText().toString().equals(shoppingItem))
                linearLayout.removeViewAt(i);
        }
    }

    public void deleteCheckedCheckBoxes() {
        // Deletes checked checkboxes.
        for(int i = 0; i < linearLayout.getChildCount(); i++) {
            if (((CheckBox) linearLayout.getChildAt(i)).isChecked())
                linearLayout.removeViewAt(i);
        }
    }

    public void deleteAllCheckBoxes() {
        // Deletes all checkboxes.
        linearLayout.removeAllViews();
    }

    public void unCheckAllCheckBoxes() {
        // Uncheck all checkboxes.
        for(int i = 0; i < linearLayout.getChildCount(); i++)
            ((CheckBox)linearLayout.getChildAt(i)).setChecked(false);
    }
}