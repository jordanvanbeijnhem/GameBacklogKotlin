package nl.jordanvanbeijnhem.gamebacklogkotlin.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import nl.jordanvanbeijnhem.gamebacklogkotlin.model.Game

@Dao
interface GameDao {

    @Query("SELECT * FROM Game ORDER BY releaseDate ASC")
    fun getAllGames(): LiveData<List<Game>>

    @Insert
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Query("DELETE FROM Game")
    suspend fun deleteAllGames()
}