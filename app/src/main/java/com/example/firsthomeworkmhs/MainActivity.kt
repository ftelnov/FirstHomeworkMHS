package com.example.firsthomeworkmhs

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.skill_item.*
import ru.nobird.android.ui.adapters.DefaultDelegateAdapter

class MainActivity : AppCompatActivity() {
    private var skills: ArrayList<Skill> = arrayListOf(
        Skill("Kotlin", "~1y"),
        Skill("Java", "~1.5y"),
        Skill("Си/C++", "~3y"),
        Skill("Lua", "0.5y"),
        Skill("Js+html5+scss", "1.5y"),
        Skill("Python", "3y")
    )
    private var skillsAdapter = ListDelegationAdapter(skillsAdapterDelegate())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toGithub.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://github.com/ftelnov")
            startActivityForResult(openURL, 1)
        }
        skillsAdapter.items = skills
        with(skillsContainer) {
            adapter = skillsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun skillsAdapterDelegate() =
        adapterDelegateLayoutContainer<Skill, Skill>(R.layout.skill_item) {
            bind {
                skillName.text = item.name
                exp.text = item.exp
            }
        }
}