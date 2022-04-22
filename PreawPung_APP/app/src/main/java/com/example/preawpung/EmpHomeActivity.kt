package com.example.preawpung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.preawpung.Fragments.EmpCheckFragment
import com.example.preawpung.Fragments.EmpHistoryFragment
import com.example.preawpung.Fragments.EmpProfileFragment
import com.example.preawpung.Fragments.EmpTrackFragment
import com.example.preawpung.databinding.ActivityEmpHomeBinding

class EmpHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmpHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmpHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val account_id = intent.getStringExtra("account_id")

        val empCheckFragment = EmpCheckFragment()
        val empTrackFragment = EmpTrackFragment()
        val empHistoryFragment = EmpHistoryFragment()
        val empProfileFragment = EmpProfileFragment()

        val bundle = Bundle()
        bundle.putString("account_id", account_id)

        val makeCurrentFragment = makeCurrentFragment(empCheckFragment)

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_check -> makeCurrentFragment(empCheckFragment)
                R.id.ic_track -> makeCurrentFragment(empTrackFragment)
                R.id.ic_history -> makeCurrentFragment(empHistoryFragment)
                R.id.ic_profile -> {
                    empProfileFragment.arguments = bundle
                    makeCurrentFragment(empProfileFragment)
                }
            }
            true
        }
    }


    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper_emp, fragment)
            commit()
        }
}