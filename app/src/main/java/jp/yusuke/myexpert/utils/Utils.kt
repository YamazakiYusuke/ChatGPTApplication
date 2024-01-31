package jp.yusuke.myexpert.utils

class Utils {
    /**
     * roleがuserかを判定
     * @return true / false
     */
    fun isUserRole(role: String): Boolean {
        return role == Const.userRole
    }

    /**
     * roleがassistantかを判定
     * @return true / false
     */
    fun isAssistantRole(role: String): Boolean {
        return role == Const.assistantRole
    }
}