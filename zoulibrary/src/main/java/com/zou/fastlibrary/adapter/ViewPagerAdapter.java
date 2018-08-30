package com.zou.fastlibrary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

  private List<Fragment> fragmentList;

  public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
      super(fm);
      this.fragmentList = fragmentList;
  }

  @Override
  public int getCount() {
      return fragmentList.size();
  }

  @Override
  public Fragment getItem(int position) {
   //   pagelistener.sendposition(position);
      return fragmentList.get(position);
  }
  public interface Viewpageposition{
      public void sendposition(int position);
  }
  private Viewpageposition pagelistener;
  public void setpagelistener( Viewpageposition pagelistener){
      this.pagelistener=pagelistener;
  }


}
