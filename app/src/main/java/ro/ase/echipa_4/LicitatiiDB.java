package ro.ase.echipa_4;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.os.Bundle;
@Database(entities={Licitatie.class,Articol.class},version=1,exportSchema=false)
public abstract class LicitatiiDB extends RoomDatabase {

    private final static String DB_NAME="licitatii.db";
    private static LicitatiiDB instanta;

    public static LicitatiiDB getInstanta(Context context)
    {
        if(instanta==null)
            instanta= Room.databaseBuilder(context,LicitatiiDB.class,DB_NAME)
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        return instanta;
    }

    public abstract LicitatiiDao getLicitatieDao();
    public abstract ArticolDao getArticolDao();

}