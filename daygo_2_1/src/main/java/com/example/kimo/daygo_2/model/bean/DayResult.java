package com.example.kimo.daygo_2.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
public class DayResult {
    private boolean error;
    private List<Result> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
