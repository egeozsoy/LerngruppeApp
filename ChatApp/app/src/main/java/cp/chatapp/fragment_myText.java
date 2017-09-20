package cp.chatapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * Created by Chantal on 17.09.2017.
 */

public class fragment_myText extends Fragment {

    public static final String KEY = "Text";
    public static final String KEYD = "TextDate";

    private String myText;
    private String dateText = "1";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        String txt = bundle.getString(KEY);
        myText = txt;
        String txtD = bundle.getString(KEYD);
        dateText = txtD;

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mytext_layout, container, false);
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(myText);

        TextView date = (TextView) view.findViewById(R.id.date);
        date.setText(dateText);
        return view;
    }
}
