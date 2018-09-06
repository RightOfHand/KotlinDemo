package app.songy.com.login.activity

import android.os.Bundle

import com.alibaba.fastjson.TypeReference

import app.songy.com.global_base.common.base.BaseActivity
import app.songy.com.global_base.common.helper.ToastHelper
import app.songy.com.global_base.common.http.KHttpCallBack
import app.songy.com.global_base.common.http.bean.ResultContainer
import app.songy.com.login.R
import app.songy.com.login.R.id.*
import app.songy.com.login.manager.KLoginManager
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Description:
 * Created by song on 2018/6/7.
 * email：bjay20080613@qq.com
 */

class KLoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_login.setOnClickListener {
            val account = et_account.text.toString()
            val pwd = et_pwd.text.toString()
            login(account, pwd)
        }

        btn_register.setOnClickListener{
            val account = et_account.text.toString()
            val pwd = et_pwd.text.toString()
            register(account, pwd)
        }
    }

    private fun login(account: String, pwd: String) {

        KLoginManager().login(account, pwd, object : KHttpCallBack(object : TypeReference<String>() {

        }) {
            override fun onResponse(result: ResultContainer<*>) {
                super.onResponse(result)
                ToastHelper.makeToast(result.data.toString())
            }
        })
    }

    private fun register(account: String, pwd: String) {
        KLoginManager().register(account, pwd, object : KHttpCallBack(object : TypeReference<String>() {

        }) {
            override fun onResponse(result: ResultContainer<*>) {
                super.onResponse(result)
                ToastHelper.makeToast(result.data.toString())
            }
        })
    }


}
