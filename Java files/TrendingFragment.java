package com.example.myapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TrendingFragment extends Fragment {
    View return_fragment;
    EditText editText;
    LineChart lineChart;
    TextView textView;
    String search_Str = "";
    private ProgressBar progressBar;
    private TextWatcher textWatcher = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return_fragment = inflater.inflate(R.layout.fragment_trending, container, false);
        editText = return_fragment.findViewById(R.id.trending_searchQuery);
        textView = return_fragment.findViewById(R.id.sampleText);
        progressBar = return_fragment.findViewById(R.id.progressBar1);
        lineChart = return_fragment.findViewById(R.id.trending_chart);
        progressBar.setVisibility(View.GONE);
        final List<Entry> entries_one = new ArrayList<>();

            int[] timeline = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,15,9,7,6,28,36,75,100,86,68,69,58,44,33};
            int[] values = new int[timeline.length];
            for(int i = 0;i<timeline.length;i++){
//                JSONObject currentObject = timeline.getJSONObject(i);
//                int current_int = (int)currentObject.getJSONArray("value").get(0);
                entries_one.add(new Entry(i,timeline[i]));
                values[i] = timeline[i];

            }

            LineDataSet lineDataSet = new LineDataSet(entries_one,"Trending chart for CoronaVirus");
            lineDataSet.setColor(Color.parseColor("#b589d6"));
            lineDataSet.setHighLightColor(Color.parseColor("#b589d6"));
            lineDataSet.setCircleColor(Color.parseColor("#b589d6"));
            lineDataSet.setColor(Color.parseColor("#4b4680"));
            List<ILineDataSet> dataSets=new ArrayList<>();
            dataSets.add(lineDataSet);
            LineData data=new LineData(dataSets);

        lineChart.getXAxis().setAxisLineWidth(0);
        lineChart.getXAxis().setDrawGridLines(false);

        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisRight().setAxisLineWidth(0);
        lineChart.getAxisRight().setDrawZeroLine(false);

        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisLeft().setAxisLineWidth(0);
        lineChart.getAxisLeft().setDrawZeroLine(false);
        lineChart.getAxisLeft().setZeroLineWidth(0);
        lineChart.getAxisLeft().setAxisLineColor(Color.parseColor("#FFFFFF"));
        lineChart.setData(data);
        lineChart.invalidate();

            lineChart.setData(data);
            lineChart.invalidate();


        final RequestQueue queue = Volley.newRequestQueue(requireActivity().getApplicationContext());
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEND)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    String query_text = editText.getText().toString();
                    if(query_text.length()==0)
                    {
                        query_text="CoronaVirus";
//                        textView.setText(query_text);
                    }


                    String api_callUrl = "https://hw8webtech.wl.r.appspot.com/trending?q="+query_text;


                    JsonObjectRequest jrequest = new JsonObjectRequest(Request.Method.GET, api_callUrl, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                progressBar.setVisibility(View.GONE);
                                queue.stop();
                                JSONObject def = response.getJSONObject("default");
                                JSONArray timelinedData = def.getJSONArray("timelineData");
                                List<Entry> dataVals = new ArrayList<Entry>();
                                for(int i=0;i<timelinedData.length();i++)
                                {
                                    JSONObject curr_json = timelinedData.getJSONObject(i);
                                    JSONArray val = curr_json.getJSONArray("value");
                                    int curr_val = val.getInt(0);
                                    dataVals.add(new Entry(i,curr_val));
                                }


                                LineDataSet lineDataSet1 = new LineDataSet(dataVals, "Trending chart for ");
                                List<ILineDataSet> dataSets=new ArrayList<>();
                                dataSets.add(lineDataSet1);
                                LineData lineData = new LineData(dataSets);

                                lineChart.getXAxis().setAxisLineWidth(0);
                                lineChart.getXAxis().setDrawGridLines(false);

                                lineChart.getAxisRight().setDrawGridLines(false);
                                lineChart.getAxisRight().setAxisLineWidth(0);
                                lineChart.getAxisRight().setDrawZeroLine(false);

                                lineChart.getAxisLeft().setDrawGridLines(false);
                                lineChart.getAxisLeft().setAxisLineWidth(0);
                                lineChart.getAxisLeft().setDrawZeroLine(false);
                                lineChart.getAxisLeft().setZeroLineWidth(0);
                                lineChart.getAxisLeft().setAxisLineColor(Color.parseColor("#FFFFFF"));
                                lineChart.setData(lineData);
                                lineChart.invalidate();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressBar.setVisibility(View.GONE);
                                    queue.stop();
                                }
                            });

////                    StringRequest stringRequest = new StringRequest(Request.Method.GET, api_callUrl, new Response.Listener<String>() {
////                        @Override
////                        public void onResponse(String response) {
////                            progressBar.setVisibility(View.GONE);
////                            queue.stop();
////
////                        }
////
////                    }, new Response.ErrorListener() {
////                        @Override
////                        public void onErrorResponse(VolleyError error) {
////                            textView.setText("Error");
////                            progressBar.setVisibility(View.GONE);
////                            queue.stop();
////                        }
////                    }
////                    );
                    queue.add(jrequest);

                }
                return false;
            }
        });


    return return_fragment;
    }

    private ArrayList<Entry> dataValues1(int[] array)
    {
        Log.d("in datavals = ", String.valueOf(array.length));
        ArrayList<Entry> dataVals = new ArrayList<>();
        int k=0;
        for(int x:array)
        {

            dataVals.add(new Entry(k,array[k]));
            k++;

        }
        Log.d("DataVals size = ", String.valueOf(dataVals.size()));
        return dataVals;

    }
//CoronaVirus : [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,15,9,7,6,28,36,75,100,86,68,69,58,44,33]

    private void createChart(String response){
        int[] arr;

            String res_modified = response.substring(1, response.length() - 1);
            String[] final_arr = res_modified.split(",");
            arr = new int[final_arr.length];
            int i=0;
            for (String s: final_arr) {

                arr[i] = Integer.parseInt(final_arr[i]);
                i++;
            }

        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(arr), "Data Set 1");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();

    }


}


