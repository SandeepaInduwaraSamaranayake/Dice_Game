package com.example.dicegame


import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import kotlin.collections.ArrayList
import kotlin.random.Random

class GameActivity : AppCompatActivity()
{
    // create a bundle object to pass the data between landscape mode and portrait mode.
    private var bundle = Bundle()

    // create an Button ArrayList to hold the dice buttons belong to computer.
    private val imgViewsOfComputer = ArrayList<ImageView>()

    // create an Button ArrayList to hold the dice buttons belong to user.
    private val imgViewsOfUser = ArrayList<ImageView>()

    // create mutable list to hold scores.
    private var lastScores = MutableList(size = 2) { 0 }

    // create mutable list to hold computer score values.
    private var computerScores = MutableList(size = 5) { 0 }

    // create mutable list to hold computer score values.
    private var userScores = MutableList(size = 5) { 0 }

    // create variable to hold the target value.
    private var target: Int = 101

    // create variable to hold the no of attempts of throwing.
    private var noOfAttempts: Int = 0

    // create a variable to hold no of wins.
    private var noOfWins: Int = 0

    // create a variable to hold no of loses.
    private var noOfLoses: Int = 0

    // create a boolean mutable list to hold whether the dice is selected for reRoll or not.
    // initialize all elements to false using a lambda function { false }.
    private var imageViewSelected = MutableList(5) { false }


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // get buttons and text views as kotlin objects.
        val btnThrow = findViewById<Button>(R.id.btnThrow)
        val btnScore = findViewById<Button>(R.id.btnScore)
        val txtViewComputer = findViewById<TextView>(R.id.txtComputer)
        val txtViewYou = findViewById<TextView>(R.id.txtYou)
        val txtTarget = findViewById<TextView>(R.id.txt_target)
        val txtWins = findViewById<TextView?>(R.id.txtWins)

        // adding dice buttons belong to computer to imgButtonsComputer list.
        imgViewsOfComputer.add(findViewById<ImageView>(R.id.imgBtn1_1))
        imgViewsOfComputer.add(findViewById<ImageView>(R.id.imgBtn1_2))
        imgViewsOfComputer.add(findViewById<ImageView>(R.id.imgBtn1_3))
        imgViewsOfComputer.add(findViewById<ImageView>(R.id.imgBtn2_1))
        imgViewsOfComputer.add(findViewById<ImageView>(R.id.imgBtn2_2))

        // adding dice buttons belong to user to imgButtonsUser list.
        imgViewsOfUser.add(findViewById<ImageButton>(R.id.imgBtn3_1))
        imgViewsOfUser.add(findViewById<ImageButton>(R.id.imgBtn3_2))
        imgViewsOfUser.add(findViewById<ImageButton>(R.id.imgBtn4_1))
        imgViewsOfUser.add(findViewById<ImageButton>(R.id.imgBtn4_2))
        imgViewsOfUser.add(findViewById<ImageButton>(R.id.imgBtn4_3))

        // loading all old data from the savedInstanceState
        if (savedInstanceState != null)
        {
            bundle = savedInstanceState.getBundle("data_bundle")!!

            // Get values from bundle object and update the local values.
            target = bundle.getInt("target")
            updateTarget(txtTarget)

            noOfAttempts = bundle.getInt("noOfAttempts")
            noOfWins = bundle.getInt("noOfWins")
            noOfLoses = bundle.getInt("noOfLoses")

            // set noOfWins and noOfLoses to txtview
            txtWins.text = "H: $noOfWins / c: $noOfLoses"

            // Get lastScores mutable list  from bundle object and update score text views
            lastScores = bundle.getIntegerArrayList("arrayListLastScores")!!.toMutableList()
            updateScore(txtViewComputer, txtViewYou, txtWins)

            computerScores = bundle.getIntegerArrayList("arrayListComputerScores")!!.toMutableList()
            userScores = bundle.getIntegerArrayList("arrayListUserScores")!!.toMutableList()
            imageViewSelected = bundle.getBooleanArray("arrayListImageViewSelected")!!.toMutableList()

            // this will restore the old dice images
            restoreDiceImages()
            // this will restore the selected dices
            restoreSelections()
            writeVariableLog()
        }

