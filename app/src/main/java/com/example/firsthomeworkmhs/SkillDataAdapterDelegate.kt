package com.example.firsthomeworkmhs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.skill_item.view.*
import ru.nobird.android.ui.adapterdelegates.AdapterDelegate
import ru.nobird.android.ui.adapterdelegates.DelegateViewHolder

class SkillDataAdapterDelegate : AdapterDelegate<Skill, DelegateViewHolder<Skill>>() {
    override fun isForViewType(position: Int, data: Skill): Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup): DelegateViewHolder<Skill> =
        ViewHolder(createView(parent, R.layout.skill_item))

    private inner class ViewHolder(root: View) : DelegateViewHolder<Skill>(root) {
        private var skillName = root.skillName
        private var skillExp = root.exp
        override fun onBind(data: Skill) {
            super.onBind(data)
            skillName.text = data.name
            skillExp.text = data.exp
        }
    }
}