package com.pawan.busfarebeta.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.pawan.busfarebeta.R;
import com.pawan.busfarebeta.app.Constants;
import com.pawan.busfarebeta.app.Utilities;
import com.pawan.busfarebeta.model.Places;
import com.pawan.busfarebeta.network.ServerRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button display;
    TextView bsfare;
    AutoCompleteTextView cur_place;
    AutoCompleteTextView destination;
    EditText show;
    Spinner spn_from;
    Spinner spn_to;
    List<Places> placeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bs_fare);
        display = (Button) findViewById(R.id.btnready);
        bsfare = (TextView) findViewById(R.id.bsfare);
        cur_place = (AutoCompleteTextView) findViewById(R.id.cur_place);
        destination = (AutoCompleteTextView) findViewById(R.id.destination);
        //spn_from = (Spinner) findViewById(R.id.spn_from);
        //spn_to= (Spinner) findViewById(R.id.spn_to);

        placeList = new ArrayList<>();
        //TODO : GET PLACE LIST FROM SERVER
        ArrayAdapter<Places> adapter = new ArrayAdapter<Places>(this, android.R.layout.simple_list_item_1, placeList );
        cur_place.setAdapter(adapter);
        cur_place.setThreshold(1);
        final ArrayAdapter<Places> finalAdapter1 = adapter;
        new ServerRequest(Constants.BASE_URL + "api.php?action=get_places", new ServerRequest.OnDataReceiver()




        {

            @Override
            public void onSuccess(String res) {
                try {
                    JSONObject ob = new JSONObject(res);
                   placeList.clear();
                    placeList.addAll(Places.getPlaceList(ob.getJSONArray("data")));
                    finalAdapter1.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {

            }
        }).execute();

        adapter = new ArrayAdapter<Places>(this, android.R.layout.simple_list_item_1, placeList);
        destination.setAdapter(adapter);
        destination.setThreshold(1);
        final ArrayAdapter<Places> finalAdapter = adapter;
        new ServerRequest(Constants.BASE_URL + "api.php?action=get_places", new ServerRequest.OnDataReceiver()




        {

            @Override
            public void onSuccess(String res) {
                try {
                     JSONObject ob = new JSONObject(res);
                    placeList.clear();
                    placeList.addAll(Places.getPlaceList(ob.getJSONArray("data")));
                    finalAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {

            }
        }).execute();




        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // String from = placeList.get(spn_from.getSelectedItemPosition()).id;
                //String to= placeList.get(spn_to.getSelectedItemPosition()).id;
                String from= cur_place.getText().toString();
                String to=destination.getText().toString();
                Utilities.log("From  = " + from + " To=" + to );
                // TODO: 7/20/2017 VALIDATION
              final  ProgressDialog pg = new ProgressDialog(MainActivity.this);
                pg.setMessage("Please wait....");
                pg.show();
                new ServerRequest(Constants.BASE_URL + "api.php?action=view&FROM=" + from + "&TO=" + to, new ServerRequest.OnDataReceiver()
                {
                    @Override
                    public void onSuccess(String res) {
                        pg.dismiss();
                        Utilities.log(res);
                        parseJSON(res);
                    }

                    @Override
                    public void onError(String message) {
                        pg.dismiss();
                        Utilities.toast(MainActivity.this, message);
                    }
                }).execute();

                //TODO : create to spinner , get selected id of tospn ..finish
                //TODO : CALL SERVER
                //TODO :ONSuccess :  get fare from response  GET USER TYPE ie student , normal

            }
        });



    }



    private void parseJSON(String res) {
        try {
            JSONObject object = new JSONObject(res);
            String response = object.optString("res");

            if(response.equals("success")){
                JSONObject data = object.optJSONObject("data");
                if(data!=null){
                    String fare = data.optString("fare");
                    Utilities.toast(this, fare + " rs tirnu parni bho");
                }
            }else{
                Utilities.toast(this, response);
            }
        } catch (JSONException e) {
            Utilities.toast(this,"Something went wrong");
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnready){
            //show.setText("Your fare is");
            //Toast.makeText(MainActivity.this, "Your fare is:10", Toast.LENGTH_SHORT).show();


        }
    }
}
