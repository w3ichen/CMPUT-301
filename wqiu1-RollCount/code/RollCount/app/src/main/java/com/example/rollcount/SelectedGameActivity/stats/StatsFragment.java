package com.example.rollcount.SelectedGameActivity.stats;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.rollcount.Game;
import com.example.rollcount.MainActivity;
import com.example.rollcount.R;
import com.example.rollcount.databinding.StatsTabBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.OptionalDouble;

public class StatsFragment extends Fragment {
    private Intent intent;
    private Game game;
    private ArrayList<Integer> rollCounts;
    private StatsTabBinding binding;

    // Credits: https://medium.com/@mobindustry/how-to-quickly-implement-beautiful-charts-in-your-android-app-cf4caf050772
    // Credits: https://www.geeksforgeeks.org/how-to-create-a-barchart-in-android/
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = StatsTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // (0) Get passed in values
        intent = getActivity().getIntent();
        game = intent.getParcelableExtra(MainActivity.SELECTED_GAME);
        rollCounts = (ArrayList<Integer>) intent.getSerializableExtra(MainActivity.SELECTED_GAME_ROLL_COUNTS);


        // (1) Bar Chart
        BarChart chart = (HorizontalBarChart) root.findViewById(R.id.chart);
        BarDataSet data = new BarDataSet(getDataSet(), "counts");
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.BLACK);
        data.setColors(ColorTemplate.MATERIAL_COLORS);

        chart.setData(new BarData(data));
        chart.animateXY(2000, 2000);
        chart.invalidate();

        // Format the x axis to strings
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "Roll " + Math.round(value);

            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //  move to left side
        xAxis.setTextSize(20f); // increase font size
        xAxis.setLabelCount(rollCounts.size()); // shows the value labels

        // (2) Average, max, min
        TextView rollAverage = root.findViewById(R.id.roll_average);
        TextView rollMax = root.findViewById(R.id.roll_max);
        TextView rollMin = root.findViewById(R.id.roll_min);
        // (2.1) Make the calculations
        double average = rollCounts.stream().mapToDouble(x -> x).average().orElse(-1);
        // Round average to one decimal place
        int scale = (int) Math.pow(10, 1);
        average = (double) Math.round(average * scale) / scale;
        int min = Collections.min(rollCounts);
        int max = Collections.max(rollCounts);
        // (2.2) Set the text
        rollAverage.setText("Average\n" + String.valueOf(average));
        rollMin.setText("Min\n" + String.valueOf(min));
        rollMax.setText("Max\n" + String.valueOf(max));

        return root;
    }

    private ArrayList<BarEntry> getDataSet() {
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();

        for (int i = 0; i < rollCounts.size(); i++) {
            barEntries.add(new BarEntry(game.getRollNumFromPosition(i), rollCounts.get(i)));
        }

        return barEntries;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}