package com.example.myquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecordsActivity extends AppCompatActivity {

    private static final String TAG = "RecordsActivity";
    private TextView score;
    private Button done;

    public class RecordItem {
        String name;
        int record;
    }

    public class CustomComparator implements Comparator<RecordItem> {
        @Override
        public int compare(RecordItem o1, RecordItem o2) {
            return o2.record - o1.record;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        final LinearLayout linearLayout = findViewById(R.id.recordlist);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Recordlist");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<RecordItem> records = new ArrayList();
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    RecordItem item = new RecordItem();
                    item.name = dataSnap.getKey().replace("-",".");
                    item.record = Integer.parseInt(dataSnap.getValue(String.class));
                    records.add(item);
                }
                Collections.sort(records, new CustomComparator());
                int u=0;
                for(RecordItem i:records)
                {
                    View item_layout = getLayoutInflater().inflate(R.layout.record_layout, null);
                    TextView user = item_layout.findViewById(R.id.username);
                    TextView scores = item_layout.findViewById(R.id.score);
                    user.setText(++u +" : "+i.name);
                    scores.setText(String.valueOf(i.record));
                    linearLayout.addView(item_layout);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}