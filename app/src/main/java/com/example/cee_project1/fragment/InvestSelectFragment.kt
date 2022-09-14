package com.example.cee_project1.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.cee_project1.CEEApplication
import com.example.cee_project1.activity.InvestResultActivity
import com.example.cee_project1.databinding.FragmentInvestSelectBinding


class InvestSelectFragment : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var binding: FragmentInvestSelectBinding
    var optionAmount = 0 //원래 투자한 돈
    var amount = 0  //지금 투자하는 돈
    var difference = 0 // amount-optionAmount , (지금 투자하는 돈)-(원래 투자한 돈)
    var selectedCompany = ""
    var isSelectPage = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun initView() {
        var sequence = CEEApplication.gameManager.getNowSequence()
        binding.fragmentInvestSelectDayTv.text = (sequence + 1).toString() + "주차"
        showExsitingMoney()

    }

    private fun showExsitingMoney() {
        binding.fragmentInvestSelectExsitingCoinTv.text =
            "보유 코인 : " + CEEApplication.gameManager.getPlayerMoney().toString() + "코인"
        binding.fragmentInvestSelectAssetTv.text = "총 자산 : " + CEEApplication.gameManager.getPlayerTotalMoney().toString() + "코인"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentInvestSelectBinding.inflate(inflater, container, false)

//        binding.root.setOnClickListener {
//            hideKeyboard()
//        }

        initView()
        changePage()
        setDeadLineBtn()


        return binding.root
    }

    private fun setDeadLineBtn() {
        //마감 버튼 눌렀을 시
        binding.fragmentInvestSelectDeadlineBtn.setOnClickListener {
            CEEApplication.gameManager.applyEvents()
            var optionsNameArr = CEEApplication.gameManager.getPlayersOptionsName()
            for (optionName in optionsNameArr) {
                Log.d(
                    "invest_test:투자 결과 apply 후",
                    CEEApplication.gameManager.getResults(optionName) + "\n"
                )
            }
//            CEEApplication.gameManager.saveState(requireContext())
//            var sequence=CEEApplication.gameManager.getNowSequence()
//            CEEApplication.prefs.setString("sequence_deadline",sequence.toString())
//            Log.d("sequence_deadline_set",sequence.toString())

            val intent = Intent(activity, InvestResultActivity::class.java)
            startActivity(intent)
        }
    }

    private fun changePage() {
        //투자하고 completeBtn 누르고 selectPage로 이동

//        var optionAmount=0 //원래 투자한 돈
//        var amount = 0  //지금 투자하는 돈
//        var difference=0 // amount-optionAmount , (지금 투자하는 돈)-(원래 투자한 돈)
//        var selectedCompany=""

        binding.fragmentInvestSelectCompany1.setOnClickListener {

            selectToInvestingPage()
            selectedCompany = CEEApplication.gameManager.getPlayersOptionsName().get(0)

            selectedOptionEvents()

        }

        binding.fragmentInvestSelectCompany2.setOnClickListener {
            selectToInvestingPage()
            selectedCompany = CEEApplication.gameManager.getPlayersOptionsName().get(1)
            selectedOptionEvents()
        }

        binding.fragmentInvestSelectCompany3.setOnClickListener {
            selectToInvestingPage()
            selectedCompany = CEEApplication.gameManager.getPlayersOptionsName().get(2)
            selectedOptionEvents()

        }

        binding.fragmentInvestSelectSavings.setOnClickListener {
            selectToInvestingPage()
            selectedCompany = CEEApplication.gameManager.getPlayersOptionsName().get(3)
            selectedOptionEvents()

        }
    }

    private fun selectedOptionEvents() {
        //네 개의 선택지에 대한 이벤트 처리
        //선택한 기업 이름 보여주기
        binding.fragmentInvestSelectSelectedCompanyTv.text = selectedCompany

        //editText에 현재까지 투자한 돈 보여주기
        optionAmount = CEEApplication.gameManager.getOptionAmount(selectedCompany)
        binding.fragmentInvestSelectInvestedMoneyEt.setText(optionAmount.toString())

        //editText에 enter 눌렀을 때 입력한 돈에 따라 보유코인 text 바꾸기

//        binding.fragmentInvestSelectInvestedMoneyEt.setOnFocusChangeListener(object :
//            View.OnFocusChangeListener {
//            override fun onFocusChange(v: View?, hasFocus: Boolean) {
//                if(!hasFocus &&
//                    binding.fragmentInvestSelectInvestedMoneyEt.text.toString()!=null
//                ){
//                    try {
//                        Log.d(
//                            "invest_test:edit text String",
//                            binding.fragmentInvestSelectInvestedMoneyEt.text.toString()
//                        )
//                        amount =
//                            Integer.parseInt(binding.fragmentInvestSelectInvestedMoneyEt.text.toString())
//                        Log.d("invest_test:edit text Int", amount.toString())
//                    } catch (e: NumberFormatException) {
//                        // handle the exception
//                    }
//                    difference = amount - optionAmount
//
//                    //보유코인 text 바꾸기
//                    var exsitingMoneyTmp = CEEApplication.gameManager.getPlayerMoney() - difference
//                    binding.fragmentInvestSelectExsitingCoinTv.text =
//                        "보유 코인 : " + exsitingMoneyTmp.toString() + "코인"
//                }
//
//            }
//        })


        //숫자키패드 완료 누르기
        binding.fragmentInvestSelectInvestedMoneyEt.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                try {
                    Log.d(
                        "invest_test:edit text String",
                        binding.fragmentInvestSelectInvestedMoneyEt.text.toString()
                    )
                    amount =
                        Integer.parseInt(binding.fragmentInvestSelectInvestedMoneyEt.text.toString())
                    Log.d("invest_test:edit text Int", amount.toString())
                } catch (e: NumberFormatException) {
                    // handle the exception
                }
                difference = amount - optionAmount

                //보유코인 text 바꾸기
                var exsitingMoneyTmp = CEEApplication.gameManager.getPlayerMoney() - difference
                binding.fragmentInvestSelectExsitingCoinTv.text =
                    "보유 코인 : " + exsitingMoneyTmp.toString() + "코인"
                binding.fragmentInvestSelectAssetTv.text = "총 자산 : " + CEEApplication.gameManager.getPlayerTotalMoney().toString() + "코인"
                true
            } else {
                false
            }
        }

        binding.fragmentInvestSelectInvestedMoneyEt.doAfterTextChanged {
            try {
                Log.d(
                    "invest_test:edit text String",
                    binding.fragmentInvestSelectInvestedMoneyEt.text.toString()
                )
                amount =
                    Integer.parseInt(binding.fragmentInvestSelectInvestedMoneyEt.text.toString())
                Log.d("invest_test:edit text Int", amount.toString())
            } catch (e: NumberFormatException) {
                // handle the exception
            }
            difference = amount - optionAmount

            //보유코인 text 바꾸기
            var exsitingMoneyTmp = CEEApplication.gameManager.getPlayerMoney() - difference
            binding.fragmentInvestSelectExsitingCoinTv.text =
                "보유 코인 : " + exsitingMoneyTmp.toString() + "코인"
            binding.fragmentInvestSelectAssetTv.text = "총 자산 : " + CEEApplication.gameManager.getPlayerTotalMoney().toString() + "코인"
        }


        binding.fragmentInvestSelectCompleteBtn.setOnClickListener {

            //상장가보다 적게 투자하는거 막기 (예외처리)
            if (CEEApplication.gameManager.playerInvest(selectedCompany, difference)) {
                Toast.makeText(context, "가능한 거래입니다.", Toast.LENGTH_SHORT).show()
            } else {//playerInvest()가 false return
                Toast.makeText(context, "불가능한 거래입니다.", Toast.LENGTH_SHORT).show()
            }
            Log.d("invest_test:투자", "$selectedCompany 에 $difference 만큼 더 투자함")

            //보유코인 text 바꾸기
            var exsitingPlayerMoney = CEEApplication.gameManager.getPlayerMoney()
            binding.fragmentInvestSelectExsitingCoinTv.text =
                "보유 코인 : " + exsitingPlayerMoney.toString() + "코인"
            binding.fragmentInvestSelectAssetTv.text = "총 자산 : " + CEEApplication.gameManager.getPlayerTotalMoney().toString() + "코인"


            investingToSelectPage()


        }

    }

    fun investingToSelectPage() {
        isSelectPage = true

        //투자 amount editText에서 정하고 "완료"버튼 눌렀을 시
        binding.fragmentInvestSelectSelectedCompanyTv.visibility = View.GONE
        binding.fragmentInvestSelectInvestedMoneyEt.visibility = View.GONE
        binding.fragmentInvestSelectCompleteBtn.visibility = View.GONE

        binding.fragmentInvestSelectSavings.visibility = View.VISIBLE
        binding.fragmentInvestSelectCompany1.visibility = View.VISIBLE
        binding.fragmentInvestSelectCompany2.visibility = View.VISIBLE
        binding.fragmentInvestSelectCompany3.visibility = View.VISIBLE
        binding.fragmentInvestSelectDeadlineBtn.visibility = View.VISIBLE

    }

    private fun selectToInvestingPage() {
        isSelectPage = false

        binding.fragmentInvestSelectSavings.visibility = View.GONE
        binding.fragmentInvestSelectCompany1.visibility = View.GONE
        binding.fragmentInvestSelectCompany2.visibility = View.GONE
        binding.fragmentInvestSelectCompany3.visibility = View.GONE
        binding.fragmentInvestSelectDeadlineBtn.visibility = View.GONE


        binding.fragmentInvestSelectSelectedCompanyTv.visibility = View.VISIBLE
        binding.fragmentInvestSelectInvestedMoneyEt.visibility = View.VISIBLE
        binding.fragmentInvestSelectCompleteBtn.visibility = View.VISIBLE

        //InvestedMoneyEt 숫자 clear 하기
        binding.fragmentInvestSelectInvestedMoneyEt.text = null

        val set = ConstraintSet()
        val constraintLayout = binding.root
        set.clone(constraintLayout)
        set.connect(
            binding.fragmentInvestSelectExsitingCoinTv.id,
            ConstraintSet.BOTTOM,
            binding.fragmentInvestSelectSelectedCompanyTv.id,
            ConstraintSet.TOP
        )
        set.applyTo(constraintLayout)
    }

    private fun hideKeyboard() {

        if (activity != null && requireActivity().currentFocus != null) {
            // 프래그먼트기 때문에 getActivity() 사용
            val inputManager: InputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }


}