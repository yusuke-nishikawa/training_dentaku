package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var value = 0F
        var ac = false
        var calc = false
        var operator = "no"

        val btnZero: Button = findViewById(R.id.zero)
        val btnOne: Button = findViewById(R.id.one)
        val btnTwo: Button = findViewById(R.id.two)
        val btnThree: Button = findViewById(R.id.three)
        val btnFour: Button = findViewById(R.id.four)
        val btnFive: Button = findViewById(R.id.five)
        val btnSix: Button = findViewById(R.id.six)
        val btnSeven: Button = findViewById(R.id.seven)
        val btnEight: Button = findViewById(R.id.eight)
        val btnNine: Button = findViewById(R.id.nine)
        val btnAdd: Button = findViewById(R.id.add)   // +
        val btnSub: Button = findViewById(R.id.sub)   // ×
        val btnMul: Button = findViewById(R.id.mul)   // -
        val btnDiv: Button = findViewById(R.id.div)   // ÷
        val btnPer: Button = findViewById(R.id.per)   // %
        val btnEqual: Button = findViewById(R.id.equal)  // =
        val btnAc: Button = findViewById(R.id.ac) // AC
        val btnComma: Button = findViewById(R.id.comma) // .
        val btnCh: Button = findViewById(R.id.ch) // プラマイ
        val resultView: TextView = findViewById(R.id.result) // 結果

        // 小数第2以下は銀行丸め
        fun resultRound(result: Float): Float {
            val roundOff = (result * 100.0).roundToInt() / 100.0
            return roundOff.toFloat()
        }

        //計算
        fun calculation(op: String): Float {
            val result = resultView.text.toString().toFloat()
            return if (op == "+") {
                value + result
            } else if (op == "-") {
                value - result
            } else if (op == "*") {
                resultRound(value * result)
            } else if (op == "/") {
                resultRound(value / result)
            } else {
                result
            }
        }
        //末尾文字が0なら除去
        fun excludeStrEndZero(resultTxt: String): String {
            val regex = Regex(".0+\$")
            return regex.replace(resultTxt, "")
        }
        //数字ボタン
        fun numBtnAction(num: String) {
            val result = resultView.text.toString()
            resultView.text = if (result != "0" && !ac) {
                result + num
            } else {
                ac = false
                num
            }
            calc = true
        }
        //％ボタン
        fun perBtnAction() {
            val result = (resultView.text.toString().toFloat()) * 0.01
            resultView.text = result.toString()
        }

        //=ボタン
        fun equalBtnAction() {
            if (calc) {
                value = calculation(operator)
                calc = false
                ac = true
                resultView.text = excludeStrEndZero(value.toString())
                operator = "no"
            }
        }
        //ACボタン
        fun acBtnAction() {
            resultView.text = "0"
            value = 0F
            operator = "no"
            ac = false
            calc = false
        }

        //+-ボタン
        fun chBtnAction() {
            val result = (-resultView.text.toString().toFloat()).toString()
            resultView.text = excludeStrEndZero(result)
        }

        //計算実行
        fun calcBtnAction(op: String) {
            if (calc) {
                value = calculation(operator)
                ac = true
                calc = false
                resultView.text = excludeStrEndZero(value.toString())
            }
            operator = op
        }

        btnZero.setOnClickListener {
            numBtnAction("0")
        }
        btnOne.setOnClickListener {
            numBtnAction("1")
        }
        btnTwo.setOnClickListener {
            numBtnAction("2")
        }
        btnThree.setOnClickListener {
            numBtnAction("3")
        }
        btnFour.setOnClickListener {
            numBtnAction("4")
        }
        btnFive.setOnClickListener {
            numBtnAction("5")
        }
        btnSix.setOnClickListener {
            numBtnAction("6")
        }
        btnSeven.setOnClickListener {
            numBtnAction("7")
        }
        btnEight.setOnClickListener {
            numBtnAction("8")
        }
        btnNine.setOnClickListener {
            numBtnAction("9")
        }
        btnAdd.setOnClickListener {
            calcBtnAction("+")
        }
        btnSub.setOnClickListener {
            calcBtnAction("-")
        }
        btnMul.setOnClickListener {
            calcBtnAction("*")
        }
        btnDiv.setOnClickListener {
            calcBtnAction("/")
        }
        btnEqual.setOnClickListener {
            equalBtnAction()
        }
        btnAc.setOnClickListener {
            acBtnAction()
        }
        btnCh.setOnClickListener {
            chBtnAction()
        }
        btnPer.setOnClickListener {
            perBtnAction()
        }
        btnComma.setOnClickListener {
            val isExist = resultView.text.toString().contains(".")
            if (!isExist) { // コンマが既にあればnumBtnActionは読まない
                if (resultView.text.toString() == "0") {
                    numBtnAction("0.")
                } else {
                    numBtnAction(".")
                }
            }
        }
    }
}
