package hu.petrik.museumfrontendjavafx.painting;

import com.google.gson.Gson;
import hu.petrik.museumfrontendjavafx.web.DB;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Painting {
    private Integer id;
    private String title;
    private int year;
    private boolean onDisplay;

    public Painting(Integer id, String title, int year, boolean on_display) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.onDisplay = on_display;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public boolean isOnDisplay() {
        return onDisplay;
    }
    public void setOnDisplay(boolean onDisplay) {
        this.onDisplay = onDisplay;
    }

    public String toJson(){
        return  new Gson().toJson(this);
    }

    static public ArrayList<Painting> allFromServer() throws IOException {
        Pair<Integer, InputStream> response = DB.getCall("paintings");
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getValue()));
        StringBuilder boby = new StringBuilder();
        String line = br.readLine();
        while (line!= null){
            boby.append(line);
            line = br.readLine();
        }
        String content = boby.toString();
        if (response.getKey() < 400){
            Painting[] paintings = new Gson().fromJson(content, Painting[].class);
            return (ArrayList<Painting>) Arrays.asList(paintings);
        }
        else {
            throw new IOException(Arrays.toString(response.getValue().readAllBytes()));
        }
    }
    static public Painting oneFromServer(int index) throws IOException {
        Pair<Integer, InputStream> response = DB.getCall("paintings/"+index);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getValue()));
        StringBuilder boby = new StringBuilder();
        String line = br.readLine();
        while (line!= null){
            boby.append(line);
            line = br.readLine();
        }
        String content = boby.toString();
        if (response.getKey() < 400){
            return new Gson().fromJson(content,Painting.class);
        }
        else {
            throw new IOException(Arrays.toString(response.getValue().readAllBytes()));
        }
    }
    static public int deleteFromId(int id) throws IOException {
        return DB.deleteCall("paintings/"+id).getKey();
    }
    public int alter() throws IOException{
        Pair<Integer, InputStream> response = DB.patchCall("paintings/"+this.id,new Gson().toJson(this));
        if (response.getKey() < 400){
            return response.getKey();
        }
        else {
            throw new IOException(Arrays.toString(response.getValue().readAllBytes()));
        }
    }
    public int add() throws IOException{
        Pair<Integer, InputStream> response = DB.postCall("paintings/",new Gson().toJson(this));
        if (response.getKey() < 400){
            return response.getKey();
        }
        else {
            throw new IOException(Arrays.toString(response.getValue().readAllBytes()));
        }
    }
}
