package net.simplyadvanced.funfacts;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class FunFactsActivity extends Activity {

    private RelativeLayout mRootContainer;
    private FactBook mFaceBook = new FactBook();
    private ColorWheel mColorWheel = new ColorWheel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_facts);

        mRootContainer = (RelativeLayout) findViewById(R.id.rootContainer);
        final TextView factLabel = (TextView) findViewById(R.id.factTextView);
        final Button showFactButton = (Button) findViewById(R.id.showFactButton);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = mColorWheel.getColor();
                factLabel.setText(mFaceBook.getFact());
                showFactButton.setTextColor(color);
                mRootContainer.setBackgroundColor(color);
            }
        };
        showFactButton.setOnClickListener(listener);

//        Toast.makeText(this, "Activity created", Toast.LENGTH_LONG).show();
        Log.d("DEBUG: FunFactsActivity", "onCreate()");
    }

}
