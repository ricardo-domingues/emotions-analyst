package com.example.uis.facebook_emotions.Model;
/*
https://developers.themoviedb.org/3/genres/get-movie-list
*/
public enum MovieGenre {
    ACTION("Ação", 28),
    ADVENTURE("Aventura", 12),
    ANIMATION("Animação", 16),
    COMEDY("Comédia", 35),
    CRIME("Crime", 80),
    DOCUMENTARY("Documentário", 99),
    DRAMA("Drama", 18),
    FAMILY("Familia", 10751),
    FANTASY("Fantasia", 14),
    HISTORY("História", 36),
    HORROR("Horror", 27),
    MUSIC("Musica", 10402),
    MYSTERY("Mistério", 9648),
    ROMANCE("Romance", 10479),
    SCIENCE_FICTION("Sci-Fi", 878),
    TV_MOVIE("Filme TV", 10770),
    THRILLER("Thriller", 53),
    WAR("Guerra", 10752),
    WESTERN("Velho Oeste", 37);


    private int id;
    private String name;

    MovieGenre(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
