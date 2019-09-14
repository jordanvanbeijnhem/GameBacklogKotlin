package nl.jordanvanbeijnhem.gamebacklogkotlin.ui.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.jordanvanbeijnhem.gamebacklogkotlin.R
import nl.jordanvanbeijnhem.gamebacklogkotlin.database.GameRepository
import nl.jordanvanbeijnhem.gamebacklogkotlin.model.Game
import java.util.*


class AddActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val gameRepository = GameRepository(application.applicationContext)

    val game = MutableLiveData<Game?>()
    val day = MutableLiveData<Int?>()
    val month = MutableLiveData<Int?>()
    val year = MutableLiveData<Int?>()
    val error = MutableLiveData<String?>()
    val success = MutableLiveData<Boolean>()

    init {
        game.value = Game()
        day.value = 0
        month.value = 0
        year.value = 0
    }

    fun insertGame() {
        if (isGameValid()) {
            game.value!!.releaseDate =
                GregorianCalendar(year.value!!, month.value!! - 1, day.value!!).time
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    gameRepository.insertGame(game.value!!)
                }
                success.value = true
            }
        }
    }

    private fun isGameValid(): Boolean {
        return when {
            game.value == null -> {
                error.value = getApplication<Application>().getString(R.string.error_game_null)
                false
            }
            game.value!!.title.isBlank() -> {
                error.value = getApplication<Application>().getString(R.string.error_tile_empty)
                false
            }
            game.value!!.platform.isBlank() -> {
                error.value = getApplication<Application>().getString(R.string.error_platform_empty)
                false
            }
            day.value!! <= 0 || day.value!! > 31 || month.value!! <= 0 || month.value!! > 12 || year.value!! <= 0 -> {
                error.value = getApplication<Application>().getString(R.string.error_date_invalid)
                false
            }
            else -> true
        }
    }
}