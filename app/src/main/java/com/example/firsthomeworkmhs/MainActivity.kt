package com.example.firsthomeworkmhs

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.nobird.android.ui.adapters.DefaultDelegateAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var skillsAdapter: DefaultDelegateAdapter<Skill>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toGithub.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://github.com/ftelnov")
            startActivityForResult(openURL, 1)
        }
        skillsAdapter = DefaultDelegateAdapter()
        skillsAdapter += SkillDataAdapterDelegate()
        with(skillsContainer) {
            adapter = skillsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}