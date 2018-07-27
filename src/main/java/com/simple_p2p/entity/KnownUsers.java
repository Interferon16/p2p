package com.simple_p2p.entity;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Known_Users")
public class KnownUsers {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "userHash")
    private String userHash;

    @Column(name = "username")
    private String userName;

    @Column(name = "ipAddress")
    private String ipAddress;

    public KnownUsers() {

    }

    public KnownUsers(String userHash, String userName, String message){
        this.userHash = userHash;
        this.userName = userName;
        this.ipAddress = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return ipAddress;
    }

    public void setMessage(String message) {
        this.ipAddress = message;
    }

    public String getUserHash() {
        return userHash;
    }

    public void setUserHash(String userHash) {
        this.userHash = userHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KnownUsers that = (KnownUsers) o;
        return id == that.id &&
                Objects.equals(userHash, that.userHash) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(ipAddress, that.ipAddress);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userHash, userName, ipAddress);
    }
}
