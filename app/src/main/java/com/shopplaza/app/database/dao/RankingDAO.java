package com.shopplaza.app.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.shopplaza.app.model.Ranking;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.ABORT;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface RankingDAO {


    @Insert(onConflict = REPLACE)
    public void insertRanking(Ranking ranking);


    @Insert(onConflict = REPLACE)
    public void insertAllRankings(List<Ranking> rankings);

    @Query("SELECT * FROM Ranking")
    List<Ranking> getRankings();
}
