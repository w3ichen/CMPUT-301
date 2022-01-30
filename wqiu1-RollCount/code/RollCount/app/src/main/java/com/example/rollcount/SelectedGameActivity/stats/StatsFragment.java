package com.example.rollcount.SelectedGameActivity.stats;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.rollcount.Game;
import com.example.rollcount.MainActivity;
import com.example.rollcount.R;
import com.example.rollcount.databinding.StatsTabBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;

public class StatsFragment extends Fragment {

    private StatsTabBinding binding;

    // Credits: https://medium.com/@mobindustry/how-to-quickly-implement-beautiful-charts-in-your-android-app-cf4caf050772
    // Credits: https://www.geeksforgeeks.org/how-to-create-a-barchart-in-android/
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = StatsTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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

        return root;
    }

    private ArrayList<BarEntry> getDataSet() {
        Intent intent = getActivity().getIntent();
        Game game = intent.getParcelableExtra(MainActivity.SELECTED_GAME);
        ArrayList<Integer> rollCounts = (ArrayList<Integer>) intent.getSerializableExtra(MainActivity.SELECTED_GAME_ROLL_COUNTS);
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