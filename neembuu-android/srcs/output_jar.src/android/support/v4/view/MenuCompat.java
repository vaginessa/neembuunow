package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.MenuItem;

public class MenuCompat
{
  static final MenuVersionImpl IMPL;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 11) {}
    for (IMPL = new HoneycombMenuVersionImpl();; IMPL = new BaseMenuVersionImpl()) {
      return;
    }
  }
  
  public static boolean setShowAsAction(MenuItem paramMenuItem, int paramInt)
  {
    return IMPL.setShowAsAction(paramMenuItem, paramInt);
  }
  
  static class HoneycombMenuVersionImpl
    implements MenuCompat.MenuVersionImpl
  {
    public boolean setShowAsAction(MenuItem paramMenuItem, int paramInt)
    {
      MenuItemCompatHoneycomb.setShowAsAction(paramMenuItem, paramInt);
      return true;
    }
  }
  
  static class BaseMenuVersionImpl
    implements MenuCompat.MenuVersionImpl
  {
    public boolean setShowAsAction(MenuItem paramMenuItem, int paramInt)
    {
      return false;
    }
  }
  
  static abstract interface MenuVersionImpl
  {
    public abstract boolean setShowAsAction(MenuItem paramMenuItem, int paramInt);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     android.support.v4.view.MenuCompat
 * JD-Core Version:    0.7.0.1
 */