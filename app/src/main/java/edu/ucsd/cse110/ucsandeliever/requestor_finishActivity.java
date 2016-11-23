package edu.ucsd.cse110.ucsandeliever;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.ucsd.cse110.ucsandeliever.utils.Const;

public class requestor_finishActivity extends Activity {
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_requestor_finish);


        done = (Button) findViewById(R.id.buttonDone);
        done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                // change the done of order to be true
                final String currUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                final DatabaseReference ordersRef = mRootRef.child("orders");

                ordersRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                        for (com.google.firebase.database.DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Order order = snapshot.getValue(Order.class);


                            if (order.getDone() == false && order.getRequestorUid().equals(currUid))
                                System.out.println("change the done to be true and requestor ready to finish");

                                order.changeDone(true);

                            Intent intent = new Intent(requestor_finishActivity.this,drawerActivity.class);
                            startActivity(intent);


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                // go to main page


            }

    });
    }




    @Override
    public void onBackPressed() {

    }


}
