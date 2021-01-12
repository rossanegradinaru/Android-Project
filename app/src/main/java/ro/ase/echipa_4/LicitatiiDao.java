package ro.ase.echipa_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import android.os.Bundle;

import java.util.List;

@Dao
public interface LicitatiiDao{

    @Insert
    void insert(Licitatie l);

    @Insert
    void insert(List<Licitatie> licitatii);

    @Query("select * from licitatii")
    List<Licitatie> getAll();

    @Query("delete from licitatii")
    void deleteAll();

    @Delete
    void deleteLicitatie(Licitatie l);

    @Query("update licitatii set nume=:nume where prenume=:prenume")
    int updateLicitatii(String prenume,String nume);

}
