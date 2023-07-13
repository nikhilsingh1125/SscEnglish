package com.sscenglishpractice.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.sscenglishpractice.CategoryActivity
import com.sscenglishpractice.HomeActivity
import com.sscenglishpractice.R
import com.sscenglishpractice.model.CategoryModel
import kotlinx.android.synthetic.main.row_home_category.view.*

class HomeAdapter(
    val context: Context,
    val arrayList: ArrayList<CategoryModel>
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    val TAG = "HomeActivity"
    private var rewardedAd: RewardedAd? = null
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        MobileAds.initialize(context)
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            context,
            "ca-app-pub-3940256099942544/5224354917",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.toString()?.let { Log.d(TAG, it) }
                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d(TAG, "Ad was loaded.")
                    rewardedAd = ad
                }
            })
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_home_category,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = arrayList[position]
        holder.itemView.txtCatTitle.text = model.title
        Glide.with(context).load(model.image).into(holder.itemView.catImg);

      /*  if (position ==0){
            holder.itemView.llLock.visibility = View.VISIBLE
        }
        else if (position ==1){
            holder.itemView.llLock.visibility = View.VISIBLE
        }
        else if (position ==2){
            holder.itemView.llLock.visibility = View.GONE
        }
        else if (position ==3){
            holder.itemView.llLock.visibility = View.GONE
        }*/
/*
        holder.itemView.llLock.setOnClickListener {
            if (position ==0){
                rewardedAd?.let { ad ->
                    ad.show(context as Activity, OnUserEarnedRewardListener {
                        Toast.makeText(context, "UnLocked", Toast.LENGTH_SHORT).show()
                        holder.itemView.llLock.visibility = View.GONE
                        holder.itemView.catCV.visibility = View.VISIBLE
                    })
                } ?: run {
                    holder.itemView.llLock.visibility = View.GONE
                    holder.itemView.catCV.visibility = View.VISIBLE
                }
            }
           else if (position == 1){
                rewardedAd?.let { ad ->
                    ad.show(context as Activity, OnUserEarnedRewardListener {
                        Toast.makeText(context, "UnLocked", Toast.LENGTH_SHORT).show()
                        holder.itemView.llLock.visibility = View.GONE
                        holder.itemView.catCV.visibility = View.VISIBLE
                    })
                } ?: run {
                    holder.itemView.llLock.visibility = View.GONE
                    holder.itemView.catCV.visibility = View.VISIBLE
                }
            }

        }*/



        holder.itemView.catCV.setOnClickListener {
            (context as HomeActivity).goToCategory(model,position)
        }

    }

    override fun getItemCount(): Int {
       return arrayList.size
    }

}