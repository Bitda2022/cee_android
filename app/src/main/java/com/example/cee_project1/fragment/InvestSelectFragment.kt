package com.example.cee_project1.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.example.cee_project1.CEEApplication
import com.example.cee_project1.activity.InvestResultActivity
import com.example.cee_project1.databinding.FragmentInvestSelectBinding


class InvestSelectFragment : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var binding: FragmentInvestSelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun initView() {
        var sequence=CEEApplication.gameManager.getNowSequence()+1
        binding.fragmentInvestSelectDayTv.text=sequence.toString()+"주차"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentInvestSelectBinding.inflate(inflater, container, false)

        initView()
        changePage()
        setDeadLineBtn()
        return binding.root
    }

    private fun setDeadLineBtn() {
        //마감 버튼 눌렀을 시
        binding.fragmentInvestSelectDeadlineBtn.setOnClickListener {
            CEEApplication.gameManager.applyEvents()
            var optionsNameArr=CEEApplication.gameManager.getPlayersOptionsName()
            for(optionName in optionsNameArr){
                Log.d("invest_test:투자 결과 apply 후",CEEApplication.gameManager.getResults(optionName)+"\n")
            }


            val intent = Intent(activity, InvestResultActivity::class.java)
            startActivity(intent)
        }
    }

    private fun changePage() {
        //투자하고 completeBtn 누르고 selectPage로 이동

        binding.fragmentInvestSelectCompany1.setOnClickListener {

            selectToInvestingPage()
            var selectedCompany = CEEApplication.gameManager.getPlayersOptionsName().get(0)
            var amount = 0

            //선택한 기업 이름 보여주기
            binding.fragmentInvestSelectSelectedCompanyTv.text=selectedCompany

            //editText에 현재까지 투자한 돈 보여주기
            var optionAmount=CEEApplication.gameManager.getOptionAmount(selectedCompany)
            binding.fragmentInvestSelectInvestedMoneyEt.setText(optionAmount.toString())

            binding.fragmentInvestSelectCompleteBtn.setOnClickListener {


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
                var difference=amount-optionAmount
                CEEApplication.gameManager.playerInvest(selectedCompany, amount)
                Log.d("invest_test:투자", "$selectedCompany 에 $amount 만큼 투자함")

                investingToSelectPage()
            }

        }

        binding.fragmentInvestSelectCompany2.setOnClickListener {
            selectToInvestingPage()
            var selectedCompany = CEEApplication.gameManager.getPlayersOptionsName().get(1)
            var amount = 0

            //선택한 기업 이름 보여주기
            binding.fragmentInvestSelectSelectedCompanyTv.text=selectedCompany

            binding.fragmentInvestSelectCompleteBtn.setOnClickListener {

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

                CEEApplication.gameManager.playerInvest(selectedCompany, amount)
                Log.d("invest_test:투자", "$selectedCompany 에 $amount 만큼 투자함")

                investingToSelectPage()

            }
        }

        binding.fragmentInvestSelectCompany3.setOnClickListener {
            selectToInvestingPage()
            var selectedCompany = CEEApplication.gameManager.getPlayersOptionsName().get(2)
            var amount = 0

            //선택한 기업 이름 보여주기
            binding.fragmentInvestSelectSelectedCompanyTv.text=selectedCompany

            binding.fragmentInvestSelectCompleteBtn.setOnClickListener {

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

                CEEApplication.gameManager.playerInvest(selectedCompany, amount)
                Log.d("invest_test:투자", "$selectedCompany 에 $amount 만큼 투자함")

                investingToSelectPage()
            }
        }

        binding.fragmentInvestSelectSavings.setOnClickListener {
            selectToInvestingPage()
            var selectedCompany = CEEApplication.gameManager.getPlayersOptionsName().get(3)
            var amount = 0

            //선택한 기업 이름 보여주기
            binding.fragmentInvestSelectSelectedCompanyTv.text=selectedCompany

            binding.fragmentInvestSelectCompleteBtn.setOnClickListener {

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

                CEEApplication.gameManager.playerInvest(selectedCompany, amount)
                Log.d("invest_test:투자", "$selectedCompany 에 $amount 만큼 투자함")

                investingToSelectPage()

            }
        }

    }

    private fun investingToSelectPage() {

        //투자 amount editText에서 정하고 "완료"버튼 눌렀을 시
        binding.fragmentInvestSelectSelectedCompanyTv.visibility = View.GONE
        binding.fragmentInvestSelectInvestedMoneyEt.visibility = View.GONE
        binding.fragmentInvestSelectCompleteBtn.visibility = View.GONE

        binding.fragmentInvestSelectCl.visibility = View.VISIBLE


    }

    private fun selectToInvestingPage() {
        binding.fragmentInvestSelectCl.visibility = View.GONE

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


}