        // set onclick listener to the btnScore.
        btnScore.setOnClickListener {
            btnScore.isEnabled = false
            // if number of attempts is not divisible by 3,
            // set no of attempts to default value
            updateScore(txtViewComputer, txtViewYou, txtWins)
            writeVariableLog()
        }

        // set onclick listener to the btnThrow.
        btnThrow.setOnClickListener {
                noOfAttempts++
                btnScore.isEnabled = true

                // check whether images are selected or not.
                // if images are selected make isSelected to true. else false.
                var isSelected = false
                for (i in imageViewSelected)
                    if (i) isSelected = true

                // if isSelected is true. reRoll dices.
                // if isSelected is false. set random images to both sides.
                if (isSelected ) {
                    reRoll()
                }
                else
                    setRandomImageToBothSides()

            // check the number of attempts.
            // if no of attempts > 3 automatically call to update score.
            if ( noOfAttempts % 3 == 0 )
            {
                btnScore.isEnabled = false
                updateScore(txtViewComputer, txtViewYou, txtWins)
                writeVariableLog()

                // clearing the borders
                for( ( index, img ) in imgViewsOfUser.withIndex())
                {
                    imageViewSelected[index] = false
                    img.background = null
                }
            }

            // if the game ties give them another one chance to win.
            if( lastScores[0] == lastScores[1] && lastScores[0] >= target && noOfAttempts % 3 != 0 )
            {
                Toast.makeText(this, "The Game Tied. This will Continue Until the Tie is Broken", Toast.LENGTH_SHORT).show()
                setRandomImageToBothSides()
            }
            writeVariableLog()
        }

        // set onclick listener to the txtTarget.
        txtTarget.setOnClickListener {
            if( noOfAttempts == 0 )
            {
                setTarget(txtTarget)
            }
        }

