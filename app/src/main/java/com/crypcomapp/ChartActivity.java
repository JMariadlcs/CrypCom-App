package com.crypcomapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.charts.Waterfall;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("John", 10000));
        data.add(new ValueDataEntry("Jake", 12000));
        data.add(new ValueDataEntry("Peter", 18000));

        pie.data(data);

        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
        anyChartView.setChart(pie);

    }
    public void chart_to_favourites(View view){
        ImageButton FavouriteButtonC = (ImageButton) findViewById(R.id.FavouritesButtonChart);

        Intent intent = new Intent(this, FavouriteActivity.class);
        startActivity(intent);
    }
    public void chart_to_profile(View view){
        ImageButton ProfileButtonC = (ImageButton) findViewById(R.id.ProfileButtonChart);

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
    public void chart_to_home(View view){
        ImageButton HomeButtonC = (ImageButton) findViewById(R.id.HomeButtonChart);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
