package com.statistant.ligue1.controller;

import java.util.Collection;

import com.statistant.ligue1.dao.DatabaseConnection;
import com.statistant.ligue1.pojo.Confrontation;
import com.statistant.ligue1.pojo.Statistic;
import com.statistant.ligue1.utils.Ligue1Utils;

public class StatisticController {

	public static void execute() {
		Collection<Confrontation> allConfrontations = DatabaseConnection.getAllConfrontations();
		if (allConfrontations.isEmpty() || allConfrontations == null) {
			Ligue1Utils.reportInfo("Aucune confrontation n'est enregistrée dans la table");
			return;
		}
		Ligue1Utils.reportInfo("Les confrontations ont bien été récupérées");
		for (Confrontation confrontation : allConfrontations) {
			if (confrontation.getNbConfrontations() == 0) {
				Ligue1Utils.reportInfo(
						"La confrontation " + confrontation.getMatch() + " est inédite et ne comporte pas de stats !");
			} else {
				try {
					setStatistiques(confrontation);
				} catch (NullConfrontationException e) {
					Ligue1Utils.reportError("Erreur lors de la mise à jour des statistiques : " + e.getMessage());
					e.printStackTrace();
					return;
				}
				Ligue1Utils.reportInfo(
						"La table \"Statistiques\" a été mise à jour avec les informations de la confrontation "
								+ confrontation.getMatch());
			}
		}
	}

