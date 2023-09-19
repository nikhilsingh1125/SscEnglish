import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.sscenglishpractice.ExamQuizsActivity
import com.sscenglishpractice.R
import com.sscenglishpractice.model.QuizCategoryModel
import kotlinx.android.synthetic.main.row_quiz_category.view.*
import libs.mjn.prettydialog.PrettyDialog

class HomeQuizAdapter(
    val context: Context,
    val arrayList: MutableList<QuizCategoryModel>
) : RecyclerView.Adapter<HomeQuizAdapter.ViewHolder>() {

    private var rewardedAd: RewardedAd? = null
    private val TAG = "RewardedAd"
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("quiz_unlock_status", Context.MODE_PRIVATE)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.row_quiz_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = arrayList[position]
        holder.itemView.txtCatTitle.text = model.Test_Title
        val isUnlocked = isQuizUnlocked(position)

        if (isUnlocked) {
            holder.itemView.catCV.visibility = View.VISIBLE
            holder.itemView.llLocked.visibility = View.GONE
        } else {
            holder.itemView.catCV.isFocusable = false
            holder.itemView.catCV.isClickable = false
            holder.itemView.llLocked.visibility = View.VISIBLE
        }

        holder.itemView.btnStart.setOnClickListener {
            if (isUnlocked) {
                val intent = Intent(context, ExamQuizsActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelableArrayList("questionsList", ArrayList(model.Questions))
                bundle.putString("testTitle", model.Test_Title)
                bundle.putString("timeTaken", model.Time_taken)
                intent.putExtras(bundle)
                context.startActivity(intent)
            } else {
                showLockToUnlockDialog(holder, position)
            }
        }

        holder.itemView.llLocked.setOnClickListener {
            showLockToUnlockDialog(holder, position)
        }
    }

    private fun isQuizUnlocked(position: Int): Boolean {
        // The first position is unlocked by default, all others use SharedPreferences
        return if (position == 0) {
            true
        } else {
            sharedPreferences.getBoolean("quiz_$position", false)
        }
    }

    private fun setQuizUnlocked(position: Int) {
        // Set unlock status in SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putBoolean("quiz_$position", true)
        editor.apply()
    }

    private fun showLockToUnlockDialog(holder: ViewHolder, position: Int) {
        val pDialog = PrettyDialog(context)
        pDialog
            .setTitle("Quizzes")
            .setIcon(R.drawable.app)
            .setMessage("Watch ads to unlock Quiz")
            .addButton(
                "Yes",
                R.color.white,
                R.color.black_light
            ) {
                pDialog.dismiss()
                loadAndShowRewardAd(holder, position)
            }
            .addButton("No",
                R.color.white,
                R.color.black_light
            ) {
                pDialog.dismiss()
            }
            .show()
    }

    private fun loadAndShowRewardAd(holder: ViewHolder, position: Int) {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(context, "ca-app-pub-7484865336777284/6596194988",
            adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.toString().let { Log.d("RewardedAd", it) }

                    setQuizUnlocked(position)
                    notifyItemChanged(position)

                    // Start the quiz activity
                    val intent = Intent(context, ExamQuizsActivity::class.java)
                    val bundle = Bundle()
                    bundle.putParcelableArrayList("questionsList", ArrayList(arrayList[position].Questions))
                    bundle.putString("testTitle", arrayList[position].Test_Title)
                    bundle.putString("timeTaken", arrayList[position].Time_taken)
                    intent.putExtras(bundle)
                    context.startActivity(intent)

                    adShown = true // Set the flag to true to indicate the ad has been shown

                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d("RewardedAd", "Ad was loaded.")
                    rewardedAd = ad
                    showLoadAdsReward(holder, position)
                }
            })
    }

    private var adShown = false // Add this boolean variable

    private fun showLoadAdsReward(holder: ViewHolder, position: Int) {
        if (!adShown) { // Check if the ad has not been shown yet
            rewardedAd?.let { ad ->
                ad.show(context as Activity, OnUserEarnedRewardListener { rewardItem ->
                    // Handle the reward.
                    val rewardAmount = rewardItem.amount
                    val rewardType = rewardItem.type
                    Log.d(TAG, "User earned the reward.")

                })

                ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        // Update the quiz lock status and UI
                        setQuizUnlocked(position)
                        notifyItemChanged(position)

                        // Start the quiz activity
                        val intent = Intent(context, ExamQuizsActivity::class.java)
                        val bundle = Bundle()
                        bundle.putParcelableArrayList("questionsList", ArrayList(arrayList[position].Questions))
                        bundle.putString("testTitle", arrayList[position].Test_Title)
                        bundle.putString("timeTaken", arrayList[position].Time_taken)
                        intent.putExtras(bundle)
                        context.startActivity(intent)

                        adShown = true // Set the flag to true to indicate the ad has been shown
                        Log.d(TAG, "Ad dismissed fullscreen content.")
                        rewardedAd = null
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        super.onAdFailedToShowFullScreenContent(p0)
                    }
                    // Rest of the callback methods...
                }
            } ?: run {
                Log.d(TAG, "The rewarded ad wasn't ready yet.")
            }
        }
    }


    override fun getItemCount(): Int {
        return arrayList.size
    }
}
