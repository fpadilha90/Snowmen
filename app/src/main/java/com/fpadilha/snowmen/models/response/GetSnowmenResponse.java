package com.fpadilha.snowmen.models.response;

import com.fpadilha.snowmen.models.Snowmen;

import java.util.List;

/**
 * Created by felipe on 08/07/2016.
 */
public class GetSnowmenResponse {
    private int count;
    private List<Snowmen> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Snowmen> getResults() {
        return results;
    }

    public void setResults(List<Snowmen> results) {
        this.results = results;
    }
}
