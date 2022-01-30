package com.example.rollcount.SelectedGameActivity.play;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.rollcount.Game;
import com.example.rollcount.MainActivity;
import com.example.rollcount.R;
import com.example.rollcount.Roll;
import com.example.rollcount.RollsList;
import com.example.rollcount.databinding.PlayTabBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

/**
 * PlayFragment
 * Purpose: renders the view of the "Play" tab which contains the list of rolls
 *      that can be incremented and decremented by the user.
 *      A list was used to easily display all the rolls and the count was displayed on each row.
 *      A + and - button was used to count rows because it is universally recognized and easy to use.
 * Design rationale: this was split off as its own class so each tab can be designed separately
 * Outstanding issues: None
 */
public class PlayFragment extends Fragment {
    private ArrayList<Roll> rollsDataList;
    private ListView rollsList;
    private ArrayAdapter<Roll> rollsAdapter;
    private PlayTabBinding binding;
    private ArrayList<Integer> rollCounts;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = PlayTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rollsList = root.findViewById(R.id.rolls_list);

        Intent intent = getActivity().getIntent();
        Game game = intent.getParcelableExtra(MainActivity.SELECTED_GAME);
        rollCounts = (ArrayList<Integer>) intent.getSerializableExtra(MainActivity.SELECTED_GAME_ROLL_COUNTS);
        int gameIndex = intent.getIntExtra(MainActivity.SELECTED_GAME_INDEX, 0);


        // Generate list of rolls
        rollsDataList = new ArrayList<>();
        for (int i = 0; i < rollCounts.size(); i++) {
            rollsDataList.add(new Roll(game.getRollNumFromPosition(i), rollCounts.get(i)));
        }
        rollsAdapter = new RollsList(root.getContext(), rollsDataList, game, gameIndex, rollCounts);
        rollsList.setAdapter(rollsAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}