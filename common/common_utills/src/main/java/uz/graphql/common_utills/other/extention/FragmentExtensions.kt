package uz.graphql.common_utills.other.extention

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import uz.graphql.common_utills.other.model.Event
import com.google.android.material.snackbar.Snackbar


/**
 * Saidmurod Turdiyev writes this for All Project (SMT)
 */

fun Fragment.putArguments(block: Bundle.() -> Unit): Fragment {
    val bundle = arguments ?: Bundle()
    block(bundle)
    arguments = bundle
    return this
}

fun <T> Fragment.loadOnlyOneTimeObserver(data: Event<T>, block: T.() -> Unit) {
    val d = data.getContentIfNotHandled()
    if (d != null) {
        block.invoke(d)
    }
}
//fun Fragment.call(phone: Long?) {
//    Dexter.withContext(requireContext()).withPermissions(android.Manifest.permission.CALL_PHONE)
//        .withListener(object : MultiplePermissionsListener {
//            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
//                report?.let {
//                    if (report.areAllPermissionsGranted()) {
//                        if (phone != null) {
//                            val uri = "tel: +$phone"
//                            val intent = Intent(Intent.ACTION_CALL)
//                            intent.data = Uri.parse(uri)
//                            startActivity(intent)
//                        } else {
//                            val intent = Intent(Intent.ACTION_CALL)
//                            intent.data = Uri.parse("tel:+998998741498")
//                            startActivity(intent)
//                        }
//                    } else {
//                        if (phone != null) {
//                            val uri = "tel: +$phone"
//                            val intent = Intent(Intent.ACTION_DIAL)
//                            intent.data = Uri.parse(uri)
//                            startActivity(intent)
//                        } else {
//                            val intent = Intent(Intent.ACTION_DIAL)
//                            intent.data = Uri.parse("tel:+998998741498")
//                            startActivity(intent)
//                        }
//                    }
//                }
//            }
//
//            override fun onPermissionRationaleShouldBeShown(
//                p0: MutableList<PermissionRequest>?,
//                p1: PermissionToken?
//            ) {
//                p1?.continuePermissionRequest()
//
//            }
//        }).check()
//}

fun Fragment.openTelegram(userName: String?) {
    if (userName != null) {
        val newUserName = if (userName.startsWith("@")) userName.replaceFirst("@", "") else userName
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = "https://t.me/$newUserName"
        intent.data = Uri.parse(uri)
        startActivity(intent)
    } else {
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = "https://t.me/Saidmurod_Turdiyev"
        intent.data = Uri.parse(uri)
        startActivity(intent)
//        Snackbar.make(requireContext(), requireView(), "Telegram username topilmadi!", Snackbar.LENGTH_SHORT).show()
    }
}

fun Fragment.showToast(message: String, isLongTime: Boolean = false) {
    Toast.makeText(requireContext(), message, if (isLongTime) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

fun Fragment.showSnackBar(message: String, isLongTime: Boolean = false, actionText: String = "qayta urining", block: View.OnClickListener? = null) {
    val snackBar = Snackbar.make(requireView(), message, if (isLongTime) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT)
    if (block != null)
        snackBar.setAction(actionText, block)
    snackBar.show()
}
