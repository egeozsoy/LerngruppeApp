package cp.chatapp;

import android.app.Fragment;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Chantal on 17.09.2017.
 */

public class fragment_yourText extends Fragment {

    public static final String KEYIn = "TextIn";
    public static final String KEYInD = "TextInDate";

    // member variables accessible from anywhere in this fragment
    String yourText;
    String dateText;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        String txt = bundle.getString(KEYIn);
        yourText = txt;
        String txtD = bundle.getString(KEYInD);
        dateText = txtD;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yourtext_layout, container, false);
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(yourText);

        TextView date = (TextView) view.findViewById(R.id.date);
        date.setText(dateText);
        return view;
    }
}
