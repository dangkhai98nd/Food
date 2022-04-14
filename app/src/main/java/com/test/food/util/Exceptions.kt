package gmo.androidbase.mvvm.util

import android.os.StrictMode
import androidx.recyclerview.widget.RecyclerView
import com.test.food.ui.base.recyclerview.scroll.EndlessScrollListener
import java.io.IOException
import java.net.InetAddress

class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)

fun networkAvailable(): Boolean {
    return try {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val ipAddr = InetAddress.getByName("google.com") //You can replace it with your name
        ipAddr.toString() != ""
    } catch (e: Exception) {
        false
    }
}

fun RecyclerView.addLoadMoreListener(onLoadMore : (page : Int) -> Unit) {
    addOnScrollListener(object : EndlessScrollListener(layoutManager!!) {
        override fun onLoadMore(page: Int) {
            onLoadMore.invoke(page)
        }
    })
}