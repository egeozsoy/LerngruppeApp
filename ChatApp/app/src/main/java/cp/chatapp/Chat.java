package cp.chatapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.Calendar;

/*alten Chatverlauf nicht dauerhaft speichern -> Beispielapp?? also schon dauerhaft, aber nicht als Bild im Layout...
chat bei beenden der App speichern
senden und empfangen
 */


public class Chat extends AppCompatActivity{

    public static final String KEY = "Text";
    public static final String KEYD = "TextDate";
    public static final String KEYIn = "TextIn";
    public static final String KEYInD = "TextInDate";

    private EditText text;
    private LinearLayout textfield;
    private ScrollView scroll;
    FragmentManager fManager;
    Fragment frag;

    DBHandler dbHandler;
    fragmentShared share = new fragmentShared();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // Get a fragment manager
        fManager = getFragmentManager();
        frag = fManager.findFragmentById(R.id.textfield);

        text = (EditText) findViewById(R.id.text);
        textfield = (LinearLayout) findViewById(R.id.textfield);
        scroll = (ScrollView) findViewById(R.id.scroll);
        dbHandler = new DBHandler(this, null, null, 1);

        //alte Nachrichten
        Cursor c = dbHandler.getCursor();
        c.moveToFirst();
        while(!c.isAfterLast()){
            Log.i("test", "mess " + c.getString(c.getColumnIndex("message")));
            if (c.getInt(c.getColumnIndex("sender")) == 0){
                makeFrag(KEY, KEYD, c.getString(c.getColumnIndex("message")), c.getString(c.getColumnIndex("date")), 0);
            }
            else{
                makeFrag(KEYIn, KEYInD, c.getString(c.getColumnIndex("message")), c.getString(c.getColumnIndex("date")), 1);
            }
            c.moveToNext();
        }

        //scroll down
        scroll.postDelayed(new Runnable() { //funktioniert nicht immer...
            public void run() {
                scroll.scrollTo(0,textfield.getBottom());
            }
        }, 300);
    }

    public void send(View view) { //eigenen Text abschicken
        if (text.getText().length() > 0) {
            String textS = text.getText().toString();
            String date = date();
            makeFrag(KEY, KEYD, textS, date, 0);
            scroll.postDelayed(new Runnable() { //funktioniert nicht immer...
                public void run() {
                    scroll.scrollTo(0,textfield.getBottom());
                }
            }, 300);

            dbHandler.addMessage(new Message(date, textS, 0));
            Log.i("test", "DATABASE: " + dbHandler.dbToString());
            text.setText("");
            textIn("test " + textS);
        }
    }

    public void textIn(String text){ //benutzen, wenn eingangstext implementiert
        String date = date();
        makeFrag(KEYIn, KEYInD, text, date, 1);
        dbHandler.addMessage(new Message(date, text, 1));
    }

    public void makeFrag(String KEY, String KEYDATE, String input, String date, int sender){
        Bundle bundle = new Bundle();
        bundle.putString(KEY, input);
        bundle.putString(KEYDATE, date);
        if(sender == 0){
            frag = new fragment_myText();
        }
        else{
            frag = new fragment_yourText();
        }
        frag.setArguments(bundle);
        fManager.beginTransaction().add(R.id.textfield, frag).commit();
    }

    public void resetDB(){
        Log.i("test","reset");
        this.deleteDatabase(dbHandler.getDatabaseName());
    }

    public String date(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        String mi = "" + min;
        if (min < 10){ mi = "0"+mi;}
        String dateTime = day+"."+month+"."+year+", "+hour+":"+mi;
        return dateTime;
    }
}
