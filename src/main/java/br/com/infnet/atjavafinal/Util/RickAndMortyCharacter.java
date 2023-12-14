package br.com.infnet.atjavafinal.Util;

import java.util.List;


public class RickAndMortyCharacter {
    public int id;
    public String name;
    public String status;
    public String species;
    public String type;
    public String gender;
    public Origin origin;
    public Location location;
    public String image;
    public List<String> episode;
    public String url;
    public String created;

    public static class Origin {
        public String name;
        public String url;
    }

    public static class Location {
        public String name;
        public String url;
    }
}

