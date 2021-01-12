package ro.ase.echipa_4;

import java.io.Serializable;

public class Review implements Serializable {
    private String nume;
    private String continut;
    private String uid;

    public Review() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Review(String nume, String continut) {
        this.nume = nume;
        this.continut = continut;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }


    @Override
    public String toString() {
        return "Review{" +
                "nume='" + nume + '\'' +
                ", continut='" + continut + '\'' +
                '}';
    }

    public String getContinut() {
        return continut;
    }

    public void setContinut(String continut) {
        this.continut = continut;
    }
}
