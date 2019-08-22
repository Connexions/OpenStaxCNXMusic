/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.logic

/**
 * Business logic needed by WebviewActivity
 * @author Ed Woodward
 */
class WebviewLogic
{
    fun getBookTitle(title: String): String
    {
        //String title = webView.getTitle();
        val index1 = title.indexOf(" - ")
        if(index1 > -1)
        {
            val index2 = title.indexOf(" - ", index1 + 3)
            //Log.d("WebViewActivity","1: " + index1 + " 2: " + index2);
            return if(index2 == -1)
            {
                title.substring(0, index1)
            }
            else
            {

                title.substring(index1 + 3, index2)
            }
        }
        else
        {
            return ""
        }
    }
}
