package org.cnx.openstaxcnxmusic.helpers

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import org.cnx.openstaxcnxmusic.beans.AboutList
import org.cnx.openstaxcnxmusic.beans.BookList
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class JsonHelperKT
{

    fun getAboutData(context: Context, clazz:Class<*>, jsonFile:String): AboutList?
    {
        return readJson(context, clazz, jsonFile) as AboutList?
    }

    fun getBookData(context: Context, clazz:Class<*>, jsonFile:String): BookList?
    {
        return readJson(context, clazz, jsonFile) as BookList?
    }

    private fun readJson(context: Context, clazz:Class<*>, jsonFile:String): Any?
    {
        val assets = context.assets
        val gson = Gson()

        try
        {
            val ips = assets.open(jsonFile)
            val bf = BufferedReader(InputStreamReader(ips))
            return gson.fromJson(bf, clazz)
        }
        catch (ioe: IOException)
        {
            Log.d("json", "Some problem: " + ioe.toString())
            return null
        }


    }
}