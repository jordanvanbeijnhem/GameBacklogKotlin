package nl.jordanvanbeijnhem.gamebacklogkotlin.ui.add

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import nl.jordanvanbeijnhem.gamebacklogkotlin.R

class AddActivity : AppCompatActivity() {

    private lateinit var viewModel: AddActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        fab.setOnClickListener {
            onSaveClicked()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AddActivityViewModel::class.java)
        viewModel.error.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        viewModel.success.observe(this, Observer { success ->
            if (success) finish()
        })
    }

    private fun onSaveClicked() {
        viewModel.game.value?.apply {
            title = etTitle.text.toString()
            platform = etPlatform.text.toString()
        }
        viewModel.day.value =
            if (etDay.text.toString().isNotBlank()) etDay.text.toString().toInt() else 0
        viewModel.month.value =
            if (etMonth.text.toString().isNotBlank()) etMonth.text.toString().toInt() else 0
        viewModel.year.value =
            if (etYear.text.toString().isNotBlank()) etYear.text.toString().toInt() else 0

        viewModel.insertGame()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
