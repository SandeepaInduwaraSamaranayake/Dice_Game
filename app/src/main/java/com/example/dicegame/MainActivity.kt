/**
 * Video Demonstration link - https://drive.google.com/drive/folders/1d_c1nQcAawll3Ju_W_hw1bnQUabjBJFq?usp=sharing
 * @author Sandeepa Induwara Samaranayake
 * IIT ID - 20210302
 * UOW ID - W1867067
 * L5-SE
 */

package com.example.dicegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnAbout = findViewById<Button>(R.id.btnAbout)
        var btnNewGame = findViewById<Button>(R.id.btnNewGame)

        // setting onclick listener to new game button
        btnNewGame.setOnClickListener {
            val gameIntent = Intent(this, GameActivity::class.java)
            startActivity(gameIntent)
        }

        // setting onclick listener to about button
        btnAbout.setOnClickListener{
            showPopupWindow(btnAbout)
        }
    }

    /**
     * this method will show the popup window.
     */
    private fun showPopupWindow(view: View?)
    {
        //  The getSystemService method is called with the parameter LAYOUT_INFLATER_SERVICE, which
        //  retrieves the LayoutInflater service. The method returns an Any object, which is then
        //  cast to a LayoutInflater object using the as keyword and assigned to the variable inflater.
        //  This allows you to use the inflater object to inflate layout files into view objects.
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup, null)

        // set the dimensions of the popup window.
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT

        // set the focus of the popup window, when it appears.
        val focus = true

        // create a PopupWindow object using above parameters.
        val popupWindow = PopupWindow(popupView, width, height, focus)

        // setting the location where the popup window should appear.
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        // finally define our cancel button as a kotlin object and set onclick listener to close
        // the the popup window when cancel button is clicked.
        val cancelButton = popupView.findViewById<Button>(R.id.cancel_button)
        cancelButton.setOnClickListener {
            // close the popup window.
            popupWindow.dismiss()
        }
    }
}

