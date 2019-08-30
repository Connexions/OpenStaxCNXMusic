/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.beans

import java.io.Serializable

/**
 * Bean for book information from json
 * @author Ed Woodward
 */
class Book : Serializable, Comparable<Book>
{
    var bookTitle: String? = null
    var url: String? = null
    var title: String? = null
    var desc: String? = null
    var id: Int = 0

    override fun compareTo(another: Book): Int
    {
        val titleCompare = title!!.toUpperCase().trim { it <= ' ' }.compareTo(another.title!!.toUpperCase().trim { it <= ' ' })
        return if(titleCompare != 0)
        {
            titleCompare
        }
        else
        {
            url!!.compareTo(another.url!!)
        }
    }
}
