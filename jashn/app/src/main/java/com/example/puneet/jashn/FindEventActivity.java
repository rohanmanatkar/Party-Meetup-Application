package com.example.puneet.jashn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class FindEventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private EditText Location;
    private TextView distance;
    private SeekBar distSeekBar;
    Spinner selectGuests;
    String guests;
    private TextView entryFee;
    private SeekBar feeSeekBar;
    Spinner sortBy;
    String sort;
    Spinner selectCategory;
    String category;
    private Button Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_event);

        Location = (EditText) findViewById(R.id.editTextLocation);
        Search = (Button) findViewById(R.id.findEventSearch);
        SeekBar();
        selectGuests = findViewById(R.id.guestSpin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.guests_number, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectGuests.setAdapter(adapter);
        selectGuests.setOnItemSelectedListener(this);
        /*
        selectCategory = findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.event_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCategory.setAdapter(adapter);
        selectCategory.setOnItemSelectedListener(this);
        */
        sortBy = findViewById(R.id.sortBySpin);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.sort_by, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBy.setAdapter(adapter2);
        sortBy.setOnItemSelectedListener(this);

        selectCategory = findViewById(R.id.categorySpin);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.category_sort, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCategory.setAdapter(adapter3);
        selectCategory.setOnItemSelectedListener(this);
    }

    public void SeekBar() {
        distSeekBar = (SeekBar) findViewById(R.id.seekBar);
        distance = (TextView) findViewById(R.id.findEventDistance);
        feeSeekBar = (SeekBar) findViewById(R.id.seekBarFee);
        entryFee = (TextView) findViewById(R.id.entryFeeValue);

        distSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress_value = progress;
                distance.setText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                distance.setText(progress_value);
            }
        });

        feeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress_value = progress;
                entryFee.setText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                entryFee.setText(progress_value);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        guests = parent.getItemAtPosition(position).toString();
        sort = parent.getItemAtPosition(position).toString();
        category = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
