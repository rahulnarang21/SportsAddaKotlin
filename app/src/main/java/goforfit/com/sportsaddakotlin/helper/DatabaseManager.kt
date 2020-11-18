package goforfit.com.sportsaddakotlin.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import goforfit.com.sportsaddakotlin.models.Product


class DatabaseManager (context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "user_db"
        private const val USER_TABLE_NAME = "user_tbl"
        private const val CART_TABLE_NAME = "cart_tbl"
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {

//        String query = "CREATE TABLE " + USER_TABLE_NAME + "(" + AppConfig.USER_ID + " INTEGER PRIMARY KEY," + AppConfig.USER_NAME + " TEXT,"+AppConfig.USER_EMAIL +
//                " TEXT," + AppConfig.USER_CONTACT + " TEXT," + AppConfig.USER_ADDRESS + " TEXT," + AppConfig.USER_CITY + " TEXT)";
        val query = "CREATE TABLE $USER_TABLE_NAME(${AppConfig.USER_ID} INTEGER PRIMARY KEY,${AppConfig.USER_NAME} TEXT," +
                "${AppConfig.USER_EMAIL} TEXT,${AppConfig.USER_CONTACT} TEXT)"

        val cartQuery = "CREATE TABLE $CART_TABLE_NAME(${AppConfig.PRODUCT_ID} INTEGER,${AppConfig.PRODUCT_NAME} TEXT," +
                "${AppConfig.PRODUCT_IMAGE} TEXT,${AppConfig.PRODUCT_REFUNDABLE_DEPOSIT} INTEGER," +
                "${AppConfig.TENURE_ID} INTEGER PRIMARY KEY,${AppConfig.TENURE_DURATION} INTEGER," +
                "${AppConfig.TENURE_TYPE} TEXT,${AppConfig.TENURE_RENT} INTEGER," +
                "${AppConfig.PRODUCT_SHIPPING_CHARGES} INTEGER,${AppConfig.PRODUCT_QTY} INTEGER)"

        sqLiteDatabase!!.execSQL(query)
        sqLiteDatabase.execSQL(cartQuery)

    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        migrateDatabase(sqLiteDatabase)
    }

    fun migrateDatabase(sqLiteDatabase: SQLiteDatabase?){
        Log.w(AppConfig.TAG,"on upgrade called")
        sqLiteDatabase!!.execSQL("DROP TABLE IF EXISTS $CART_TABLE_NAME")

        // for saving the old details
        var selectQuery = "SELECT * FROM $USER_TABLE_NAME"
        val cursor = sqLiteDatabase.rawQuery(selectQuery,null)
        cursor.moveToFirst()

        // drop the old table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $USER_TABLE_NAME")

        // CREATE TABLE AGAIN
        onCreate(sqLiteDatabase)
        // add the user to the db
        addUser(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),sqLiteDatabase)
        cursor.close()
    }


    fun addUser(id:String,name: String,email: String,contact: String,db:SQLiteDatabase=this.writableDatabase):Boolean{
        val contentValues = ContentValues()
        contentValues.put(AppConfig.USER_ID,id)
        contentValues.put(AppConfig.USER_NAME,name)
        contentValues.put(AppConfig.USER_EMAIL,email)
        contentValues.put(AppConfig.USER_CONTACT,contact)
        val result:Long = db.insert(USER_TABLE_NAME,null,contentValues)

        // insert will return -1 if any error and row value if no error
        return result!=-1L
    }

    val userDetails:HashMap<String,String>
        get() {
        val user = HashMap<String,String>()
            val selectQuery = "SELECT * FROM $USER_TABLE_NAME"

            val db = this.readableDatabase
            val cursor = db.rawQuery(selectQuery,null)
            cursor.moveToFirst()
            if (cursor.count>0){
                user.put(AppConfig.USER_ID, cursor.getString(0));
                user.put(AppConfig.USER_NAME, cursor.getString(1));
                user.put(AppConfig.USER_EMAIL, cursor.getString(2));
                user.put(AppConfig.USER_CONTACT, cursor.getString(3));
            }

            cursor.close()
            db.close()

            return user
        }

    // OR
