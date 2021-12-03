package com.example.s092512_lykkehjul

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random


class SecondActivity : AppCompatActivity() {

    var totalLives = 5
    lateinit var randomWord: String
    lateinit var randomWordDisplay: String
    var randomWordDashes: MutableList<GuessChar> = ArrayList()
    lateinit var tvTotalLives: TextView
    lateinit var guessEditBox: EditText
    lateinit var spinButton: Button
    lateinit var guessButton: Button
    lateinit var wheel : ImageView
    lateinit var rvRandomWord: RecyclerView
    var randomWords = Data().loadRandomWords()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        guessEditBox = findViewById(R.id.etGuess)
        spinButton = findViewById(R.id.spin)
        guessButton = findViewById(R.id.btnGuess)
        wheel = findViewById(R.id.wheel)
        tvTotalLives = findViewById(R.id.total_lives)
        tvTotalLives.text = totalLives.toString()

        randomWord = randomWords.random();
        randomWordDisplay = randomWord
        var randomWordChars = randomWord.toCharArray()
        for(i in randomWordChars) {
            if(i.equals(' ')) {
                randomWordDashes.add(GuessChar(' ', true))
            } else {
                randomWordDashes.add(GuessChar( '_', false))
            }
        }

        rvRandomWord = findViewById(R.id.rvRandomWord)
        rvRandomWord.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        rvRandomWord.setHasFixedSize(true)
        rvRandomWord.adapter = ItemAdapter(randomWordDashes)

        spin()
    }



    fun guessClickHandler(v: View){
        var guess = guessEditBox.text.trim()
        if(guess.isEmpty()){
            return
        }

        var guessedChar = guess.get(0)

        guessButton.isEnabled = false
        if(!randomWordDisplay.contains(guessedChar, true)){
            // spin again
            totalLives--
            tvTotalLives.text = totalLives.toString()

            if(totalLives === 0){
                spinButton.isEnabled = false

            }

            return
        }

        var randomWordChars = randomWord.toCharArray()
        for((i, item) in randomWordChars.withIndex()){
            var word = randomWordDashes.get(i)
            if(item === guessedChar && word.isGuessed){
                Toast.makeText(this,"Letter already used: " + guessedChar, Toast.LENGTH_SHORT).show()
                continue
            }


            if(item === guessedChar){
                word.char = item
                word.isGuessed = true
            }
        }

        //update view with new guess chars
        rvRandomWord.adapter = ItemAdapter(randomWordDashes)

        //check if the whole random word is guessed
        var stillRemainingLetters = randomWordDashes.any { !it.isGuessed }
        if(!stillRemainingLetters){
            //user guessed the whole word correctly
            spinButton.isEnabled = false
            Toast.makeText(this,"Success", Toast.LENGTH_LONG).show()
            //TODO show fragment here..............
        }
    }

    fun spin() {
        spinButton.setOnClickListener() {
            var rotateby = Random.nextInt(0, 7)
            var animator = wheel.animate()
            animator.rotationBy(-(720 + (rotateby) * 15).toFloat())
            animator.interpolator = DecelerateInterpolator(1.0f)
            animator.duration = 3200
            animator.start()
            animator.setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(a: Animator?) {
                    guessButton.isEnabled = false
                }
                override fun onAnimationRepeat(a: Animator?) {
                }
                override fun onAnimationEnd(a: Animator?) {
                    guessButton.isEnabled = true
                }
                override fun onAnimationCancel(a: Animator?) {
                }
            })
        }

    }

    fun wheelState() {
       val wheelItem = Data().loadPoints()
        Toast.makeText(applicationContext,""+ wheelItem,Toast.LENGTH_SHORT).show()


    }
}