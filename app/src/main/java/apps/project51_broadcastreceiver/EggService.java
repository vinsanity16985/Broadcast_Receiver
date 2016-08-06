package apps.project51_broadcastreceiver;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.NotificationCompat;

/**
 * Created by vinsa_000 on 4/11/2016.
 */
public class EggService extends IntentService {
    SharedPreferences myPrefs;
    SharedPreferences.Editor editor;
    NotificationCompat.Builder builder;
    String num_eggs;

    public EggService(){
        super("EggService");
    }

    @Override
    /**
     * Receives an intent from the broadcast receiver
     * Updates the value of the number of eggs and notifies the user of the change in eggs
     * @param intent: The intent passed from MyBroadcastReceiver
     */
    protected void onHandleIntent(Intent intent) {
        //Initialize necessary variables
        this.myPrefs = getSharedPreferences(getString(R.string.my_prefs), Context.MODE_PRIVATE);
        this.editor = this.myPrefs.edit();
        num_eggs = getString(R.string.num_eggs);
        String add1 = getString(R.string.add_egg);
        String add2 = getString(R.string.add_eggs);
        String sub1 = getString(R.string.subtract_egg);
        String noteTitle = getString(R.string.notification_title);
        String noteText1 = getString(R.string.notification_text_add1);
        String noteText2 = getString(R.string.notification_text_add2);
        String noteText3 = getString(R.string.notification_text_sub1);

        if(intent != null){
            if(intent.hasExtra(add1)){
                builder = buildNotification(R.drawable.eggs, noteTitle, noteText1);
                editor.putInt(num_eggs, addEggs(intent.getExtras().getInt(add1), myPrefs.getInt(num_eggs, Constants.DEFAULT_VALUE)));
            }
            else if(intent.hasExtra(add2)){
                builder = buildNotification(R.drawable.eggs, noteTitle, noteText2);
                editor.putInt(num_eggs, addEggs(intent.getExtras().getInt(add2), myPrefs.getInt(num_eggs, Constants.DEFAULT_VALUE)));
            }
            else if(intent.hasExtra(sub1)){
                builder = buildNotification(R.drawable.eggs, noteTitle, noteText3);
                editor.putInt(num_eggs, addEggs(intent.getExtras().getInt(sub1), myPrefs.getInt(num_eggs, Constants.DEFAULT_VALUE)));
            }
            else{
                builder = buildNotification(R.drawable.eggs, noteTitle, doMakeBreakfast());
            }
            editor.commit();

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(Constants.NOTIFICATION_ID, builder.build());
        }
    }

    /**
     * Will increment the number of eggs by num_to_add
     * @param num_to_add: An integer value to increment the number of eggs
     * @param eggs: The current number of eggs that we have
     * @return int: The sum of num_to_add and eggs
     */
    private int addEggs(int num_to_add, int eggs){
        eggs += num_to_add;
        return eggs;
    }

    /**
     * Will determine which String to place in the text of the notification displayed to the user
     * @return String: String to place in the text of the notification displayed to the user
     */
    private String doMakeBreakfast(){
        String text;
        int eggs = myPrefs.getInt(num_eggs, Constants.DEFAULT_VALUE);
        if(eggs != Constants.DEFAULT_VALUE){
            //If 6 or more eggs then we have omelets
            if(eggs >= 6){
                eggs -= 6;
                editor.putInt(num_eggs, eggs);
                editor.commit();
                text = getString(R.string.omelets_beginning) + eggs + getString(R.string.omelets_end);
            }
            //Otherwise we are having gruel
            else{
                text = getString(R.string.gruel_beginning) + eggs + getString(R.string.gruel_end);
            }
            return text;
        }
        return getString(R.string.unknown_eggs);
    }

    /**
     * Will create a Notification.Builder with the given icon, title and text
     * @param icon: The icon displayed in the notification
     * @param title: The title displayed in the notification
     * @param text: The text displayed in the notification
     * @return NotificationCompat.Builder: The notification builder that will create the notification to display to the user
     */
    private NotificationCompat.Builder buildNotification(int icon, String title, String text){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(icon);
        builder.setContentTitle(title);
        builder.setContentText(text);
        return builder;
    }
}
