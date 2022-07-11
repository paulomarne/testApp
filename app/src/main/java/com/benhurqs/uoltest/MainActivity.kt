package com.benhurqs.uoltest

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.benhurqs.network.ErrorType
import com.benhurqs.network.model.CustomerResponse
import com.benhurqs.network.repository.CustomerProtocol
import com.benhurqs.network.repository.CustomerRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CustomerProtocol {

    private fun callApi(){
        CustomerRepository.getCustomerList(protocol = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callApi()
    }

    override fun showLoad() {
        loading.visibility = View.VISIBLE
        loading.playAnimation()
    }

    private fun hideLoad(exec: (() -> Unit)?) {
        loading.repeatCount = 1
        loading.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {}
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                loading.visibility = View.GONE
                exec?.invoke()
            }
        })
    }

    override fun loadList(list: List<CustomerResponse?>) {
        hideLoad {
            customer_list.initialize(list)
        }
    }

    override fun showError(errorType: ErrorType) {
        hideLoad {
            error_view.setup(errorType){ callApi() }
        }
    }

}