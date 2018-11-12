package com.skynet.rimp.common.utils.time;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateEditor extends PropertyEditorSupport
{
    private static final String HHmm = "^(([1-9]{1})|([0-1][0-9])|([1-2][0-3])):([0-5][0-9])$";
    private static final String YYYYMMDD = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$";
    private static final String YYYYMMDDHHMMSS = "^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D*$";
    
    private SimpleDateFormat df;
    
    public String getAsText()
    {
        Date value = (Date)getValue();
        if (null == value)
        {
            value = new Date();
        }
        return df.format(value);
    }
    
    public void setAsText(String text)
        throws IllegalArgumentException
    {
        Date value = null;
        if (null != text && !text.equals(""))
        {
            Pattern p = Pattern.compile(YYYYMMDDHHMMSS);
            Matcher m = p.matcher(text);
            if (m.find())
            {
                df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
            else
            {
                Pattern pp = Pattern.compile(YYYYMMDD);
                Matcher mm = pp.matcher(text);
                if (mm.find())
                {
                    df = new SimpleDateFormat("yyyy-MM-dd");
                }
                else
                {
                    Pattern ppp = Pattern.compile(HHmm);
                    Matcher mmm = ppp.matcher(text);
                    if (mmm.find())
                    {
                        df = new SimpleDateFormat("HH:mm");
                    }
                }
            }
            try
            {
                value = df.parse(text);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        setValue(value);
    }
}
