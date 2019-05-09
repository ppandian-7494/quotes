package com.iptech.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Orders {
    private long id;
    private String name;
    private long quote;
    private long votes;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getQuote() {
        return quote;
    }

    public void setQuote(long quote) {
        this.quote = quote;
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }

    public boolean equals(Orders orders) {
        return (this.getId() == orders.getId() && this.getName() == orders.getName() && this.getQuote() == orders.getQuote() && this.getVotes() == orders.getVotes());
    }

    @Override
    public String toString()
    {
        return "Order [id=" + id + ", name=" + name + ", quote=" + quote + ", votes=" + votes + "]";
    }
}
