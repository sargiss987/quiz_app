package com.example.quiz_app


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.solver.ArrayLinkedVariables
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.util.rangeTo
import com.example.quiz_app.R.*
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.coroutines.*



class QuestionFragment : Fragment() {

    var position : Int = 0
    private var counter : Int = 0
    private val questionList = Constants.getQuestionList()
    private val notificationId = 1235465
    private lateinit var job : Job
    private var answerList  = mutableListOf<RadioButton?>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment

        return inflater.inflate(layout.fragment_question, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initiate fragment
        initFragment()
        //setting timer

        //set button disabled until finish
        btnPrevious.isEnabled = false



             setTimer()


        //set action for button next
        btnNext.setOnClickListener {

            //set finish button disabled
            //cancel job
            if (position >= questionList.size || btnNext.text == "Finish"){
                showCompletedNotification(view)
                startActivity(Intent(view.context,MainActivity::class.java))

                return@setOnClickListener

            }

            //resetting timer
            job.cancel()
            setEnabled(true)
            setTimer()


            //change next button to finish
            //set enabled previous button
            if (position == questionList.size - 1){

                btnNext.text = "Finish"
                btnPrevious.isEnabled = true
                job.cancel()
                setEnabled(false)
            }



            //calculating correct answers
            if (position < questionList.size) {
                //calculate right answers
                calculateRightAnswers()
                checkAnswers()

            }
            //changing fragment state
            position++
            if(position < questionList.size) {
                //change fragment state
                initFragment()

            }
        }

        // set action for button previous
        btnPrevious.setOnClickListener {


            if(position > 0 ) {
                position--
                progressBar.progress = 0
                txtTime.text = "00 : 00"
                setEnabled(false)
                answerList[position]?.isChecked = true
                initFragment()
                originalState()
                showRightAnswer()



            }
        }

    }

    //method sets timer
    //if time is finished set radio buttons disabled
    private fun setTimer(){
        progressBar.progress = 60

        job = CoroutineScope(Dispatchers.Default).launch{

            var start = 60

            for (x in 0 until start) {
                delay(1000)
                start--
                withContext(Dispatchers.Main){
                    progressBar.progress--
                    if (start >= 10) {
                        txtTime.text = "00 : ${start}"
                    } else {
                        txtTime.text = "00 : 0${start}"
                    }
                }
                }
            withContext(Dispatchers.Main){
                //if time is finished set radio buttons disabled
                //check answer
                if (progressBar.progress == 0){
                    Toast.makeText(context,"time finished, please press next or finish",Toast.LENGTH_SHORT).show()
                    setEnabled(false)
                    showTimeFinishedNotification()
                }
            }



        }

    }

    //set state of fragment
    private fun initFragment(){
        txtQuestion.text = questionList[position].question
        txtAnswer1.text = questionList[position].answer1
        txtAnswer2.text = questionList[position].answer2
        txtAnswer3.text = questionList[position].answer3
        txtAnswer4.text = questionList[position].answer4
        progressBar.max = 60
    }

    //method calculates right answers
    private fun calculateRightAnswers(){


        when {
            rbAnswer1.isChecked and
                    (txtAnswer1.text == questionList[position].rightAnswer) -> {
                counter++
            }
            rbAnswer2.isChecked and
                    (txtAnswer2.text == questionList[position].rightAnswer) -> {
                counter++

            }
            rbAnswer3.isChecked and
                    (txtAnswer3.text == questionList[position].rightAnswer) -> {
                counter++

            }
            rbAnswer4.isChecked and
                    (txtAnswer4.text == questionList[position].rightAnswer) -> {
                counter++

            }

        }
    }

    //method shows notification when quiz is completed
    private fun showCompletedNotification(view: View){

        val builder = NotificationCompat.Builder(view.context, CHANNEL_ID)
            .setSmallIcon(drawable.ic_stat_name)
            .setContentTitle("Quiz completed")
            .setContentText("your score $counter")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(view.context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }

    //method shows notification when time is finished
    private fun showTimeFinishedNotification(){

        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(drawable.ic_stat_name)
            .setContentTitle("Timer")
            .setContentText("Time is finished")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(context!!)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }

    //method sets state of radio buttons
    private fun setEnabled(boolean : Boolean)
    {
        rbAnswer1.isClickable = boolean
        rbAnswer2.isClickable = boolean
        rbAnswer3.isClickable = boolean
        rbAnswer4.isClickable = boolean
    }

    //check the selected answer
    private fun checkAnswers(){


        when {
            rbAnswer1.isChecked ->{

                answerList.add(rbAnswer1)
        }
            rbAnswer2.isChecked ->{

                answerList.add(rbAnswer2)
            }
            rbAnswer3.isChecked ->{

                answerList.add(rbAnswer3)
            }
            rbAnswer4.isChecked ->{

                answerList.add(rbAnswer4)
            }

            else -> {
                answerList.add(null)
            }
        }
    }

    //set text color green if answer is right
    //else set color red
    private fun showRightAnswer(){

        when{
            answerList[position] == rbAnswer1  ->
                if (txtAnswer1.text != questionList[position].rightAnswer){
                    txtAnswer1.setTextColor(resources.getColor(color.red))
                }else{
                    txtAnswer1.setTextColor(resources.getColor(color.green))
                }
            answerList[position] == rbAnswer2  ->
                if (txtAnswer2.text != questionList[position].rightAnswer){
                    txtAnswer2.setTextColor(resources.getColor(color.red))
                }else{
                    txtAnswer2.setTextColor(resources.getColor(color.green))
                }
            answerList[position] == rbAnswer3  ->
                if (txtAnswer3.text != questionList[position].rightAnswer){
                    txtAnswer3.setTextColor(resources.getColor(color.red))
                }else{
                    txtAnswer3.setTextColor(resources.getColor(color.green))
                }
            answerList[position] == rbAnswer4  ->
                if (txtAnswer4.text != questionList[position].rightAnswer){
                    txtAnswer4.setTextColor(resources.getColor(color.red))
                }else{
                    txtAnswer4.setTextColor(resources.getColor(color.green))
                }
        }

    }

    //return original state of color after each iteration
    private fun originalState(){
        txtAnswer1.setTextColor(resources.getColor(color.black))
        txtAnswer2.setTextColor(resources.getColor(color.black))
        txtAnswer3.setTextColor(resources.getColor(color.black))
        txtAnswer4.setTextColor(resources.getColor(color.black))
    }

    }





