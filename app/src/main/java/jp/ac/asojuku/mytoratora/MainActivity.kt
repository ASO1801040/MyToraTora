package jp.ac.asojuku.mytoratora

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit {
            clear()
        }
    }

    override fun onResume() {
        super.onResume()
        //ボタンがクリックされたら処理を呼び出し
        ToraButton.setOnClickListener{ onJankenButtonTapped(it) }
        ObaButton.setOnClickListener { onJankenButtonTapped(it) }
        KiyomasaButton.setOnClickListener { onJankenButtonTapped(it) }

    }
    fun onJankenButtonTapped(view : View?){
        //ボタンがクリックされたら呼び出される処理
        val intent = Intent(this, ResultActivity::class.java);
        intent.putExtra("MY_HAND",view?.id)
        startActivity(intent);

    }
}
