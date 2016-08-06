package apps.project51_broadcastreceiver;

//!!!!!!!!!!!!!!!!!!!!!!!!!
//Vincent Noca & Josh Cohen
//!!!!!!!!!!!!!!!!!!!!!!!!!

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    SharedPreferences myPrefs;
    SharedPreferences.Editor editor;
    Button subtractButton;
    int num_eggs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialize necessary variables
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.content_main, null);
        subtractButton = (Button)layout.findViewById(R.id.button3);
        myPrefs = getSharedPreferences(getString(R.string.my_prefs), Context.MODE_PRIVATE);
        editor = myPrefs.edit();

        //Set the layout and the toolbar
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Add an integer num_eggs for the number of eggs that we have to SharedPreferences
        if(myPrefs.getInt(getString(R.string.num_eggs), Constants.DEFAULT_VALUE) != Constants.DEFAULT_VALUE){
            editor.putInt(getString(R.string.num_eggs), 0);
            editor.commit();
        }

        //Can't subtract if there are no eggs to subtract
        num_eggs = myPrefs.getInt(getString(R.string.num_eggs), Constants.DEFAULT_VALUE);
        noEggs(num_eggs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * OnClick handler for each button
     * @param view: View to be handled
     */
    public void broadcastIntent(View view){
        //Get the number of eggs we have and get key strings for the extras
        num_eggs = myPrefs.getInt(getString(R.string.num_eggs), Constants.DEFAULT_VALUE);
        String add1 = getString(R.string.add_egg);
        String add2 = getString(R.string.add_eggs);
        String sub1 = getString(R.string.subtract_egg);
        String mb = getString(R.string.make_breakfast);

        //Create an intent to be sent to MyBroadcastReceiver
        Intent myBroadcastIntent = new Intent();
        myBroadcastIntent.setAction("EGG_INTENT");

        //Depending on which button is clicked an appropriate extra will be added to the intent
        switch (view.getId()){
            case R.id.button1:
                subtractButton.setClickable(true);
                myBroadcastIntent.putExtra(add1, Constants.ADD_ONE_EGG);
                break;
            case R.id.button2:
                subtractButton.setClickable(true);
                myBroadcastIntent.putExtra(add2, Constants.ADD_TWO_EGGS);
                break;
            case R.id.button3:
                noEggs(num_eggs - 1);
                myBroadcastIntent.putExtra(sub1, Constants.SUBTRACT_ONE_EGG);
                break;
            case R.id.button4:
                noEggs(num_eggs - 6);
                myBroadcastIntent.putExtra(mb, Constants.MAKE_BREAKFAST);
                break;
        }
        //Send the intent
        sendBroadcast(myBroadcastIntent);
    }

    /**
     * Will check the value of num_eggs
     * If num_eggs == 0 then take away the user's ability to subtract an egg
     * @param num_eggs: The number of eggs we currently have
     */
    private void noEggs(int num_eggs){
        if(num_eggs == 0){
            subtractButton.setClickable(false);
        }
    }
}
