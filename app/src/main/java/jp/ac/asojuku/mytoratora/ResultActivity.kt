package jp.ac.asojuku.mytoratora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import jp.ac.asojuku.mytoratora.R
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    val tora = 0;
    val oba = 1;
    val kiyomasa =2;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
    }

    override fun onResume() {
        super.onResume()
        val id = intent.getIntExtra("MY_HAND",0)
        val myHand: Int
        myHand = when(id){
            R.id.ToraButton->{playerImage.setImageResource(R.drawable.animalface_tora);tora}
            R.id.ObaButton ->{playerImage.setImageResource(R.drawable.koshi_magari_smile_obaasan); oba}
            R.id.KiyomasaButton ->{playerImage.setImageResource(R.drawable.nigaoe_katoukiyomasa); kiyomasa}

            else-> tora
        }
        val comHand = getHand()
        when(comHand){
            tora ->comImage.setImageResource(R.drawable.animalface_tora);
            oba ->comImage.setImageResource(R.drawable.koshi_magari_smile_obaasan);
            kiyomasa ->comImage.setImageResource(R.drawable.nigaoe_katoukiyomasa);

        }
        val gameResult = (comHand - myHand + 3) % 3
        when(gameResult){
            0 ->resultLabel.setText(R.string.result_draw)
            1 ->resultLabel.setText(R.string.result_win)
            2 ->resultLabel.setText(R.string.result_lose)

        }
        when(gameResult){
            0 ->resultImage.setImageResource(R.drawable.maiko_ozashiki_asobi_toratora)
            1 ->resultImage.setImageResource(R.drawable.dance_yorokobi_mai_woman)
            2 ->resultImage.setImageResource(R.drawable.smartphone_yukata_woman)

        }
        backButton.setOnClickListener{ finish()}
        this.saveDate(myHand,comHand,gameResult)
    }
    private fun saveDate(myHand:Int, comHand:Int,gameResult:Int){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val gameCount = pref.getInt("GAME_COUNT",0)
        val winningStreakCount = pref.getInt("WINNING_STREAK_COUNT",0)
        val lastComHand = pref.getInt("LAST_COM_HAND",0);
        val lastGameResult = pref.getInt("LAST_GAME_RESULT",-1)

        val editWinningStreakCount = when{
            lastGameResult == 2 && gameResult == 2-> (winningStreakCount+1)
            else -> 0
        }
        val editor = pref.edit();
        editor.putInt("GAME_COUNT",gameCount+1)
            .putInt("WINNING_STREAK_COUNT",editWinningStreakCount)
            .putInt("LAST_MY_HAND",myHand)
            .putInt("LAST_COM_HAND",comHand)
            .putInt("BEFORE_LAST_COM_HAND",lastComHand)
            .putInt("GAME_RESULT",gameResult)
            .apply()
    }
    private fun getHand():Int{
        var hand = (Math.random() * 3).toInt()
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val gameCount = pref.getInt("GAME_COUNT",0)
        val winningStreakCount = pref.getInt("WINNING_STREAK_COUNT",0)
        val lastMyHand = pref.getInt("LAST_MY_HAND",0)
        val lastComHand = pref.getInt("LAAST_COM_HAND",0)
        val beforeLastComHand = pref.getInt("BEFORE_LAST_COM_HAND",0)
        val gameResult = pref.getInt("GAME_RESULT",-1)

        if(gameCount ==1) {
            if (gameResult == 2) {
                while (lastComHand == hand) {
                    hand = (Math.random() * 3).toInt()
                }
            } else if (gameResult == 1) {
                hand = (lastMyHand - 1 + 3) % 3
            }
        }else if(winningStreakCount > 0){
            if(beforeLastComHand == lastComHand){
                while(lastComHand == hand) {
                    hand = (Math.random() * 3).toInt()
                }
            }
        }
        return hand
    }
}