//    fun getUserDetails():HashMap<String,String> {
//        val user = HashMap<String,String>()
//        val selectQuery = "SELECT * FROM $USER_TABLE_NAME"
//
//        val db = this.readableDatabase
//        val cursor = db.rawQuery(selectQuery,null)
//        cursor.moveToFirst()
//        if (cursor.count>0){
//            user.put(AppConfig.USER_ID, cursor.getString(0));
//            user.put(AppConfig.USER_NAME, cursor.getString(1));
//            user.put(AppConfig.USER_EMAIL, cursor.getString(2));
//            user.put(AppConfig.USER_CONTACT, cursor.getString(3));
//        }
//
//        cursor.close()
//        db.close()
//
//        return user
//    }


    fun addToCart(productId:String, productName:String , productImage:String ,
                  productDeposit:Int, tenureId:Int, tenureNumber:Int,tenureType:String , tenureRent:Int,
                  shippingCharges:Int, productQty:Int):Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(AppConfig.PRODUCT_ID,productId)
        contentValues.put(AppConfig.PRODUCT_IMAGE,productImage)
        contentValues.put(AppConfig.PRODUCT_REFUNDABLE_DEPOSIT, productDeposit)
        contentValues.put(AppConfig.TENURE_ID, tenureId)
        contentValues.put(AppConfig.TENURE_DURATION, tenureNumber)
        contentValues.put(AppConfig.TENURE_TYPE,tenureType)
        contentValues.put(AppConfig.TENURE_RENT,tenureRent)
        contentValues.put(AppConfig.PRODUCT_SHIPPING_CHARGES,shippingCharges)
        contentValues.put(AppConfig.PRODUCT_QTY,productQty)
        val result = db.insert(CART_TABLE_NAME,null,contentValues)
        return result!=-1L

    }

    fun isProductExist(productId: String,tenureId: String):Boolean{
        val db = this.writableDatabase
        val cursor = db.rawQuery(
            "select * from "+CART_TABLE_NAME+" where "+AppConfig.PRODUCT_ID + " = ? AND "+AppConfig.TENURE_ID+" = ?",
            arrayOf(productId,tenureId)
        )

        val exists = cursor.count>0
        cursor.close()
        return exists
    }

    fun updateCartProduct(tenureId: Int,tenureNumber: Int,tenureType: String,tenureRent: Int):Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(AppConfig.TENURE_ID,tenureId)
        contentValues.put(AppConfig.TENURE_DURATION,tenureNumber)
        contentValues.put(AppConfig.TENURE_TYPE,tenureType)
        contentValues.put(AppConfig.TENURE_RENT,tenureRent)
        val rows = db.update(CART_TABLE_NAME,contentValues,AppConfig.TENURE_ID+"=?", arrayOf(tenureId.toString()))
        return rows>0
    }


    fun getCartProducts():ArrayList<Product>{
        val productArrayList:ArrayList<Product> = ArrayList()
        val selectQuery = "select * from "+ CART_TABLE_NAME

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery,null)
        if (cursor.count>0){
            while (cursor.moveToNext()){
                val product = Product()
                product.productId = cursor.getInt(cursor.getColumnIndex(AppConfig.PRODUCT_ID))
                product.productName = cursor.getString(cursor.getColumnIndex(AppConfig.PRODUCT_NAME))
                product.productImage = cursor.getString(cursor.getColumnIndex(AppConfig.PRODUCT_IMAGE))
                product.refundableDeposit = cursor.getInt(cursor.getColumnIndex(AppConfig.PRODUCT_REFUNDABLE_DEPOSIT))
                product.tenureId = cursor.getInt(cursor.getColumnIndex(AppConfig.TENURE_ID))
                product.tenureDuration = cursor.getInt(cursor.getColumnIndex(AppConfig.TENURE_DURATION))
                product.tenureType = cursor.getString(cursor.getColumnIndex(AppConfig.TENURE_TYPE))
                product.tenureRent = cursor.getInt(cursor.getColumnIndex(AppConfig.TENURE_RENT))
                product.shippingCharges = cursor.getInt(cursor.getColumnIndex(AppConfig.PRODUCT_SHIPPING_CHARGES))
                product.productQty = cursor.getInt(cursor.getColumnIndex(AppConfig.PRODUCT_QTY))
                productArrayList.add(product)

            }
        }
        cursor.close()
        db.close()
        return productArrayList


    }

    fun updateProductQty(tenureId: Int,productQty: Int):Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(AppConfig.PRODUCT_QTY,productQty)
        val rows = db.update(CART_TABLE_NAME,contentValues,AppConfig.TENURE_ID+"=?", arrayOf(tenureId.toString()))
        return rows>0

    }

    fun deleteProduct(tenureId: Int):Int{
        val db = this.writableDatabase
        return db.delete(CART_TABLE_NAME,AppConfig.TENURE_ID+"=?", arrayOf(tenureId.toString()))
    }


    fun getTotalProducts():Int{
        val selectQuery = "select * from "+ CART_TABLE_NAME
        val db = this.readableDatabase

        val cursor = db.rawQuery(selectQuery,null)
        return cursor.count
    }

    fun emptyCart(){
        val productArrayList:ArrayList<Product> = getCartProducts()
        for (product in productArrayList)
            deleteProduct(product.tenureId)
    }


    fun deleteDatabase(){
        val db = this.writableDatabase
        // delete all rows
        db.delete(USER_TABLE_NAME,null,null)
        db.delete(CART_TABLE_NAME,null,null)
        db.close()
    }




}

