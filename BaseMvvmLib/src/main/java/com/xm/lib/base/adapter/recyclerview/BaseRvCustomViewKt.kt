package com.xm.lib.base.adapter.recyclerview

import android.content.Context
import android.util.AttributeSet

abstract class BaseRvCustomViewKt<T, VM: BaseRvCustomViewModel>: BaseRvCustomView<T, VM>{
    constructor(context: Context): super(context)
    constructor(context: Context, attributes: AttributeSet?): super(context, attributes)
    constructor(context: Context, attributes: AttributeSet?, themeResId: Int): super(context, attributes, themeResId)
}