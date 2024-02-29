package com.example.tictactoekotlin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textViewPlayer1: TextView
    private lateinit var textViewPlayer2: TextView
    private lateinit var buttons: Array<Array<Button?>>
    private lateinit var resetButton: Button

    private var player1Turn = true
    private var roundCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewPlayer1 = findViewById(R.id.textView)
        textViewPlayer2 = findViewById(R.id.textView2)
        resetButton = findViewById(R.id.button_reset)

        buttons = Array(3) { row ->
            Array(3) { col ->
                val buttonId = resources.getIdentifier("button$row$col", "id", packageName)
                findViewById<Button>(buttonId).apply {
                    setOnClickListener { onButtonClick(this) }
                }
            }
        }

        resetButton.setOnClickListener {
            resetGame()
        }
    }

    private fun onButtonClick(view: View) {
        val button = view as Button

        if (button.text.toString() == "") {
            if (player1Turn) {
                button.text = "X"
            } else {
                button.text = "O"
            }

            roundCount++
            if (checkForWinner()) {
                if (player1Turn) {
                    player1Wins()
                } else {
                    player2Wins()
                }
            } else if (roundCount == 9) {
                draw()
            } else {
                player1Turn = !player1Turn
                updateTurnText()
            }
        }
    }

    private fun checkForWinner(): Boolean {
        val board = Array(3) { arrayOfNulls<String>(3) }

        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = buttons[i][j]?.text.toString()
            }
        }

        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != "") {
                return true
            }
            if (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != "") {
                return true
            }
        }

        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != "") {
            return true
        }

        return board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != ""
    }

    private fun player1Wins() {
        showResult("Player 1 Wins!")
    }

    private fun player2Wins() {
        showResult("Player 2 Wins!")
    }

    private fun draw() {
        showResult("It's a Draw!")
    }

    private fun showResult(result: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Game Over")
        builder.setMessage(result)
        builder.setPositiveButton("Restart") { _, _ ->
            resetGame()
        }
        builder.setNegativeButton("Exit") { _, _ ->
            finish()
        }
        builder.setCancelable(false)
        builder.show()
    }

    private fun resetGame() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j]?.text = ""
            }
        }

        player1Turn = true
        roundCount = 0
        updateTurnText()
    }

    private fun updateTurnText() {
        if (player1Turn) {
            textViewPlayer1.text = "Player 1 (X) Turn"
            textViewPlayer2.text = ""
        } else {
            textViewPlayer1.text = ""
            textViewPlayer2.text = "Player 2 (O) Turn"
        }
    }
}



