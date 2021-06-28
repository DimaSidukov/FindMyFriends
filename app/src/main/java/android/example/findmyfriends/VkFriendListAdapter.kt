package android.example.findmyfriends

import android.example.findmyfriends.vkdata.friendsinfo.Item
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class VkFriendListAdapter(private var userData: List<Item>) :
    RecyclerView.Adapter<VkFriendListAdapter.MyViewHolder>() {

    private var checkBoxStateArray = SparseBooleanArray()
    private var pickAllFlag = false

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var nameSurname: TextView = itemView.findViewById(R.id.name_list_item)
            var cityName: TextView = itemView.findViewById(R.id.city_list_item)
            var image: ImageView = itemView.findViewById(R.id.image_list_item)
            var checkBox: CheckBox = itemView.findViewById(R.id.check_list_item)

            init {
                checkBox.setOnClickListener {
                    if (checkBoxStateArray.get(adapterPosition, false)) {
                        checkBox.isChecked = false
                        checkBoxStateArray.put(adapterPosition, false)

                    } else {
                        checkBox.isChecked = true
                        checkBoxStateArray.put(adapterPosition, true)
                    }
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(pickAllFlag) {
            holder.checkBox.isChecked = pickAllFlag
            for(i in userData.indices) {
                checkBoxStateArray.put(i, pickAllFlag)
                if(i == userData.indices.last)
                    pickAllFlag = false
            }
        }

        holder.checkBox.isChecked = checkBoxStateArray.get(position, false)

        val name = userData[position].first_name + " " + userData[position].last_name
        holder.nameSurname.text = name
        holder.cityName.text = userData[position].city?.title
        Picasso.with(holder.image.context).load(userData[position].photo_100).into(holder.image)
    }

    override fun getItemCount() = userData.size

    fun filterList(updatedList: List<Item>) {
        userData = updatedList
        notifyDataSetChanged()
    }

    fun selectAll() {
        pickAllFlag = !pickAllFlag
        notifyDataSetChanged()
    }

    fun getChecked() = checkBoxStateArray
}