package goforfit.com.sportsaddakotlin.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import goforfit.com.sportsaddakotlin.R
import goforfit.com.sportsaddakotlin.models.ViewPagerImageModel
import kotlinx.android.synthetic.main.pager_item_layout.view.*

class ViewPagerAdapter(val context: Context,val imagesList:ArrayList<ViewPagerImageModel>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        return super.instantiateItem(container, position)

        val itemView = LayoutInflater.from(context).inflate(R.layout.pager_item_layout,container,false)

        val viewPagerImageModel = imagesList.get(position)
        Glide.with(context).load(viewPagerImageModel.imageUrl)
            .thumbnail(0.5f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(itemView.pager_image)

        container.addView(itemView)
        return itemView

    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return imagesList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
//        super.destroyItem(container, position, obj)
        container.removeView(obj as RelativeLayout)
    }
}