//class DatabaseManager(context: Context?) :
//    SQLiteOpenHelper(
//        context,
//        DATABASE_NAME,
//        null,
//        DATABASE_VERSION
//    ) {
//
//    // return user
//    val userDetails: HashMap<String, String>
//        get() {
//            val user =
//                HashMap<String, String>()
//            val selectQuery =
//                "SELECT  * FROM $USER_TABLE_NAME"
//            val db = this.readableDatabase
//            val cursor = db.rawQuery(selectQuery, null)
//            cursor.moveToFirst()
//            if (cursor.count > 0) {
//                user[AppConfig.USER_ID] = cursor.getString(0)
//                user[AppConfig.USER_NAME] = cursor.getString(1)
//                user[AppConfig.USER_EMAIL] = cursor.getString(2)
//                user[AppConfig.USER_CONTACT] = cursor.getString(3)
//            }
//            cursor.close()
//            db.close()
//            // return user
//            return user
//        }
//
//    fun addToCart(
//        productId: String?,
//        productName: String?,
//        productImage: String?,
//        productDeposit: Int,
//        tenureId: Int,
//        tenureNumber: Int,
//        tenureType: String?,
//        tenureRent: Int,
//        shippingCharges: Int,
//        productQty: Int
//    ): Boolean {
//        val db = this.writableDatabase
//        val contentValues = ContentValues()
//        contentValues.put(AppConfig.PRODUCT_ID, productId)
//        contentValues.put(AppConfig.PRODUCT_NAME, productName)
//        contentValues.put(AppConfig.PRODUCT_IMAGE, productImage)
//        contentValues.put(AppConfig.PRODUCT_REFUNDABLE_DEPOSIT, productDeposit)
//        contentValues.put(AppConfig.TENURE_ID, tenureId)
//        contentValues.put(AppConfig.TENURE_DURATION, tenureNumber)
//        contentValues.put(AppConfig.TENURE_TYPE, tenureType)
//        contentValues.put(AppConfig.TENURE_RENT, tenureRent)
//        contentValues.put(AppConfig.PRODUCT_SHIPPING_CHARGES, shippingCharges)
//        contentValues.put(AppConfig.PRODUCT_QTY, productQty)
//        val result =
//            db.insert(CART_TABLE_NAME, null, contentValues)
//
//        // insert will return -1 if any error and row value if no error
//        return result != -1L
//    }
//
//    fun isProductExist(productId: String, tenureId: String): Boolean {
//        //Log.w(AppConfig.TAG,productId+" and tenure id is "+tenureId);
//        val db = this.writableDatabase
//        val cursor = db.rawQuery(
//            "select * from " + CART_TABLE_NAME + " where " + AppConfig.PRODUCT_ID + " = ? AND " + AppConfig.TENURE_ID + " = ?",
//            arrayOf(productId, tenureId)
//        )
//        val exists = cursor.count > 0
//        cursor.close()
//        return exists
//    }
//
//    fun updateCartProduct(
//        tenureId: Int,
//        tenureNumber: Int,
//        tenureType: String?,
//        tenureRent: Int
//    ): Boolean {
//        val db = this.writableDatabase
//        val contentValues = ContentValues()
//        contentValues.put(AppConfig.TENURE_ID, tenureId)
//        contentValues.put(AppConfig.TENURE_DURATION, tenureNumber)
//        contentValues.put(AppConfig.TENURE_TYPE, tenureType)
//        contentValues.put(AppConfig.TENURE_RENT, tenureRent)
//        val rows = db.update(
//            CART_TABLE_NAME,
//            contentValues,
//            AppConfig.TENURE_ID + "=?",
//            arrayOf(tenureId.toString())
//        )
//        return rows > 0
//    }
//
//    val cartProducts: ArrayList<Any>
//        get() {
//            val productArrayList: ArrayList<Product> = ArrayList<Product>()
//            val selectQuery =
//                "SELECT  * FROM $CART_TABLE_NAME"
//            val db = this.readableDatabase
//            val cursor = db.rawQuery(selectQuery, null)
//            if (cursor.count > 0) {
//                while (cursor.moveToNext()) {
//                    productArrayList.add(
//                        Product(
//                            cursor.getInt(0),
//                            cursor.getString(1),
//                            cursor.getString(2),
//                            cursor.getInt(3),
//                            cursor.getInt(4),
//                            cursor.getInt(5),
//                            cursor.getString(6),
//                            cursor.getInt(7),
//                            cursor.getInt(8),
//                            cursor.getInt(9)
//                        )
//                    )
//                }
//            }
//            cursor.close()
//            db.close()
//            return productArrayList
//        }
//
//    fun updateProductQty(tenureId: Int, productQty: Int): Boolean {
//        val db = this.writableDatabase
//        val contentValues = ContentValues()
//        contentValues.put(AppConfig.PRODUCT_QTY, productQty)
//        val rows = db.update(
//            CART_TABLE_NAME,
//            contentValues,
//            AppConfig.TENURE_ID + "=?",
//            arrayOf(tenureId.toString())
//        )
//        return rows > 0
//    }
//
//    fun deleteProduct(tenureId: Int): Int {
//        val db = this.writableDatabase
//        return db.delete(
//            CART_TABLE_NAME,
//            AppConfig.TENURE_ID + "=?",
//            arrayOf(tenureId.toString())
//        )
//    }
//
//    val totalProducts: Int
//        get() {
//            val selectQuery =
//                "SELECT  * FROM $CART_TABLE_NAME"
//            val db = this.readableDatabase
//            val cursor = db.rawQuery(selectQuery, null)
//            return cursor.count
//        }
//
//    fun emptyCart() {
//        val productArrayList: ArrayList<Product> = cartProducts
//        for (i in productArrayList.indices) {
//            deleteProduct(productArrayList[i].getTenureId())
//        }
//    }
//
//    fun deleteDatabase() {
//        val db = this.writableDatabase
//        // Delete All Rows
//        db.delete(USER_TABLE_NAME, null, null)
//        db.delete(CART_TABLE_NAME, null, null)
//        db.close()
//    } //    public boolean updateUserName(String adminId, String adminName){
//
//    //        SQLiteDatabase db = this.getWritableDatabase();
//    //        ContentValues contentValues = new ContentValues();
//    //        //contentValues.put(AppConfig.RESIDENT_ID,adminId);
//    //        contentValues.put(AppConfig.ADMIN_NAME,adminName);
//    //        db.update(ADMIN_TABLE_NAME,contentValues,AppConfig.ADMIN_ID+"=?",new String[]{adminId});
//    //        return true;
//    //    }
//    companion object {
//        private const val DATABASE_VERSION = 2
//        private const val DATABASE_NAME = "user_db"
//        private const val USER_TABLE_NAME = "user_tbl"
//        private const val CART_TABLE_NAME = "cart_tbl"
//    }
//}