	private static void setStatistiques(Confrontation confrontation) throws NullConfrontationException {

		if (confrontation == null) {
			throw new NullConfrontationException("Confrontation nulle");
		}
		Statistic statistique;
		statistique = new Statistic(confrontation.getMatch());

		statistique.setHomeTeamGoalAverage(confrontation.getHomeTeamAverageGoals());
		statistique.setAwayTeamGoalAverage(confrontation.getAwayTeamAverageGoals());
		statistique.setGoalAverage(confrontation.getAverageGoals());
		statistique.setHomeTeamScoredLessThan05GoalPercentage(
				confrontation.getHomeTeamPercentageOfMatchesWithLessThanXGoals(0.5F));
		statistique.setHomeTeamScoredLessThan15GoalPercentage(
				confrontation.getHomeTeamPercentageOfMatchesWithLessThanXGoals(1.5F));
		statistique.setHomeTeamScoredLessThan25GoalsPercentage(
				confrontation.getHomeTeamPercentageOfMatchesWithLessThanXGoals(2.5F));
		statistique.setHomeTeamScoredLessThan35GoalsPercentage(
				confrontation.getHomeTeamPercentageOfMatchesWithLessThanXGoals(3.5F));
		statistique.setHomeTeamScoredLessThan45GoalsPercentage(
				confrontation.getHomeTeamPercentageOfMatchesWithLessThanXGoals(4.5F));
		statistique.setHomeTeamScoredMoreThan05GoalPercentage(
				confrontation.getHomeTeamPercentageOfMatchesWithMoreThanXGoals(0.5F));
		statistique.setHomeTeamScoredMoreThan15GoalPercentage(
				confrontation.getHomeTeamPercentageOfMatchesWithMoreThanXGoals(1.5F));
		statistique.setHomeTeamScoredMoreThan25GoalsPercentage(
				confrontation.getHomeTeamPercentageOfMatchesWithMoreThanXGoals(2.5F));
		statistique.setHomeTeamScoredMoreThan35GoalsPercentage(
				confrontation.getHomeTeamPercentageOfMatchesWithMoreThanXGoals(3.5F));
		statistique.setHomeTeamScoredMoreThan45GoalsPercentage(
				confrontation.getHomeTeamPercentageOfMatchesWithMoreThanXGoals(4.5F));
		statistique.setAwayTeamScoredLessThan05GoalPercentage(
				confrontation.getAwayTeamPercentageOfMatchesWithLessThanXGoals(0.5F));
		statistique.setAwayTeamScoredLessThan15GoalPercentage(
				confrontation.getAwayTeamPercentageOfMatchesWithLessThanXGoals(1.5F));
		statistique.setAwayTeamScoredLessThan25GoalsPercentage(
				confrontation.getAwayTeamPercentageOfMatchesWithLessThanXGoals(2.5F));
		statistique.setAwayTeamScoredLessThan35GoalsPercentage(
				confrontation.getAwayTeamPercentageOfMatchesWithLessThanXGoals(3.5F));
		statistique.setAwayTeamScoredLessThan45GoalsPercentage(
				confrontation.getAwayTeamPercentageOfMatchesWithLessThanXGoals(4.5F));
		statistique.setAwayTeamScoredMoreThan05GoalPercentage(
				confrontation.getAwayTeamPercentageOfMatchesWithMoreThanXGoals(0.5F));
		statistique.setAwayTeamScoredMoreThan15GoalPercentage(
				confrontation.getAwayTeamPercentageOfMatchesWithMoreThanXGoals(1.5F));
		statistique.setAwayTeamScoredMoreThan25GoalsPercentage(
				confrontation.getAwayTeamPercentageOfMatchesWithMoreThanXGoals(2.5F));
		statistique.setAwayTeamScoredMoreThan35GoalsPercentage(
				confrontation.getAwayTeamPercentageOfMatchesWithMoreThanXGoals(3.5F));
		statistique.setAwayTeamScoredMoreThan45GoalsPercentage(
				confrontation.getAwayTeamPercentageOfMatchesWithMoreThanXGoals(4.5F));
		statistique.setBothTeamsToScorePercentage(confrontation.getBothTeamScoredPercentage());
		statistique.setLessThan05GoalPercentage(confrontation.getPercentageOfMatchesWithLessThanXGoals(0.5F));
		statistique.setLessThan15GoalPercentage(confrontation.getPercentageOfMatchesWithLessThanXGoals(1.5F));
		statistique.setLessThan25GoalsPercentage(confrontation.getPercentageOfMatchesWithLessThanXGoals(2.5F));
		statistique.setLessThan35GoalsPercentage(confrontation.getPercentageOfMatchesWithLessThanXGoals(3.5F));
		statistique.setLessThan45GoalsPercentage(confrontation.getPercentageOfMatchesWithLessThanXGoals(4.5F));
		statistique.setMoreThan05GoalPercentage(confrontation.getPercentageOfMatchesWithMoreThanXGoals(0.5F));
		statistique.setMoreThan15GoalPercentage(confrontation.getPercentageOfMatchesWithMoreThanXGoals(1.5F));
		statistique.setMoreThan25GoalsPercentage(confrontation.getPercentageOfMatchesWithMoreThanXGoals(2.5F));
		statistique.setMoreThan35GoalsPercentage(confrontation.getPercentageOfMatchesWithMoreThanXGoals(3.5F));
		statistique.setMoreThan45GoalsPercentage(confrontation.getPercentageOfMatchesWithMoreThanXGoals(4.5F));
		statistique
				.setHomeTeamConcededExactly0GoalPercentage(confrontation.getHomeTeamConcedeExactlyXGoalsPercentage(0));
		statistique
				.setHomeTeamConcededExactly1GoalPercentage(confrontation.getHomeTeamConcedeExactlyXGoalsPercentage(1));
		statistique
				.setHomeTeamConcededExactly2GoalsPercentage(confrontation.getHomeTeamConcedeExactlyXGoalsPercentage(2));
		statistique
				.setHomeTeamConcededExactly3GoalsPercentage(confrontation.getHomeTeamConcedeExactlyXGoalsPercentage(3));
		statistique
				.setHomeTeamConcededExactly4GoalsPercentage(confrontation.getHomeTeamConcedeExactlyXGoalsPercentage(4));
		statistique
				.setHomeTeamConcededExactly5GoalsPercentage(confrontation.getHomeTeamConcedeExactlyXGoalsPercentage(5));
		statistique.setHomeTeamScoredExactly0GoalPercentage(confrontation.getHomeTeamScoredExactlyXGoalsPercentage(0));
		statistique.setHomeTeamScoredExactly1GoalPercentage(confrontation.getHomeTeamScoredExactlyXGoalsPercentage(1));
		statistique.setHomeTeamScoredExactly2GoalsPercentage(confrontation.getHomeTeamScoredExactlyXGoalsPercentage(2));
		statistique.setHomeTeamScoredExactly3GoalsPercentage(confrontation.getHomeTeamScoredExactlyXGoalsPercentage(3));
		statistique.setHomeTeamScoredExactly4GoalsPercentage(confrontation.getHomeTeamScoredExactlyXGoalsPercentage(4));
		statistique.setHomeTeamScoredExactly5GoalsPercentage(confrontation.getHomeTeamScoredExactlyXGoalsPercentage(5));
		statistique
				.setAwayTeamConcededExactly0GoalPercentage(confrontation.getAwayTeamConcedeExactlyXGoalsPercentage(0));
		statistique
				.setAwayTeamConcededExactly1GoalPercentage(confrontation.getAwayTeamConcedeExactlyXGoalsPercentage(1));
		statistique
				.setAwayTeamConcededExactly2GoalsPercentage(confrontation.getAwayTeamConcedeExactlyXGoalsPercentage(2));
		statistique
				.setAwayTeamConcededExactly3GoalsPercentage(confrontation.getAwayTeamConcedeExactlyXGoalsPercentage(3));
		statistique
				.setAwayTeamConcededExactly4GoalsPercentage(confrontation.getAwayTeamConcedeExactlyXGoalsPercentage(4));
		statistique
				.setAwayTeamConcededExactly5GoalsPercentage(confrontation.getAwayTeamConcedeExactlyXGoalsPercentage(5));
		statistique.setAwayTeamScoredExactly0GoalPercentage(confrontation.getAwayTeamScoredExactlyXGoalsPercentage(0));
		statistique.setAwayTeamScoredExactly1GoalPercentage(confrontation.getAwayTeamScoredExactlyXGoalsPercentage(1));
		statistique.setAwayTeamScoredExactly2GoalsPercentage(confrontation.getAwayTeamScoredExactlyXGoalsPercentage(2));
		statistique.setAwayTeamScoredExactly3GoalsPercentage(confrontation.getAwayTeamScoredExactlyXGoalsPercentage(3));
		statistique.setAwayTeamScoredExactly4GoalsPercentage(confrontation.getAwayTeamScoredExactlyXGoalsPercentage(4));
		statistique.setAwayTeamScoredExactly5GoalsPercentage(confrontation.getAwayTeamScoredExactlyXGoalsPercentage(5));
		statistique.setDrawPercentage(confrontation.getDrawPercentage());
		statistique.setHomeTeamWinPercentage(confrontation.getHomeTeamWinPercentage());
		statistique.setAwayTeamWinPercentage(confrontation.getAwayTeamWinPercentage());
		statistique.setHomeTeamWinByExactly1GoalPercentage(confrontation.getHomeTeamWonByExactlyXGoalsPercentage(1));
		statistique.setHomeTeamWinByExactly2GoalsPercentage(confrontation.getHomeTeamWonByExactlyXGoalsPercentage(2));
		statistique.setHomeTeamWinByExactly3GoalsPercentage(confrontation.getHomeTeamWonByExactlyXGoalsPercentage(3));
		statistique.setHomeTeamWinByExactly4GoalsPercentage(confrontation.getHomeTeamWonByExactlyXGoalsPercentage(4));
		statistique.setHomeTeamWinByExactly5GoalsPercentage(confrontation.getHomeTeamWonByExactlyXGoalsPercentage(5));
		statistique.setHomeTeamWinByExactly6GoalsPercentage(confrontation.getHomeTeamWonByExactlyXGoalsPercentage(6));
		statistique.setHomeTeamWinByExactly7GoalsPercentage(confrontation.getHomeTeamWonByExactlyXGoalsPercentage(7));
		statistique.setHomeTeamWinByExactly8GoalsPercentage(confrontation.getHomeTeamWonByExactlyXGoalsPercentage(8));
		statistique.setHomeTeamWinByExactly9GoalsPercentage(confrontation.getHomeTeamWonByExactlyXGoalsPercentage(9));
		statistique.setAwayTeamWinByExactly1GoalPercentage(confrontation.getAwayTeamWonByExactlyXGoalsPercentage(1));
		statistique.setAwayTeamWinByExactly2GoalsPercentage(confrontation.getAwayTeamWonByExactlyXGoalsPercentage(2));
		statistique.setAwayTeamWinByExactly3GoalsPercentage(confrontation.getAwayTeamWonByExactlyXGoalsPercentage(3));
		statistique.setAwayTeamWinByExactly4GoalsPercentage(confrontation.getAwayTeamWonByExactlyXGoalsPercentage(4));
		statistique.setAwayTeamWinByExactly5GoalsPercentage(confrontation.getAwayTeamWonByExactlyXGoalsPercentage(5));
		statistique.setAwayTeamWinByExactly6GoalsPercentage(confrontation.getAwayTeamWonByExactlyXGoalsPercentage(6));
		statistique.setAwayTeamWinByExactly7GoalsPercentage(confrontation.getAwayTeamWonByExactlyXGoalsPercentage(7));
		statistique.setAwayTeamWinByExactly8GoalsPercentage(confrontation.getAwayTeamWonByExactlyXGoalsPercentage(8));
		statistique.setAwayTeamWinByExactly9GoalsPercentage(confrontation.getAwayTeamWonByExactlyXGoalsPercentage(9));

		DatabaseConnection.createOrUpdateStatistic(statistique);
	}

}
