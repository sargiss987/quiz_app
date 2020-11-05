package com.example.quiz_app

import java.util.ArrayList

object Constants {

    const val question_1 = "What is the world's largest continent?"
    const val answer_0_1 = "Asia"
    const val answer_0_2 = "Africa"
    const val answer_0_3 = "North America"
    const val answer_0_4 = "Antarctica"
    const val rightAnswer_0 = "Asia"

    const val question_2 = "What percentage of the River Nile is located in Egypt?"
    const val answer_1_1 = "50%"
    const val answer_1_2 = "74%"
    const val answer_1_3 = "22%"
    const val answer_1_4 = "10%"
    const val rightAnswer_1 = "22%"

    const val question_3 = "What body of water separates Africa and Europe?"
    const val answer_2_1 = "The Strait of Gibraltar"
    const val answer_2_2 = "The Strait of Bering"
    const val answer_2_3 = "The Strait of Dardanelles"
    const val answer_2_4 = "The Strait of Bab_el_Mandeb"
    const val rightAnswer_2 = "The Strait of Gibraltar"

    const val question_4 = "What is the state capital of Oklahoma?"
    const val answer_3_1 = "Phoenix"
    const val answer_3_2 = "Tallahassee"
    const val answer_3_3 = "Sacramento"
    const val answer_3_4 = "Oklahoma City"
    const val rightAnswer_3 = "Oklahoma City"

    const val question_5 = "What is the only major city located on two continents?"
    const val answer_4_1 = "Mexico"
    const val answer_4_2 = "Istanbul"
    const val answer_4_3 = "Ankara"
    const val answer_4_4 = "Paris"
    const val rightAnswer_4 = "Istanbul"





    fun getQuestionList(): ArrayList<Question> {

        val questionList = ArrayList<Question>()
        val question1 = Question(question_1, answer_0_1, answer_0_2, answer_0_3, answer_0_4,
            rightAnswer_0)
        val question2 = Question(question_2, answer_1_1, answer_1_2, answer_1_3, answer_1_4,
        rightAnswer_1)
        val question3 = Question(question_3, answer_2_1, answer_2_2, answer_2_3, answer_2_4,
            rightAnswer_2)
        val question4 = Question(question_4, answer_3_1, answer_3_2, answer_3_3, answer_3_4,
            rightAnswer_3)
        val question5 = Question(question_5, answer_4_1, answer_4_2, answer_4_3, answer_4_4,
            rightAnswer_4)

        questionList.add(question1)
        questionList.add(question2)
        questionList.add(question3)
        questionList.add(question4)
        questionList.add(question5)



        return questionList
    }
}