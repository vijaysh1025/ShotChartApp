package com.vijay.nbashottracker.feature.games.model.summary


import com.google.gson.annotations.SerializedName

data class Statistics(

	@field:SerializedName("free_throws_made")
	val freeThrowsMade: Int? = null,

	@field:SerializedName("rebounds")
	val rebounds: Int? = null,

	@field:SerializedName("second_chance_pts")
	val secondChancePts: Int? = null,

	@field:SerializedName("fouls_drawn")
	val foulsDrawn: Int? = null,

	@field:SerializedName("blocked_att")
	val blockedAtt: Int? = null,

	@field:SerializedName("true_shooting_att")
	val trueShootingAtt: Float? = null,

	@field:SerializedName("true_shooting_pct")
	val trueShootingPct: Float? = null,

	@field:SerializedName("two_points_made")
	val twoPointsMade: Int? = null,

	@field:SerializedName("double_double")
	val doubleDouble: Boolean? = null,

	@field:SerializedName("steals")
	val steals: Int? = null,

	@field:SerializedName("pls_min")
	val plsMin: Int? = null,

	@field:SerializedName("two_points_att")
	val twoPointsAtt: Int? = null,

	@field:SerializedName("two_points_pct")
	val twoPointsPct: Float? = null,

	@field:SerializedName("field_goals_att")
	val fieldGoalsAtt: Int? = null,

	@field:SerializedName("field_goals_pct")
	val fieldGoalsPct: Float? = null,

	@field:SerializedName("points")
	val points: Int? = null,

	@field:SerializedName("effective_fg_pct")
	val effectiveFgPct: Float? = null,

	@field:SerializedName("triple_double")
	val tripleDouble: Boolean? = null,

	@field:SerializedName("three_points_made")
	val threePointsMade: Int? = null,

	@field:SerializedName("assists")
	val assists: Int? = null,

	@field:SerializedName("efficiency_game_score")
	val efficiencyGameScore: Float? = null,

	@field:SerializedName("offensive_rebounds")
	val offensiveRebounds: Int? = null,

	@field:SerializedName("defensive_rebounds")
	val defensiveRebounds: Int? = null,

	@field:SerializedName("points_in_paint")
	val pointsInPaint: Int? = null,

	@field:SerializedName("offensive_fouls")
	val offensiveFouls: Int? = null,

	@field:SerializedName("efficiency")
	val efficiency: Float? = null,

	@field:SerializedName("field_goals_made")
	val fieldGoalsMade: Int? = null,

	@field:SerializedName("minutes")
	val minutes: String? = null,

	@field:SerializedName("blocks")
	val blocks: Int? = null,

	@field:SerializedName("personal_fouls")
	val personalFouls: Int? = null,

	@field:SerializedName("flagrant_fouls")
	val flagrantFouls: Int? = null,

	@field:SerializedName("points_in_paint_att")
	val pointsInPaintAtt: Int? = null,

	@field:SerializedName("points_in_paint_pct")
	val pointsInPaintPct: Float? = null,

	@field:SerializedName("three_points_att")
	val threePointsAtt: Int? = null,

	@field:SerializedName("three_points_pct")
	val threePointsPct: Float? = null,

	@field:SerializedName("assists_turnover_ratio")
	val assistsTurnoverRatio: Float? = null,

	@field:SerializedName("free_throws_att")
	val freeThrowsAtt: Int? = null,

	@field:SerializedName("free_throws_pct")
	val freeThrowsPct: Float? = null,

	@field:SerializedName("points_in_paint_made")
	val pointsInPaintMade: Int? = null,

	@field:SerializedName("turnovers")
	val turnovers: Int? = null,

	@field:SerializedName("tech_fouls")
	val techFouls: Int? = null,

	@field:SerializedName("points_off_turnovers")
	val pointsOffTurnovers: Int? = null
)