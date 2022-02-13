package hu.petrik.museumfrontendjavafx.statue;

import com.google.gson.Gson;
import hu.petrik.museumfrontendjavafx.painting.Painting;
import hu.petrik.museumfrontendjavafx.web.DB;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Statue {
    private Integer id;
    private String person;
    private int height;
    private int price;

    public Statue(Integer id, String person, int height, int price) {
        this.id = id;
        this.person = person;
        this.height = height;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPerson() {
        return person;
    }
    public void setPerson(String person) {
        this.person = person;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public String toJson(){
        return  new Gson().toJson(this);
    }

    static public ArrayList<Statue> allFromServer() throws IOException {
        Pair<Integer, InputStream> response = DB.getCall("statues");
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getValue()));
        StringBuilder boby = new StringBuilder();
        String line = br.readLine();
        while (line!= null){
            boby.append(line);
            line = br.readLine();
        }
        String content = boby.toString();
        if (response.getKey() < 400){
            Statue[] statues = new Gson().fromJson(content,Statue[].class);
            return (ArrayList<Statue>) Arrays.asList(statues);
        }
        else {
            throw new IOException(Arrays.toString(response.getValue().readAllBytes()));
        }
    }
    static public Statue oneFromServer(int index) throws IOException {
        Pair<Integer, InputStream> response = DB.getCall("statues/"+index);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getValue()));
        StringBuilder boby = new StringBuilder();
        String line = br.readLine();
        while (line!= null){
            boby.append(line);
            line = br.readLine();
        }
        String content = boby.toString();
        if (response.getKey() < 400){
            return new Gson().fromJson(content,Statue.class);
        }
        else {
            throw new IOException(Arrays.toString(response.getValue().readAllBytes()));
        }
    }
    static public int deleteFromId(int id) throws IOException {
        return DB.deleteCall("statues/"+id).getKey();
    }
    public int alter() throws IOException{
        Pair<Integer, InputStream> response = DB.patchCall("statues/"+this.id,new Gson().toJson(this));
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
