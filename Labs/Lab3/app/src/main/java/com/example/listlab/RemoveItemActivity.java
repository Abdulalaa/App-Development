package com.example.listlab;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;

import javax.inject.Inject;

@AndroidEntryPoint
public class RemoveItemActivity extends AppCompatActivity {

    @Inject
    StringList the_list;   // reference to singleton string list object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_remove_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // remove the item to the list

    public void removeItem(View view)
    {

        EditText removePosition;
        int position;

        // get the desired position of item to be deleted

        removePosition = (EditText) findViewById(R.id.edit_remove_position);
        position = Integer.parseInt(removePosition.getText().toString());

        // try to put remove item at position
        try
        {
            the_list.remove(position);

            // hide soft keyboard so snackbar is visible

            hideKeyboard();
            Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                    "Item removed from the list",
                    Snackbar.LENGTH_SHORT).show();
        }
        catch(IndexOutOfBoundsException e)
        {
            hideKeyboard();
            Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                    "Failed to remove item from the list",
                    Snackbar.LENGTH_SHORT).show();

        }

    } // end removeItem

    private void hideKeyboard()
    {
        // This method dismisses the soft keyboard.
        // Code derived from developer.android.com and
        // StackOverflow

        Context context = getCurrentFocus().getContext();

        InputMethodManager inputMethodManager =
                (InputMethodManager)
                        context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        if (inputMethodManager != null)
            inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

    } // end hideKeyboard

} // end RemoveItemActivity