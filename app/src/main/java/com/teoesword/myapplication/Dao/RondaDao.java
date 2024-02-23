package com.teoesword.myapplication.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.teoesword.myapplication.Databases.RondaEntity;

import java.util.List;

@Dao
public interface RondaDao {


    @Query("SELECT descripcion_cml FROM ronda")
    List<String> getDescripcionCmlList();
}

