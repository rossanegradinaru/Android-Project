package ro.ase.echipa_4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ArticolDao {
    @Insert
    void insert(Articol articol);

    @Query("select * from articole")
    List<Articol> getAll();

    @Query("delete from articole")
    void deleteAll();

    @Delete
    void deleteArticol(Articol articol);

    @Query("update articole set pretInitial=:pretInitial where denumire=:denumire")
    int updateArticole(int pretInitial,String denumire);


}
