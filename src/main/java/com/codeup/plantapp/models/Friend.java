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
    private User userID1;

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
        return userID1;
    }
    public void setUserID1(User userID1) {
        this.userID1 = userID1;
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
    public Friend(User userID1, User userID2, boolean confirmed) {
        this.userID1 = userID1;
        this.userID2 = userID2;
        this.confirmed = confirmed;
    }
}