package org.cnx.openstaxcnxmusic.logic;

/**
 * Created by ew2 on 8/1/16.
 */
public class WebviewLogic
{
    public String getBookTitle(String title)
    {
        //String title = webView.getTitle();
        int index1 = title.indexOf(" - ");
        if(index1 > -1)
        {
            int index2 = title.indexOf(" - ", index1 + 3);
            //Log.d("WebViewActivity","1: " + index1 + " 2: " + index2);
            if(index2 == -1)
            {
                return title.substring(0, index1);
            }
            else
            {

                return title.substring(index1 + 3, index2);
            }
        }
        else
        {
            return "";
        }
    }
}