        // setting onclick listener to all the image views at once.
        for ( (index, imgView) in imgViewsOfUser.withIndex() )
        {
            imgView.setOnClickListener {

                // creating a border using drawable/border.xml
                val border: Drawable =
                    ResourcesCompat.getDrawable(resources, R.drawable.border, null)!!

                // check whether is there any background(border) available in the imageview.
                if (imgView.background == null)
                {
                    // if a background(border) is not available for image view now, set background (border)
                    imgView.background = border

                    // when the user clicked the dices to select them, make the corresponding index of the ImageViewSelected to true.
                    imageViewSelected[index] = true
                }
                else
                {
                    // if a background(border) is available for image view now, then remove the background (border)
                    imgView.background = null
                    // remove selection state.
                    imageViewSelected[index] = false
                }
            }
        }
    }

    /**
     * overriding the onSaveInstanceState() method
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // convert our Mutable lists to ArrayLists
        val arrayListLastScores: ArrayList<Int> = lastScores.toCollection(ArrayList())
        val arrayListComputerScores: ArrayList<Int> = computerScores.toCollection(ArrayList())
        val arrayListUserScores: ArrayList<Int> = userScores.toCollection(ArrayList())

        // put Int data to bundle object
        bundle.putInt("target", target)
        bundle.putInt("noOfAttempts",noOfAttempts)
        bundle.putInt("noOfWins",noOfWins)
        bundle.putInt("noOfLoses",noOfLoses)

        // put ArrayLists to bundle object
        bundle.putIntegerArrayList("arrayListLastScores", arrayListLastScores)
        bundle.putIntegerArrayList("arrayListComputerScores", arrayListComputerScores)
        bundle.putIntegerArrayList("arrayListUserScores", arrayListUserScores)
        bundle .putBooleanArray("arrayListImageViewSelected", imageViewSelected.toBooleanArray())

        // Save bundle object to saved instance state
        outState.putBundle("data_bundle", bundle)
    }

    /**
     * randomNumGenerator will generate a random number between given start and end.
     */
    private fun randomNumGenerator(start: Int, end: Int): Int
    {
        // return random number between start and end.
        return (start..end).random()
    }

    /**
     * set random dice images to computer side only.
     */
    private fun setRandomImagesToComputer(imgViewList: List<ImageView>)
    {
        for ((index, imgView) in imgViewList.withIndex()) {
            // load random image to the ImageView and get the score of that dice to generatedRandomNum.
            var generatedRandomNum = setRandomImage(imgView)
            // put the generatedRandomNum variable to the computer score list.
            computerScores[index] = generatedRandomNum
        }
        Log.d("throw click", computerScores.toString())
    }

    /**
     * set random dice images to user side only.
     */
    private fun setRandomImagesToUser(imgViewList: List<ImageView>)
    {
        for ((index, imgView) in imgViewList.withIndex())
        {
            // load random image to the ImageView and get the score of that dice to generatedRandomNum.
            var generatedRandomNum = setRandomImage(imgView)
            // put the generatedRandomNum variable to the user score list.
            userScores[index] = generatedRandomNum
        }
        Log.d("throw click", userScores.toString())
    }

    /**
     * method to set image to a single image view.
     */
    private fun setRandomImageToAnImageView(index: Int, imgView: ImageView)
    {
        // setting random dice and getting values on the dice face.
        var generatedRandomNum = setRandomImage(imgView)
        // put the generatedRandomNum variable to the user score list.
        userScores[index] = generatedRandomNum
    }

    /**
     * set random dice images to both computer side and user side.
     */
    private fun setRandomImageToBothSides()
    {
        // set random dices to computer side.
        setRandomImagesToComputer(imgViewsOfComputer)
        // setting random dices to user.
        setRandomImagesToUser(imgViewsOfUser)
    }

    /**
     * this method will set a random image to a given ImageView.
     */
    private fun setRandomImage(imgView: ImageView): Int
    {
        // generating a random number between 1 to 6.
        val randomNum = randomNumGenerator(1, 6)
        // getting resource id
        val resourceId = resources.getIdentifier(
            "dice_$randomNum", "drawable", "com.example.dicegame"
        )
        // setting image to the ImageView.
        imgView.setImageResource(resourceId)

        // return generated random number.
        return randomNum
    }

    /**
     * this method will update the both computer score and user's score.
     */
    private fun updateScore(txtViewComputer: TextView, txtViewYou: TextView, txtWins: TextView)
    {
        // getting sum score for both sides
        val computerScoreSum = computerScores.sum()
        val userScoreSum = userScores.sum()

        lastScores[0] += computerScoreSum
        lastScores[1] += userScoreSum

        txtViewComputer.text = "Computer - " + lastScores[0].toString()
        txtViewYou.text = "You - " + lastScores[1].toString()

        // this will call the isExceedingTarget() method
        if( isExceedingTarget() == 2)
        {
            Log.d("isExceedingTarget() return", isExceedingTarget().toString() )
            showWinOrLose("You win!", true)
            // increment noOfWins
            noOfWins++
            // update noOfWinsLoses on the screen
            updateNoOfWinsLoses(txtWins)
        }
        else if(isExceedingTarget() == 0)
        {
            showWinOrLose("You lose",false)
            // increment noOfLoses
            noOfLoses++
            // update noOfLosesLoses on the screen
            updateNoOfWinsLoses(txtWins)
        }
        else if(isExceedingTarget() == 1)
        {
            //activateTieGame()
        }
        writeVariableLog()
    }

    /**
     * this method will reRoll the dices which are not selected by the user to hold.
     */
    private fun reRoll() {
        for ((index, selected) in imageViewSelected.withIndex()) {
           //Log.d("reRoll dice", "$selected $index")
            if (!selected) {
                setRandomImageToAnImageView(index, imgViewsOfUser[index])
            }
        }
    }

    /**
     * this method will set the target.
     */
    private fun setTarget(txtTarget: TextView)
    {
        // define a PopupWindow object
        lateinit var popup: PopupWindow

        // inflate the layout file
        val popupView = layoutInflater.inflate(R.layout.set_target_popup, null)

        // set up the popup window
        popup = PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true)

        // display the popup window
        popup.showAtLocation(popupView, Gravity.CENTER, 0, 0)

        // retrieve the input
        val confirmButton = popupView.findViewById<Button>(R.id.confirmButton)

        confirmButton.setOnClickListener {
            val editText = popupView.findViewById<EditText>(R.id.editText)
            val input = editText.text.toString().toInt()
            // set the input to the target.
            target = input
            updateTarget(txtTarget)
            popup.dismiss()
        }

        val cancelButton = popupView.findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener {
            popup.dismiss()
        }
    }

    /**
     * set updated target to the txtview
     */
    private fun updateTarget(txtTarget: TextView)
    {
        txtTarget.text = "Target - $target\uD83D\uDCDD"
    }

    /**
     * this method will update the nOfWins
     */
    private fun updateNoOfWinsLoses(txtNoOfWins: TextView)
    {
        txtNoOfWins.text = "H: $noOfWins / c: $noOfLoses"
    }

    /**
     * this method will check whether the sum of scores is exceeding the target
     */
    private fun isExceedingTarget() : Int
    {
        // getting computer's and user's
        var compScore: Int = lastScores[0]
        var userScore: Int = lastScores[1]

        if( compScore > userScore && compScore >= target )
        {
            Log.d("Computer win status", "The computer won the match")
            return 0  // if computer wins returns 0
        }
        else if( compScore == userScore && compScore >= target )
        {
            Log.d("Game is a tie", "You need to play another one round")
            return 1 // if game ties return 1
        }
        else if ( userScore > compScore && userScore >= target)
        {
            Log.d("User win status", "You win the game")
            return 2 // if user wins returns 2
        }
        return -1 // if nothing is executed, return -1
    }

    /**
     * show win or lose message.
     */
    private fun showWinOrLose(message: String, state: Boolean)
    {
        // define a PopupWindow object
        lateinit var popup: PopupWindow

        // inflate the layout file
        val popupView = layoutInflater.inflate(R.layout.win_lose_popup, null)

        // set up the popup window
        popup = PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true)

        // display the popup window
        popup.showAtLocation(popupView, Gravity.CENTER, 0, 0)

        // the game is not playable anymore, so disable the game window.
        disableBackground()

        val cancelButton = popupView.findViewById<Button>(R.id.close_button)
        cancelButton.setOnClickListener {
            // when cancel btn is clicked, show initial new game, about screen.
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            // close the popup window.
            popup.dismiss()
        }

        // get the text view which supposed to show "You win" or "You lose" as a kotlin object.
        val txtState = popupView.findViewById<TextView>(R.id.message_text)
        // set text to the text view.
        txtState.text = message
        if(state)
            // if state is true, it means user won the game. set text view text color to green.
            txtState.setTextColor(ContextCompat.getColor(this, R.color.green))
        else
            // if state is false, it means user lose the game. set text view text color to red.
            txtState.setTextColor(ContextCompat.getColor(this, R.color.red))
    }

    /**
     * this method will disable the game window
     */
    private fun disableBackground()
    {
        // Disable user interactions
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    /**
     * this method will create a log of all data in the program
     */
    private fun writeVariableLog()
    {
        Log.d("++++++++++", "++++++++++++++")
        Log.d("lastScores", lastScores.toString())
        Log.d("computerScores", computerScores.toString())
        Log.d("userScores", userScores.toString())
        Log.d("target", target.toString())
        Log.d("attempts", noOfAttempts.toString())
        Log.d("imageViewSelected", imageViewSelected.toString())
        Log.d("++++++++++SCORE LOGS", "++++++++++++++")
        Log.d("computer scores", computerScores.sum().toString())
        Log.d("user scores", userScores.sum().toString())
    }

    /**
     * this method will restore the old images again when the orientation is changed.
     */
    private fun restoreDiceImages()
    {
        // restore computer dice images
        for ((index, value) in computerScores.withIndex())
                imgViewsOfComputer[index].setImageResource(
                    resources.getIdentifier("dice_$value", "drawable", "com.example.dicegame"))


        // restore user dice images
        for ((index, value) in userScores.withIndex())
            imgViewsOfUser[index].setImageResource(
                resources.getIdentifier("dice_$value", "drawable", "com.example.dicegame")
            )
    }

    /**
     * this method will restore the dice image selections.
     */
    private fun restoreSelections()
    {
        // create the border
        val border: Drawable =
            ResourcesCompat.getDrawable(resources, R.drawable.border, null)!!
        // restore selected dices
        for( (index, value) in imageViewSelected.withIndex() )
        {
            if( value )
            {
                imgViewsOfUser[index].background = border
            }
        }
    }
}



