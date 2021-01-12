package ro.ase.echipa_4;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

//enum Articol{GEANTA,BLUZA,PALARIE,BALERINI}


@Entity(tableName="licitatii")
public class Licitatie implements Serializable{

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   @PrimaryKey(autoGenerate = true)
  private int id;

   public String getUid() {
      return uid;
   }

   public void setUid(String uid) {
      this.uid = uid;
   }

   @Ignore
 private String uid;

   private String nume;
   private String prenume;
   private String email;
   public String articol;
   private int sumaLicitata;
   private String data;

   public Licitatie(String nume, String prenume, String email, String articol, int sumaLicitata, String data) {
      this.nume = nume;
      this.prenume = prenume;
      this.email = email;
      this.articol = articol;
      this.sumaLicitata = sumaLicitata;
      this.data = data;
   }

   public String getNume() {
      return nume;
   }

   public void setNume(String nume) {
      this.nume = nume;
   }

   public String getPrenume() {
      return prenume;
   }

   public void setPrenume(String prenume) {
      this.prenume = prenume;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getArticol() {
      return articol;
   }

   public void setArticol(String articol) {
      this.articol = articol;
   }
   public int getSumaLicitata() {
      return sumaLicitata;
   }
   public void setSumaLicitata(int sumaLicitata) {
      this.sumaLicitata = sumaLicitata;
   }

   public String getData() {
      return data;
   }

   public void setData(String data) {
      this.data = data;
   }

   @Override
   public String toString() {
      return "Licitatie{" +
              "nume='" + nume + '\'' +
              ", prenume='" + prenume + '\'' +
              ", email='" + email + '\'' +
              ", articol=" + articol +
              ", sumaLicitata=" + sumaLicitata +
              ", data=" + data +
              '}';
   }
}
