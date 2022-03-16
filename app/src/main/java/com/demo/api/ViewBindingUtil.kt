package com.demo.api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * ViewBinding帮助类
 * @author like
 * @date 3/3/21 10:13 PM
 */
object ViewBindingUtil {

    /**
     * 获取 VB Class
     * @param clazz javaClass
     */
    fun <VB : ViewBinding, T : Any> getClassVB(clazz: Class<T>): Class<VB> {
        val parameterizedType = clazz.genericSuperclass as ParameterizedType
        val types = parameterizedType.actualTypeArguments
        for (type in types) {
            if (type.toString().endsWith("Binding")) {
                return type as Class<VB>
            }
        }
        return types[0] as Class<VB>
    }

    /**
     * ViewBinding 初始化处理 ( 通过传入 javaClass )
     * @param inflater  [LayoutInflater]
     * @param container [ViewGroup]
     * @param view      待绑定 View
     * @param clazz     javaClass
     */
    fun <VB : ViewBinding, T : Any> viewBindingJavaClass(inflater: LayoutInflater? = null, container: ViewGroup? = null, view: View?, clazz: Class<T>): VB {
        try {
            return viewBinding(inflater, container, view, getClassVB(clazz))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        throw Exception("${clazz.simpleName} viewBinding error")
    }

    /**
     * ViewBinding 初始化处理
     * @param inflater  [LayoutInflater]
     * @param container [ViewGroup]
     * @param view      待绑定 View
     * @param clazz     VB Class
     */
    fun <VB : ViewBinding> viewBinding(inflater: LayoutInflater? = null, container: ViewGroup? = null, view: View?, clazz: Class<VB>): VB {
        try {
            return if (view != null) {
                val method = clazz.getMethod("bind", View::class.java)
                method.invoke(null, view) as VB
            } else {
                val method = clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
                method.invoke(null, inflater, container, false) as VB
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        throw Exception("${clazz.simpleName} viewBinding error")
    }
}