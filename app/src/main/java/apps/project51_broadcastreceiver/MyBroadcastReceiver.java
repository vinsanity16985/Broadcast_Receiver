package apps.project51_broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by vinsa_000 on 4/11/2016.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    /**
     * This method will receive the intent passed by broadcastIntent()
     * It will relay this intent to EggService for necessary calculations
     * @param context: The context from which the intent was passed from
     * @param intent:  The intent passed to this class
     */
    public void onReceive(Context context, Intent intent) {
        //Create an intent to be sent to EggService and key strings for extras
        Intent serviceIntent = new Intent(context, EggService.class);
        String add1 = context.getString(R.string.add_egg);
        String add2 = context.getString(R.string.add_eggs);
        String sub1 = context.getString(R.string.subtract_egg);
        String mb = context.getString(R.string.make_breakfast);

        //Determines which extra was passed through the intent
        //Relays that extra to the EggService Intent
        if(intent.hasExtra(add1)){
            serviceIntent.putExtra(add1, intent.getExtras().getInt(add1));
        }
        else if(intent.hasExtra(add2)){
            serviceIntent.putExtra(add2, intent.getExtras().getInt(add2));
        }
        else if(intent.hasExtra(sub1)){
            serviceIntent.putExtra(sub1, intent.getExtras().getInt(sub1));
        }
        else{
            serviceIntent.putExtra(mb, intent.getExtras().getInt(mb));
        }

        //Sends the intent to EggService
        context.startService(serviceIntent);
    }
}
