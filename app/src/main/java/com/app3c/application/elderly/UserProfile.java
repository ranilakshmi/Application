package com.app3c.application.elderly;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app3c.application.R;
import com.app3c.application.caretaker.CaretakerLoginPage;
import com.app3c.application.caretaker.CaretakerRegistrationPage;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class UserProfile extends AppCompatActivity {

    public static String[] genres = new String[]{"Children","Education","Environment","Health"};

    public static int[][] generate_matrix(HashMap<Integer, String[]> movies, int start, int end) {

        int[][] matrix = new int[end - start][4];

        //System.out.println(end);
        //System.out.println(start);

        for (int i = start; i < end; i++) {
            // System.out.println(i);
            Arrays.sort(movies.get(i + 1));
            int j = 0;
            int k = 0;
            while (j < movies.get(i + 1).length && k < 4) {
                //System.out.println(movies.get(i+1)[j]
                //+genres[k]);
                //System.out.println(i+" "+j+" "+k);
                if (movies.get(i + 1)[j] == genres[k]) {
                    matrix[i - start][k] = 1;
                    k++;
                    j++;
                } else {
                    matrix[i - start][k] = 0;
                    k++;
                }

            }
            while (k < 4) {
                matrix[i - start][k] = 0;
                k++;
            }

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



    String recommend(int [] movie_ratings) {

        HashMap<Integer, String[]> movie_genres = new HashMap<Integer, String[]>();
        movie_genres.put(1, new String[]{"Children", "Education", "Environmemt"});
        movie_genres.put(2, new String[]{"Children", "Education", "Health"});
        movie_genres.put(3, new String[]{"Environment"});
        movie_genres.put(4, new String[]{"Children", "Environment", "Health"});
        movie_genres.put(5, new String[]{"Environment"});
        movie_genres.put(6, new String[]{"Environment", "Education"});

        int total_no_of_movies = movie_genres.size();

        int[][] mg_matrix = generate_matrix(movie_genres, 0, movie_ratings.length);

        for (int i = 0; i < movie_ratings.length; i++) {
            for (int j = 0; j < genres.length; j++) {
                mg_matrix[i][j] = mg_matrix[i][j] * movie_ratings[i];
            }
        }

        float[] added_col = new float[4];
        int sum_added_col = 0;

        for (int j = 0; j < 4; j++) {
            int sume = 0;
            for (int i = 0; i < movie_ratings.length; i++) {
                sume = sume + mg_matrix[i][j];
            }
            sum_added_col = sum_added_col + sume;
            added_col[j] = sume;
        }

        Map<Float,String> category_rating = new HashMap<>();
        for (int j = 0; j < 4; j++) {
            added_col[j] = added_col[j] / sum_added_col;
            category_rating.put(added_col[j],genres[j] );
        }
        category_rating = sortByKeys(category_rating);
        String msg = "Recommended categories are ";
        for (Map.Entry<Float, String> entry : category_rating.entrySet()) {
            System.out.println("[" + entry.getKey() + ", " + (entry.getValue() + movie_ratings.length) + "]");
             msg = msg + entry.getValue() +",";
        }
        Log.i("result", msg);
        /*
         for (int i=0;i<added_col.length;i++){
            System.out.println(added_col[i]);
        }*/

        int[][] candidate_movie_matrix = generate_matrix(movie_genres, movie_ratings.length, total_no_of_movies);

        Map<Float, Integer> new_ratings = new HashMap<Float, Integer>();

        for (int i = 0; i < candidate_movie_matrix.length; i++) {
            float weight = 0;
            for (int j = 0; j < 4; j++) {
                weight += candidate_movie_matrix[i][j] * added_col[j];
            }
            new_ratings.put(weight, i);
        }

        new_ratings = sortByKeys(new_ratings);

        for (Map.Entry<Float, Integer>
                entry : new_ratings.entrySet()) {
            System.out.println(
                    "[" + entry.getKey()
                            + ", " + (entry.getValue() + movie_ratings.length) + "]");
        }
        return msg;
    }

    //int[] userRatings = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        final EditText rating1 = findViewById(R.id.rating1);
        final EditText rating2 = findViewById(R.id.rating2);
        final EditText rating3 = findViewById(R.id.rating3);

        final Button submitbutton = findViewById(R.id.submit_user_profile);
        submitbutton.setOnClickListener(new View.OnClickListener() {
                                            @Override public void onClick(View view) {
                                                final int r1 = rating1.getInputType();
                                                final int r2 = rating2.getInputType();
                                                final int r3 = rating3.getInputType();
                                                int[] r = {r1, r2, r3};
                                                String msg = recommend(r);
                                                Context context = getApplicationContext();
                                                //CharSequence text = "Please enter all the details";
                                                int duration = Toast.LENGTH_LONG;

                                                Toast toast = Toast.makeText(context,msg, duration);
                                                toast.show();
                                                startActivity(new Intent(UserProfile.this, ElderlyLoginPage.class));
                                            }
                                        });

    }
}

