package com.vijay.nba.data.entity.summary


import com.google.gson.annotations.SerializedName


data class InjuriesItem(

	@field:SerializedName("comment")
	val comment: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("update_date")
	val updateDate: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null
)