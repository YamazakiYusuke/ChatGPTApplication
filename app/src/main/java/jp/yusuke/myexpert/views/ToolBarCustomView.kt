package jp.yusuke.myexpert.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import jp.yusuke.myexpert.R

interface ToolBarCustomViewDelegate {
    fun onClickedLeftButton()
}

class ToolBarCustomView : LinearLayout {
    var delegate: ToolBarCustomViewDelegate? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        LayoutInflater.from(context).inflate(R.layout.custom_tool_bar, this, true)
    }

    // ツールバーに表示する文字や、ボタンの表示/非表示の切り替えを設定する
    fun configure(titleText: String, isHideBackButton: Boolean) {
        //　カスタムツールバーのImageButtonとTextViewを取得する
        val titleTextView: TextView = findViewById(R.id.text_title)
        val leftButton: LinearLayout = findViewById(R.id.btn_back)

        // TextViewに文字を設定
        // ImageViewの表示/非表示を切り替える
        titleTextView.text = titleText
        leftButton.visibility = if (isHideBackButton) View.INVISIBLE else View.VISIBLE

        // ボタンがクリックされたときのリスナーを設定
        // 実際の処理は画面ごとのActivityで設定
        leftButton.setOnClickListener {
            delegate?.onClickedLeftButton()
        }
    }

    /**
     * BackButtonのvisibilityをセット
     * @param visibility View.INVISIBLE or View.VISIBLE
     */
    fun setBackButtonVisibility(visibility: Int) {
        val leftButton: LinearLayout = findViewById(R.id.btn_back)
        leftButton.visibility = visibility
    }
}