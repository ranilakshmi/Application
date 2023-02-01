package com.app3c.application.elderly;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app3c.application.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.*;
import java.lang.String;

public class UserProfile extends AppCompatActivity {

    //int[] userRatings = new int[3];


    final EditText rating1 = findViewById(R.id.rating1);
    final EditText rating2 = findViewById(R.id.rating2);
    final EditText rating3 = findViewById(R.id.rating3);

    final int r1 = rating1.getInputType();
    final int r2 = rating1.getInputType();
    final int r3 = rating1.getInputType();


    content_based_recommendation ratings = new content_based_recommendation(r1,r2,r3);

}

class content_based_recommendation {

    static int r1,r2,r3;

    public content_based_recommendation(int r1, int r2, int r3) {
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
    }

    public static int[] movie_ratings = new int[]{r1,r2,r3};

    public static String[] genres = new String[]{"a","b","c","d"};

    public static int[][] generate_matrix(HashMap<Integer,String[]> movies, int start,int end){

        int[][] matrix = new int[end-start][4];

        //System.out.println(end);
        //System.out.println(start);

        for (int i=start;i<end;i++){
            // System.out.println(i);
            Arrays.sort(movies.get(i+1));
            int j=0;
            int k=0;
            while (j<movies.get(i+1).length && k<4){
                //System.out.println(movies.get(i+1)[j]
                //+genres[k]);
                //System.out.println(i+" "+j+" "+k);
                if (movies.get(i+1)[j] == genres[k]){
                    matrix[i-start][k] = 1;
                    k++;
                    j++;
                }
                else{
                    matrix[i-start][k] = 0;
                    k++;
                }

            }
            while (k<4){
                matrix[i-start][k] = 0;
                k++;
            }

        }
        return matrix;
    }

    public static <K extends Comparable, V> Map<K, V> sortByKeys(Map<K, V> map)
    {
        Map<K, V> treeMap = new TreeMap<>(new Comparator<K>() {
            @Override
            public int compare(K a, K b) {
                return b.compareTo(a);
            }
        });
        treeMap.putAll(map);
        return treeMap;
    }

    public static void main(String[] args) {

        HashMap<Integer, String[]> movie_genres = new HashMap<Integer, String[]>();
        movie_genres.put(1,new String[]{"a","b","c"});
        movie_genres.put(2,new String[]{"b","c","d"});
        movie_genres.put(3,new String[]{"a"});
        movie_genres.put(4,new String[]{"a","c","d"});
        movie_genres.put(5,new String[]{"c"});
        movie_genres.put(6,new String[]{"c","b"});

        int total_no_of_movies = movie_genres.size();

        int[][] mg_matrix = generate_matrix(movie_genres,0,movie_ratings.length);

        for (int i=0;i<movie_ratings.length;i++){
            for (int j=0;j<genres.length;j++){
                mg_matrix[i][j] = mg_matrix[i][j]*movie_ratings[i];
            }
        }

        float[] added_col = new float[4];
        int sum_added_col = 0;

        for (int j=0;j<4;j++){
            int sume = 0;
            for (int i=0;i<movie_ratings.length;i++){
                sume = sume+mg_matrix[i][j];
            }
            sum_added_col = sum_added_col + sume;
            added_col[j] = sume;
        }


        for (int j=0;j<4;j++){
            added_col[j] = added_col[j]/sum_added_col;
        }
        /*
         for (int i=0;i<added_col.length;i++){
            System.out.println(added_col[i]);
        }*/

        int[][] candidate_movie_matrix = generate_matrix(movie_genres,movie_ratings.length,total_no_of_movies);

        Map<Float,Integer> new_ratings = new HashMap<Float,Integer>();

        for (int i=0;i<candidate_movie_matrix.length;i++){
            float weight=0;
            for (int j=0;j<4;j++){
                weight += candidate_movie_matrix[i][j]*added_col[j];
            }
            new_ratings.put(weight,i);
        }

        new_ratings = sortByKeys(new_ratings);

        for (Map.Entry<Float, Integer>
                entry : new_ratings.entrySet())
            System.out.println(
                    "[" + entry.getKey()
                            + ", " + (entry.getValue()+movie_ratings.length) + "]");


    }
}

