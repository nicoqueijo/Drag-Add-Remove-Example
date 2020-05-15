package com.nicoqueijo.dragaddremoveexample

import android.animation.LayoutTransition
import android.os.Bundle
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.jmedeisis.draglinearlayout.DragLinearLayout
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var scrollView: ScrollView
    private lateinit var dragLinearLayout: DragLinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scrollView = findViewById(R.id.scroll_view)
        dragLinearLayout = findViewById<DragLinearLayout>(R.id.drag_linear_layout).apply {
            setContainerScrollView(scrollView)
            setOnViewSwapListener { _, startPosition, _, endPosition ->
                swapElements(startPosition, endPosition)
            }
        }

        addedCurrencies.forEach {
            addRow(it)
        }

        findViewById<FloatingActionButton>(R.id.floating_action_button).setOnClickListener {
            if (removedCurrencies.isNotEmpty()) {
                removedCurrencies.pop().let {
                    addRow(it)
                    addedCurrencies.add(it)
                }
            } else {
                Toast.makeText(this, R.string.no_more_items, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addRow(currency: Currency) {
        CurrencyRow(this).run row@{
            initRow(currency)
            dragLinearLayout.run {
                addView(this@row)
                setViewDraggable(this@row, this@row)
                scrollableArea.setOnLongClickListener {
                    val indexToRemove = indexOfChild(this@row)
                    addedCurrencies.removeAt(indexToRemove).let {
                        removedCurrencies.push(it)
                    }
                    layoutTransition = LayoutTransition()
                    removeDragView(this@row)
                    layoutTransition = null
                    Snackbar.make(this, R.string.item_removed, Snackbar.LENGTH_SHORT)
                        .setAction(R.string.undo) {
                            layoutTransition = LayoutTransition()
                            addDragView(this@row, this@row, indexToRemove)
                            layoutTransition = null
                            addedCurrencies.add(indexToRemove, removedCurrencies.pop())
                        }.show()
                    true
                }
            }
        }
    }

    private fun swapElements(startPosition: Int, endPosition: Int) {
        addedCurrencies.run {
            this[startPosition] = this[endPosition].also {
                this[endPosition] = this[startPosition]
            }
        }
    }

    companion object {
        private val addedCurrencies = mutableListOf(
            Currency("ARS", "Argentine Peso", R.drawable.ars),
            Currency("AUD", "Australian Dollar", R.drawable.aud),
            Currency("BRL", "Brazilian Real", R.drawable.brl),
            Currency("CAD", "Canadian Dollar", R.drawable.cad),
            Currency("CHF", "Swiss Franc", R.drawable.chf),
            Currency("CNY", "Chinese Yuan Renminbi", R.drawable.cny),
            Currency("COP", "Colombian Peso", R.drawable.cop),
            Currency("DKK", "Danish Krone", R.drawable.dkk),
            Currency("EUR", "Euro", R.drawable.eur),
            Currency("GBP", "British Pound", R.drawable.gbp),
            Currency("INR", "Indian Rupee", R.drawable.inr),
            Currency("JPY", "Japanese Yen", R.drawable.jpy),
            Currency("KRW", "South Korean Won", R.drawable.krw),
            Currency("MXN", "Mexican Peso", R.drawable.mxn),
            Currency("SEK", "Swedish Krona", R.drawable.sek),
            Currency("USD", "United States Dollar", R.drawable.usd)
        )

        private val removedCurrencies: Stack<Currency> = Stack()
    }
}
