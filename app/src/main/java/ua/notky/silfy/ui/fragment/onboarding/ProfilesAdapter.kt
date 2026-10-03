package ua.notky.silfy.ui.fragment.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.notky.silfy.databinding.ItemProfileCardBinding
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.util.englishLanguageName

/** Profile cards of "Who's learning?" */
class ProfilesAdapter(
    private val onClick: (Profile) -> Unit
) : ListAdapter<Profile, ProfilesAdapter.ViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProfileCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemProfileCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(profile: Profile) {
            binding.avatar.setProfile(profile)
            binding.textName.text = profile.name
            binding.textEnglish.text = binding.root.context.englishLanguageName()
            binding.textLanguage.text = profile.language.nativeName
            binding.textBadge.text = profile.language.badge
            binding.root.setOnClickListener { onClick(profile) }
        }
    }

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Profile>() {
            override fun areItemsTheSame(oldItem: Profile, newItem: Profile) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Profile, newItem: Profile) = oldItem == newItem
        }
    }
}
