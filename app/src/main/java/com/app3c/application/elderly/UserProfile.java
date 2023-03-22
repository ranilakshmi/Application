package com.app3c.application.elderly;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app3c.application.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class UserProfile extends AppCompatActivity {

    public static String[] genres = new String[]{"Children","Education","Environment","Health"};

    public static int[][] generate_matrix(HashMap<String, String[]> movies, int events_length) {

        int[][] matrix = new int[events_length][genres.length];

        int i=0;

        for(Map.Entry<String,String[]> mpElem : movies.entrySet()) {
            Arrays.sort(mpElem.getValue());
            int j = 0;
            int k = 0;
            while (j < mpElem.getValue().length && k < genres.length) {
                if (mpElem.getValue()[j] == genres[k]) {
                    matrix[i][k] = 1;
                    k++;
                    j++;
                } else {
                    matrix[i][k] = 0;
                    k++;
                }
            }
            while (k < genres.length) {
                matrix[i][k] = 0;
                k++;
            }
            i++;
        }
        return matrix;
    }

    public static <K extends Comparable, V> Map<K, V> sortByKeys(Map<K, V> map) {
        Map<K, V> treeMap = new TreeMap<>(new Comparator<K>() {
            @Override
            public int compare(K a, K b) {
                return b.compareTo(a);
            }
        });
        treeMap.putAll(map);
        return treeMap;
    }
    public static String[] recommend(ArrayList<Integer> movie_ratings, HashMap<String, String[]>upcoming_events, HashMap<String, String[]>done_events) {

        int no_of_movies = done_events.size();

        int[][] mg_matrix = generate_matrix(done_events,done_events.size());

        for (int i = 0; i < movie_ratings.size(); i++) {
            for (int j = 0; j < genres.length; j++) {
                mg_matrix[i][j] = mg_matrix[i][j] * movie_ratings.get(i);
            }
        }

        float[] added_col = new float[genres.length];
        int sum_added_col = 0;

        for (int j=0;j<genres.length;j++){
            int sume = 0;
            for (int i = 0; i < movie_ratings.size(); i++) {
                sume = sume + mg_matrix[i][j];
            }
            sum_added_col = sum_added_col + sume;
            added_col[j] = sume;
        }

        Map<Float,String> category_rating = new HashMap<>();
        for (int j = 0; j < genres.length; j++) {
            added_col[j] = added_col[j] / sum_added_col;
            category_rating.put(added_col[j],genres[j] );
        }
        category_rating = sortByKeys(category_rating);
        String msg = "Recommended categories are ";
        for (Map.Entry<Float, String> entry : category_rating.entrySet()) {
            System.out.println("[" + entry.getKey() + ", " + (entry.getValue() + movie_ratings.size()) + "]");
        }
        //Log.i("result", msg);
        System.out.println(msg);

        int[][] candidate_movie_matrix = generate_matrix(upcoming_events, upcoming_events.size());

        //System.out.println(upcoming_events.keySet());

        Set<String> keys_set = upcoming_events.keySet();

        String[] keys = keys_set.toArray(new String[keys_set.size()]);
        Map<Float, String> new_ratings = new HashMap<Float, String>();

        for (int i = 0; i < candidate_movie_matrix.length; i++) {
            float weight = 0;
            for (int j = 0; j < genres.length; j++) {
                weight += candidate_movie_matrix[i][j] * added_col[j];
            }
            new_ratings.put(weight, keys[i]);
        }

        new_ratings = sortByKeys(new_ratings);
        return new_ratings.values().toArray(new String[new_ratings.size()]);
    }

//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_profile);
//
//        RadioGroup radioGroup1;
//        RadioGroup radioGroup2;
//        RadioGroup radioGroup3;
//
//        Button submit;
//
//        submit = (Button)findViewById(R.id.submit_user_profile);
//
//        // Bind the components to their respective objects
//        // by assigning their IDs
//        // with the help of findViewById() method
//        radioGroup1 = (RadioGroup)findViewById(R.id.radioEvent1);
//        radioGroup2 = (RadioGroup)findViewById(R.id.radioEvent2);
//        radioGroup3 = (RadioGroup)findViewById(R.id.radioEvent3);

//    public static void main(String args[]){
//        int[] r = new int[]{1,1,0,0};
//        HashMap<String, String[]> upcoming_events = new HashMap<String,String[]>();
//        upcoming_events.put("Q1EsA", new String[]{"Education","Children","Health"});
//        upcoming_events.put("Q2EsA", new String[]{"Environment','Health"});
//        upcoming_events.put("Q3EsA", new String[]{"Education"});
//        HashMap<String, String[]> done_events = new HashMap<String,String[]>();
//        done_events.put("A1EsA", new String[]{"Health"});
//        done_events.put("A2EsA", new String[]{"Environment","Children"});
//        done_events.put("A3EsA", new String[]{"Education","Children"});
//        done_events.put("A4EsA", new String[]{"Environment", "Health"});
//
//        String[] recommended_list = recommend(r, upcoming_events, done_events);
//
//        for(int i = 0; i < recommended_list.length; i++) {
//            System.out.println(recommended_list[i]);
//        }
//    }
}