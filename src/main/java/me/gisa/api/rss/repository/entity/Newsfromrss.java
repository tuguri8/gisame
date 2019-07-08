package me.gisa.api.rss.repository.entity;

import lombok.Data;


import javax.persistence.*;

@Data
@Entity
@Table(name="NEWS_FROM_RSS")
public class Newsfromrss {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;
    private String pubDate;
    private String link;
    private String regionName;
}
