package edu.ucsd.cse110.ucsandeliever;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class confirmPickingBidActivity extends AppCompatActivity {

    private String monFromStatus;
    private String timFromStatus;
    private String runFromStatus;
    private String requesterID;

    private TextView moneyShow;
    private TextView timeShow;
    private TextView runnerShow;

    String balance1 = "";
    String balance2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_picking_bid);

        // get data from last page
        Intent i = getIntent();
        Bundle data = i.getExtras();
        monFromStatus = data.getString("moneyGet");
        timFromStatus = data.getString("timeGet");
        runFromStatus = data.getString("runnerGet");

        // find the textviews
        moneyShow = (TextView) findViewById(R.id.moneyText);
        timeShow = (TextView) findViewById(R.id.timeText);
        runnerShow = (TextView) findViewById(R.id.runnerText);

        // assign values to the textviews
        moneyShow.setText(monFromStatus);
        timeShow.setText(timFromStatus);
        runnerShow.setText(runFromStatus);

        //

    }

    public void confirmSystem(View view){

        String button_text;
        button_text = ((Button) view).getText().toString();
        if(button_text.equals("Yes, choose this runner")){

            // look for runner from firebase using runFromStatus
            // change that runner's status to true
            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference usersRef = mRootRef.child("users");
            DatabaseReference runnerRef = usersRef.child(runFromStatus);
            DatabaseReference indicatorRef = runnerRef.child("runnerStatusIndicator");
            indicatorRef.setValue(true);

            //change the user's status to already pick to true
            String currUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference requesterRef = usersRef.child(currUid);
            DatabaseReference alreadyPickRef = requesterRef.child("alreadyPick");
            alreadyPickRef.setValue(true);
            requesterID = requesterRef.getKey();

            usersRef.addListenerForSingleValueEvent( new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                    balance1 = dataSnapshot.getValue(Student.class).getBalance();

                    for (com.google.firebase.database.DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Student student = snapshot.getValue(Student.class);

                        if (student.getuid().equals(runFromStatus))
                        {
                            balance1 = student.getBalance();
                        }
                        else if (student.getuid().equals(requesterID))
                        {
                            balance2 = student.getBalance();
                            int newBalanceForUser2 = Integer.parseInt(balance2) - Integer.parseInt(monFromStatus);
                            snapshot.getRef().child("balance").setValue(Integer.toString(newBalanceForUser2));
                        }

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            // to change because chat needs to get done first - Zihan
           // Intent intent = new Intent(this,Chat.class);
            //startActivity(intent);

            // now go to the requestor_contact page - he chang
            // for chatting info
            Intent k = new Intent(confirmPickingBidActivity.this, requestor_contactActivity.class);
            Bundle b = new Bundle();


            b.putString("runner", runFromStatus);
            k.putExtras(b);

            startActivity(k);

        }else if(button_text.equals("No, go back to last page")){

            Intent intent = new Intent(this,orderStatus.class);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
    }


}
