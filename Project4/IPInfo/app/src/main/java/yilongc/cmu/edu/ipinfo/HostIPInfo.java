package yilongc.cmu.edu.ipinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

/**
 * This class is the main activity for the click event of submit button.
 *
 * @author Yilong Chang
 */
public class HostIPInfo extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * The click listener will need a reference to this object, so that upon successfully getting back the lcoation of IP, it
         * can callback to this object with the resulting String.
         */
        final HostIPInfo hostIPInfo = this;

        /**
         * Find the "submit" button, and add a listener to it
         */
        Button submitButton = (Button) findViewById(R.id.submit);

        // Add a listener to the send button
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View viewParam) {
                String searchIP = ((EditText) findViewById(R.id.searchIP)).getText().toString();
                GetIPInfo getIPInfo = new GetIPInfo();
                getIPInfo.search(searchIP, hostIPInfo); // Done asynchronously in another thread.  It calls ip.locationReady() in this thread when complete.
            }
        });
    }

    /**
     * This is called by the GetIPInfo object when the location is ready.
     * This allows for passing back the location info for updating the result TextView.
     */
    public void locationReady(String location) {
        TextView searchView = (EditText) findViewById(R.id.searchIP);
        TextView resultView = (TextView) findViewById(R.id.result);
        TextView locationView = (TextView) findViewById(R.id.location);
        CharSequence result;
        CharSequence searchIP = searchView.getText();
        if (location != null) {
            // Set result
            if (searchIP == null || searchIP.length() == 0) {
                result = "Here is the location of you own IP";
            } else {
                // If empty IP, get the client's own IP location
                result = "Here is the location of IP " + searchIP;
            }
            resultView.setText(result);
            locationView.setText(location);
            resultView.setVisibility(View.VISIBLE);
        } else {
            result = "Sorry, I could not find the location of the IP " + searchIP;
            resultView.setText(result);
            resultView.setText(null);
            resultView.setVisibility(View.INVISIBLE);
        }
        searchView.setText("");
    }
}
