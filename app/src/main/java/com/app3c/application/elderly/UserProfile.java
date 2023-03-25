package com.app3c.application.elderly;

import java.util.*;

public class UserProfile {

    public static String[] genres = new String[]{"Children", "Education", "Environment", "Health"};

    public static int[][] generate_matrix(HashMap<String, String[]> movies, int events_length) {

        int[][] matrix = new int[events_length][genres.length];
        int i = 0;
        for (Map.Entry<String, String[]> mpElem : movies.entrySet()) {
            Arrays.sort(mpElem.getValue());
            int j = 0;
            int k = 0;
            while (j < mpElem.getValue().length && k < genres.length) {
                if (mpElem.getValue()[j].equals(genres[k])) {
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
        Map<K, V> treeMap = new TreeMap<>((a, b) -> {
            return b.compareTo(a);
        });
        treeMap.putAll(map);
        return treeMap;
    }

    public static String[] recommend(ArrayList<Integer> movie_ratings, HashMap<String, String[]> upcoming_events, HashMap<String, String[]> done_events) {
        int[][] mg_matrix = generate_matrix(done_events, done_events.size());

        for (int i = 0; i < movie_ratings.length; i++) {
            for (int j = 0; j < genres.length; j++) {
                mg_matrix[i][j] = mg_matrix[i][j] * movie_ratings[i];
            }
        }

        float[] added_col = new float[genres.length];
        int sum_added_col = 0;

        for (int j = 0; j < genres.length; j++) {
            int sume = 0;
            for (int i = 0; i < movie_ratings.length; i++) {
                sume = sume + mg_matrix[i][j];
            }
            sum_added_col = sum_added_col + sume;
            added_col[j] = sume;
        }

        Map<Float, String> category_rating = new HashMap<>();
        for (int j = 0; j < genres.length; j++) {
            added_col[j] = added_col[j] / sum_added_col;
            category_rating.put(added_col[j], genres[j]);
        }
        category_rating = sortByKeys(category_rating);
        //String msg = "Recommended categories are ";
        for (Map.Entry<Float, String> entry : category_rating.entrySet()) {
            System.out.println("[" + entry.getKey() + ", " + (entry.getValue() + movie_ratings.length) + "]");
        }

        int[][] candidate_movie_matrix = generate_matrix(upcoming_events, upcoming_events.size());
        Set<String> keys_set = upcoming_events.keySet();

        String[] keys = keys_set.toArray(new String[0]);

        Map<Float, String> new_ratings = new HashMap<>();

        for (int i = 0; i < candidate_movie_matrix.length; i++) {
            float weight = 0;
            for (int j = 0; j < genres.length; j++) {
                weight += candidate_movie_matrix[i][j] * added_col[j];
            }
            new_ratings.put(weight, keys[i]);
        }

        new_ratings = sortByKeys(new_ratings);
        return new_ratings.values().toArray(new String[0]);
    }
}