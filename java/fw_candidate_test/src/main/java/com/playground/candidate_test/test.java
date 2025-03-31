package com.playground.candidate_test;

import java.util.StringTokenizer;

public class test {
    /**
     * This method is used to calculate candidate score based on the received
     * response and correct answer
     *
     * @param candidateResponse
     * @param correctAnswer
     * @return String
     */
    public String calculateCandidateScore(String candidateResponse, String correctAnswer) {
        /*
         * Here we can tokenize the string so that we can compare two string word by
         * word and based on that candidate score will be calculated
         */
        StringTokenizer candidateTokenizer = new StringTokenizer(candidateResponse);
        StringTokenizer answerTokenizer = new StringTokenizer(correctAnswer);
        int candidateScore = 0;
        int maxPossibleScore = 0;

        // To calculate maximum possible score
        while (answerTokenizer.hasMoreTokens()) {
            String word = answerTokenizer.nextToken();
            maxPossibleScore += calculateScore(word);
        }
        answerTokenizer = new StringTokenizer(correctAnswer);
        // To calculate candidate score
        while (candidateTokenizer.hasMoreTokens() && answerTokenizer.hasMoreTokens()) {
            String candidateInput = candidateTokenizer.nextToken();
            String answerInput = answerTokenizer.nextToken();
            /*
             * Assumption : if user input must match with the actual answer then only it is
             * eligible for further calculation otherwise not consider also considering the
             * case sensitivity
             */
            if (candidateInput.equals(answerInput)) {
                candidateScore += calculateScore(candidateInput);
            }
        }

        return "Maximum Point Score (A) is " + maxPossibleScore + "\n\"Candidate Scored\" (B) is " + candidateScore
                + ". \nThe percentage score (B/A %) is " + (candidateScore * 100.0f / maxPossibleScore + "%");
    }

    /**
     * This method accept the word as an parameter and check for the eligibility of
     * points calculation
     *
     * @param word
     * @return int
     */
    public int calculateScore(String word) {
        int calculatedScore = 0;
        try {
            // check if the given word is number
            Integer.parseInt(word);
            calculatedScore = 4;
        } catch (NumberFormatException e) {
            // If given word is not a number
            if (word.length() >= 5 && word.length() <= 7) {
                calculatedScore = 1;
            } else if (word.length() > 7) {
                calculatedScore = 3;
            }
        }
        return calculatedScore;
    }
}

