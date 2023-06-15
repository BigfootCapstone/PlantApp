package com.codeup.plantapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "friends")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id1")
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_id2")
    private User userID2;

    @Column
    private boolean confirmed;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public User getUserID1() {
        return user;
    }
    public void setUserID1(User userID1) {
        this.user = user;
    }

    public User getUserID2() {
        return userID2;
    }
    public void setUserID2(User userID2) {
        this.userID2 = userID2;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Friend() {
    }
    public Friend(User user, User userID2, boolean confirmed) {
        this.user = user;
        this.userID2 = userID2;
        this.confirmed = confirmed;
    }

    public Friend(long id, User user, User userID2, boolean confirmed) {
        this.id = id;
        this.user = user;
        this.userID2 = userID2;
        this.confirmed = confirmed;
    }
}