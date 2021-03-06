package net.toload.main.hd.ui;

import android.app.Activity;

import net.toload.main.hd.data.DataSource;
import net.toload.main.hd.data.Word;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Art Hung on 2015/4/26.
 */
public class ManageImRunnable implements Runnable{


    private ManageImHandler handler;
    private Activity activity;
    private DataSource datasource;
    private String table;
    private String query;
    private boolean searchRoot;
    private int maximum;
    private int offset;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public ManageImRunnable(ManageImHandler handler, Activity activity, String table, String query,
                            boolean searchRoot, int maximum, int offset) {
        this.handler = handler;
        this.activity = activity;
        this.table = table;
        this.query = query;
        this.searchRoot = searchRoot;
        this.maximum = maximum;
        this.offset = offset;

        datasource = new DataSource(this.activity);
    }

    public void run() {
        handler.showProgress();

        handler.updateGridView(loadImWord(table, query, maximum, offset));

        /*if (maximum > 0) {
            handler.updateGridViewInitial(loadImWord(table, query, maximum, offset));
        }else{
            handler.updateGridView(loadImWord(table, query, maximum, offset));
        }*/
    }

    private List<Word> loadImWord(String table, String query, int maximum, int offset){
        List<Word> results = new ArrayList<>();

        try {
            datasource.open();
            results = datasource.loadWord(table, query, this.searchRoot, maximum, offset);
            datasource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

}
