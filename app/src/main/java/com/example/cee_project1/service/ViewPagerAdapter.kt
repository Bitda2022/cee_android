import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cee_project1.fregment.InvestFragment
import com.example.cee_project1.fregment.QuizSettingFragment
import com.example.cee_project1.fregment.SettingFragment
import com.example.cee_project1.fregment.StudyFragment

class ViewPagerAdapter (fragment : FragmentActivity) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> StudyFragment()
            1 -> QuizSettingFragment()
            2 -> InvestFragment()
            else -> SettingFragment()
        }
    }

}
