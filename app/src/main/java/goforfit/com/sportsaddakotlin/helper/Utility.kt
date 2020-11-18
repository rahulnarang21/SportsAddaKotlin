package goforfit.com.sportsaddakotlin.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import goforfit.com.sportsaddakotlin.R
import goforfit.com.sportsaddakotlin.models.Product
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utility {

    fun showNonCancellableProgressDialog(progressDialog: ProgressDialog,msg: String){
        progressDialog.setMessage(msg)
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    fun getSharedPrefs(context: Context?):SharedPreferences?{
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun isLessThanNougat():Boolean{
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.N
    }

    fun htmlToString(htmlTxt: String?):Spanned{
        return if (isLessThanNougat())
            Html.fromHtml(htmlTxt)
        else
            Html.fromHtml(htmlTxt,Html.FROM_HTML_MODE_COMPACT)
    }

    fun getTenureType(rentType: String):String{
        return if (rentType.toLowerCase().equals("month")) "months" else "days"

    }

    fun getTenureTitleText(productTenure:Product):String{
        return productTenure.tenureDuration.toString() + " " + getTenureType(productTenure.tenureType!!) + " (Rs "+
                productTenure.tenureRent + ")"
    }



    // one function to be added
    //    fun getTenureTitleText(productTenure: Product): String {
//        return productTenure.getTenureDuration()
//            .toString() + " " + getTenureType(productTenure.getTenureType()) + " (Rs " + productTenure.getTenureRent() + ")"
//    }


    fun checkField(fieldString: String,context: Context?,fieldName: String,
                   isContact:Boolean =false,isEmail:Boolean=false):Boolean{
       if (isContact){
            return if (fieldString != "" && fieldString.length == 10 && !fieldString.startsWith("0")) { true
            } else {
                Toast.makeText(context, "Please enter a valid 10 digit contact number!", Toast.LENGTH_SHORT).show()
                false
            }
        }
        else if (isEmail){
            return if (fieldString != "" && fieldString.contains("@") && fieldString.contains(".")) {
                true
            } else {
                Toast.makeText(context, "Please enter a valid email address!", Toast.LENGTH_SHORT).show()
                false
            }
        }
        else {
           return if (fieldString.equals("")){
               Toast.makeText(context,"Please fill out "+fieldName+" !",Toast.LENGTH_LONG).show()
               false
           } else true
       }

    }

//    fun checkField(fieldString: String,context: Context?,fieldName: String):Boolean{
//        return if (fieldString.equals("")){
//            Toast.makeText(context,"Please fill out "+fieldName+" !",Toast.LENGTH_LONG).show()
//            false
//        } else true
//    }


//    fun checkContact(contact: String, context: Context?): Boolean {
//        return if (contact != "" && contact.length == 10 && !contact.startsWith("0")) { true
//        } else {
//            Toast.makeText(context, "Please enter a valid 10 digit contact number!", Toast.LENGTH_SHORT).show()
//            false
//        }
//    }
//
//    fun checkEmail(email: String, context: Context?): Boolean {
//        return if (email != "" && email.contains("@") && email.contains(".")) {
//            true
//        } else {
//            Toast.makeText(context, "Please enter a valid email address!", Toast.LENGTH_SHORT).show()
//            false
//        }
//    }

    fun hideKeyboard(context: Context,view: View){
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }

//    fun showResponseDialog(
//        message: String?,
//        context: Activity,
//        icon: Int,
//        forFinishingActivity: Boolean
//    ) {
//        val builder = AlertDialog.Builder(context)
//        val layoutInflater =
//            (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
//        @SuppressLint("InflateParams") val view: View =
//            layoutInflater.inflate(R.layout.response_alert_dialog, null)
//        builder.setView(view)
//        builder.setCancelable(false)
//        val responseIcon =
//            view.findViewById<ImageView>(R.id.response_icon)
//        val responseMsg = view.findViewById<TextView>(R.id.response_msg)
//        val doneBtn = view.findViewById<TextView>(R.id.dismiss_btn)
//        responseIcon.setImageResource(icon)
//        responseMsg.text = message
//        val alertDialog = builder.create()
//        alertDialog.show()
//        doneBtn.setOnClickListener {
//            alertDialog.dismiss()
//            if (forFinishingActivity) {
//                context.setResult(AppConfig.INTENT_REQUEST_CODE, Intent())
//                context.finish()
//            }
//        }
//    }

    private fun getDateTimeFormat(dateTimeFormat: String): SimpleDateFormat {
        return SimpleDateFormat(dateTimeFormat, Locale.getDefault())
    }

    fun getDateString(time: Long): String? {
        val dateFormat = "EEE, dd MMM"
        val simpleDateFormat = getDateTimeFormat(dateFormat)
        simpleDateFormat.timeZone = TimeZone.getTimeZone(AppConfig.INDIAN_TIME_ZONE)
        return simpleDateFormat.format(time)
    }

    fun getDateStringForServer(time: Long): String? {
        val dateFormat = "yyyy-MM-dd"
        val simpleDateFormat = getDateTimeFormat(dateFormat)
        simpleDateFormat.timeZone = TimeZone.getTimeZone(AppConfig.INDIAN_TIME_ZONE)
        return simpleDateFormat.format(time)
    }

    fun strToDate(date: String?): String? {
        val dateFormat = getDateTimeFormat("yyyy-MM-dd")
        var date1: Date? = Date()
        try {
            date1 = dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val dateFormat1 = getDateTimeFormat("dd MMM, yyyy")
        return dateFormat1.format(date1)
    }

    fun getDecimalFormat(): DecimalFormat? {
        return DecimalFormat("0.00")
    }
//
//    fun getProductsString(context: Context?): String {
//        val productArrayList: ArrayList<Product> =
//            DatabaseManager(context).getCartProducts()
//        var productNameString = ""
//        for (product in productArrayList) {
//            productNameString += product.getProductName().toString() + " "
//        }
//        return productNameString
//    }
//
//    fun getOrderDetailsObject(
//        context: Context?,
//        bundle: Bundle,
//        txnId: String?,
//        totalDiscount: Double,
//        selectedAddressId: String?,
//        paymentStatus: String?,
//        paymentMode: String?
//    ): JSONObject? {
//        val databaseManager = DatabaseManager(context)
//        val userDetails: HashMap<String, String> =
//            databaseManager.getUserDetails()
//        val productArrayList: ArrayList<Product> = databaseManager.getCartProducts()
//        val orderDetailsObject = JSONObject()
//        val productsArray = JSONArray()
//        var totalRent = 0.0
//        var totalRefundableDeposit = 0
//        var shippingCharges = 0
//        try {
//            for (i in productArrayList.indices) {
//                val product: Product = productArrayList[i]
//                val productsObject = JSONObject()
//                val qty: Int = product.getProductQty()
//                Log.w(AppConfig.TAG, qty.toString() + "")
//                Log.w(AppConfig.TAG, product.getTenureRent().toString() + "")
//                productsObject.put(AppConfig.PRODUCT_ID, product.getProductId())
//                productsObject.put(AppConfig.PRODUCT_QTY, qty)
//                productsObject.put(AppConfig.PRODUCT_SHIPPING_CHARGES, product.getShippingCharges())
//                productsObject.put(AppConfig.TENURE_ID, product.getTenureId())
//                totalRent += product.getTenureRent() * qty
//                totalRefundableDeposit =
//                    totalRefundableDeposit + product.getRefundableDeposit() * qty
//                shippingCharges = shippingCharges + product.getShippingCharges()
//                productsArray.put(productsObject)
//            }
//            Log.w(AppConfig.TAG, "rent $totalRent")
//            Log.w(AppConfig.TAG, "deposit $totalRefundableDeposit")
//            var gst = 0.18 * totalRent
//            //gstCharges = Math.round(gstCharges*100.00)/100.00;
//            gst = AppConfig.getDecimalFormat().format(gst).toDouble()
//            totalRent = totalRent + totalRefundableDeposit + shippingCharges + gst
//            orderDetailsObject.put(AppConfig.PRODUCTS, productsArray)
//            orderDetailsObject.put(AppConfig.USER_ID, userDetails[AppConfig.USER_ID])
//            orderDetailsObject.put(AppConfig.TOTAL_DISCOUNT, totalDiscount)
//            orderDetailsObject.put(AppConfig.TOTAL_RENT_WITH_DISCOUNT, totalRent - totalDiscount)
//            orderDetailsObject.put(AppConfig.GST, gst)
//            orderDetailsObject.put(AppConfig.ORDER_TOTAL_AMOUNT, totalRent)
//            orderDetailsObject.put(AppConfig.ORDER_STATUS, AppConfig.ORDER_STATUS_PLACED)
//            orderDetailsObject.put(AppConfig.MODE_OF_PAYMENT, paymentMode)
//            orderDetailsObject.put(AppConfig.COUPON_ID, bundle.getString(AppConfig.COUPON_ID))
//            orderDetailsObject.put(AppConfig.PAYMENT_STATUS, paymentStatus)
//            orderDetailsObject.put(AppConfig.ADDRESS_ID, selectedAddressId)
//            orderDetailsObject.put(AppConfig.TXN_ID, txnId)
//            orderDetailsObject.put(AppConfig.USER_CONTACT, userDetails[AppConfig.USER_CONTACT])
//            orderDetailsObject.put(
//                AppConfig.DELIVERY_DATE,
//                bundle.getString(AppConfig.DELIVERY_DATE)
//            )
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//        Log.w(AppConfig.TAG, orderDetailsObject.toString() + "")
//        return orderDetailsObject
//    }
//
//    fun getTotalAmount(
//        context: Context?,
//        totalDiscount: Double
//    ): Double {
//        val productArrayList: ArrayList<Product> =
//            DatabaseManager(context).getCartProducts()
//        var totalRent = 0.0
//        var totalRefundableDeposit = 0
//        var shippingCharges = 0
//        for (i in productArrayList.indices) {
//            val product: Product = productArrayList[i]
//            val qty: Int = product.getProductQty()
//            Log.w(AppConfig.TAG, qty.toString() + "")
//            Log.w(AppConfig.TAG, product.getTenureRent().toString() + "")
//            totalRent += product.getTenureRent() * qty
//            totalRefundableDeposit = totalRefundableDeposit + product.getRefundableDeposit() * qty
//            shippingCharges = shippingCharges + product.getShippingCharges()
//        }
//        Log.w(AppConfig.TAG, "rent $totalRent")
//        Log.w(AppConfig.TAG, "deposit $totalRefundableDeposit")
//        var gst = 0.18 * totalRent
//        gst = AppConfig.getDecimalFormat().format(gst).toDouble()
//        totalRent = totalRent + totalRefundableDeposit + shippingCharges + gst
//        //Log.w(AppConfig.TAG,totalRent+" and discount is "+totalDiscount+ " and "+(totalRent-totalDiscount));
//        return totalRent - totalDiscount
//    }
}