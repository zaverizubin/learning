package com.playground.candidate_test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class EvaluateAnswersOption2 {
	private final List<String> candidateResponses;
	private final List<String> correctAnswers;

	public EvaluateAnswersOption2(final List<String> candidateResponses, final List<String> correctAnswers) {
		this.candidateResponses = candidateResponses;
		this.correctAnswers = correctAnswers;
	}

	public Map<Integer, Float> getCandidateResult() {

		final Map<Integer, Float> result = new HashMap<>();

		for (int i = 0; i < this.correctAnswers.size(); i++) {
			final int maxScore = calculateCorrectAnswerScore(this.correctAnswers.get(i));
			final int candidateScore = calculateCandidateScore(this.candidateResponses.get(i), this.correctAnswers.get(i));
			final float score = 100 * (float) candidateScore / maxScore;
			result.put(i + 1, (float) (Math.round(score * 100.0) / 100.0));

		}

		return result;

	}

	public int calculateCorrectAnswerScore(String correctAnswer) {
		int score = 0;
		correctAnswer = correctAnswer.replaceAll("[.,!]", "");
		final String[] arrOfWords = correctAnswer.split(" ");

		for (final String word : arrOfWords) {
			score += getWordScore(word.trim());
		}
		return score;
	}

	public int calculateCandidateScore(final String candidateResponse, final String correctAnswer) {

		final String[] arrOfCorrectAnswer = correctAnswer.split(" ");
		final String[] arrOfCandidateResponse = candidateResponse.split(" ");

		int score = 0;

		final HashSet<String> set = new HashSet<>();

		for (final String word : arrOfCorrectAnswer) {
			set.add(word.toLowerCase());
		}

		for (final String word : arrOfCandidateResponse) {
			if (set.contains(word.toLowerCase())) {
				score += getWordScore(word);
			}
		}
		return score;

	}

	private int getWordScore(final String word) {
		int score = 0;
		final String regex = "^-?[0-9]+\\.?[0-9]+$";
		if (word.matches(regex)) {
			score = 4;
		} else if (word.length() > 7) {
			score = 3;
		} else if (word.length() >= 5) {
			score = 1;
		}

		return score;
	}

	public static void main(String[] args) {
		String response="There are Twenty‐Four hours in a day. A year has 14 months";
		String currentAns="There are twenty-four hours in a day, 30 days in a month, and 12 months in the calendar year";
		EvaluateAnswersOption1 option1 = new EvaluateAnswersOption1(response, currentAns);
		System.out.print(option1.getCandidateResult());
	}

}
