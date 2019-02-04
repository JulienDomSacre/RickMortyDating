package com.juliensacre.rickmortydating

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.juliensacre.rickmortydating.characterdetail.CharacterDetailFragment
import com.juliensacre.rickmortydating.characters.CharactersFragment
import com.juliensacre.rickmortydating.util.RxBus
import com.juliensacre.rickmortydating.util.RxEvent
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {

    private lateinit var rxEventDisposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CharactersFragment.newInstance())
                .commitNow()
        }

        rxEventDisposable = RxBus.listen(RxEvent.CharacterSelected::class.java).subscribe {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CharacterDetailFragment.newInstance(it.id))
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!rxEventDisposable.isDisposed) rxEventDisposable.dispose()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                val backStackEntryCount = supportFragmentManager.backStackEntryCount

                if (backStackEntryCount > 0) {

                    supportFragmentManager.popBackStack()

                }

                return true
            }
        }
        return false
    }
}
