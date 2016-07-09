package com.fpadilha.snowmen.models.response;

import com.fpadilha.snowmen.models.Snowman;

import java.util.List;

/**
 * Created by felipe on 08/07/2016.
 */
public class GetSnowmenResponse {
    private int count;
    private List<Snowman> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Snowman> getResults() {
        return results;
    }

    public void setResults(List<Snowman> results) {
        this.results = results;
    }
}
