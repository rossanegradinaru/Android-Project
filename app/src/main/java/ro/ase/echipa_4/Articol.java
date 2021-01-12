package ro.ase.echipa_4;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "articole")
public class Articol {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String denumire;
    private int vechime;
    private int pretInitial;

    public Articol(int id, String denumire, int vechime, int pretInitial) {
        this.id = id;
        this.denumire = denumire;
        this.vechime = vechime;
        this.pretInitial = pretInitial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getVechime() {
        return vechime;
    }

    public void setVechime(int vechime) {
        this.vechime = vechime;
    }

    public int getPretInitial() {
        return pretInitial;
    }

    public void setPretInitial(int pretInitial) {
        this.pretInitial = pretInitial;
    }

    @Override
    public String toString() {
        return "Articol{" +
                "id=" + id +
                ", denumire='" + denumire + '\'' +
                ", vechime=" + vechime +
                ", pretInitial=" + pretInitial +
                '}';
    }
}
