package com.jagamypriera.thetruthnews.fragment.newslist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsListResponseModel {
    @JsonProperty("status") public String status;
    @JsonProperty("code") public String code;
    @JsonProperty("totalResults") public int total;
    @JsonProperty("articles") public ArrayList<News> newsList=new ArrayList<>();
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class News{
        @JsonProperty("author") public String author;
        @JsonProperty("title") public String title;
        @JsonProperty("description") public String description;
        @JsonProperty("publishedAt") public String date;
        @JsonProperty("urlToImage") public String image;
        @JsonProperty("url") public String url;
        public int headlinePosition;
        public boolean favorite;
        public boolean loading;
        public ArrayList<News> headlines=new ArrayList<>();

        @Override
        public String toString() {
            return "News{" +
                    "author='" + author + '\'' +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", date='" + date + '\'' +
                    ", image='" + image + '\'' +
                    ", url='" + url + '\'' +
                    ", headlines=" + headlines +
                    '}';
        }
    }
}
