package com.infoicon.acim.main;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.infoicon.acim.R;
import com.infoicon.acim.splash.SplashActivity;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.baseclasses.BaseActivity;
import com.infoicon.acim.home.HomeFragment;
import com.infoicon.acim.manual.ManualFragment;
import com.infoicon.acim.settings.SettingsFragment;
import com.infoicon.acim.text.TextFragment;
import com.infoicon.acim.utils.AllSelectdTabs;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.HomeMenuItemClickListener;
import com.infoicon.acim.utils.UtilsMethods;
import com.infoicon.acim.workbook.WorkbookPartFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener,HomeMenuItemClickListener {


    public static TabLayout tabLayout;
    private ImageView home,text,workbook,manual,settings;
  //  private TextView textViewHome,textViewText,textViewWorkBook,textViewManual,textViewSettings;
    public static HashMap<String, List<Fragment>> fragmentsStack= new HashMap<String, List<Fragment>>();
    public static  List<Fragment> tabFragments;
    private List<Fragment> currentTabFragments;
    public AllSelectdTabs allSelectdTabs;
    boolean isNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UtilsMethods.checkTablet(MainActivity.this);
        if(getIntent().getExtras()!=null) {
            Log.e("from_notification", "from_notification");
                isNotification = true;
            } else {
                isNotification = false;
            }

        initViews();
        initListeners();
        setUpTabLayout();

      /*  AppConstants.currentSelectedTabTag= AppConstants.TAB_HOME;
        allSelectdTabs=new AllSelectdTabs();
        allSelectdTabs.setSelectedTabs(AppConstants.currentSelectedTabTag);
        AppConstants.allSelectedTabs.add(allSelectdTabs);
        tabFragments = new ArrayList<>();
        tabFragments.add(new HomeFragment());
        fragmentsStack.put(AppConstants.TAB_HOME, tabFragments);*/

          getSupportFragmentManager()
                  .beginTransaction().replace(R.id.frame_container, new HomeFragment()).commit();

        //if user click on notification then shows setting screen
        if(isNotification){
            TabLayout.Tab tab = tabLayout.getTabAt(4);
            tab.select();
        }


       copyDatabase();
    }

    @Override
    public void initViews() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    @Override
    public void initListeners() {

    }
    private void setUpTabLayout() {


        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewHome = inflater.inflate(R.layout.custom_tab_layout, null);
        home = (ImageView)viewHome.findViewById(R.id.imageView);
        if(getResources().getBoolean(R.bool.isTablet)){
            home.setImageResource(R.drawable.home_tab_selector_tablet);
        }else{
            home.setImageResource(R.drawable.home_tab_selector);
        }
        tabLayout.addTab(tabLayout.newTab().setCustomView(viewHome));

        View viewText = inflater.inflate(R.layout.custom_tab_layout, null);
        text = (ImageView)viewText.findViewById(R.id.imageView);
        if(getResources().getBoolean(R.bool.isTablet)){
            text.setImageResource(R.drawable.text_tab_selector_tablet);
        }else{
            text.setImageResource(R.drawable.text_tab_selector);
        }


        tabLayout.addTab(tabLayout.newTab().setCustomView(viewText));


        View viewWorkbook = inflater.inflate(R.layout.custom_tab_layout, null);
        workbook = (ImageView)viewWorkbook.findViewById(R.id.imageView);

        if(getResources().getBoolean(R.bool.isTablet)){
            workbook.setImageResource(R.drawable.workbook_tab_selector_tablet);
        }else{
            workbook.setImageResource(R.drawable.workbook_tab_selector);
        }
        tabLayout.addTab(tabLayout.newTab().setCustomView(viewWorkbook));


        View viewManual = inflater.inflate(R.layout.custom_tab_layout, null);
        manual = (ImageView)viewManual.findViewById(R.id.imageView);

        if(getResources().getBoolean(R.bool.isTablet)){
            manual.setImageResource(R.drawable.manual_tab_selector_tablet);
        }else{
            manual.setImageResource(R.drawable.manual_tab_selector);
        }
        tabLayout.addTab(tabLayout.newTab().setCustomView(viewManual));


        View viewSettings = inflater.inflate(R.layout.custom_tab_layout, null);
        settings = (ImageView)viewSettings.findViewById(R.id.imageView);

        if(getResources().getBoolean(R.bool.isTablet)){
            settings.setImageResource(R.drawable.settings_tab_selector_tablet);
        }else{
            settings.setImageResource(R.drawable.settings_tab_selector);
        }
        tabLayout.addTab(tabLayout.newTab().setCustomView(viewSettings));
        tabLayout.addOnTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        int position = tab.getPosition();
        switch (position) {
            case 0:

                //setFragment(new HomeFragment(),AppConstants.TAB_HOME);

               // UtilsMethods.replaceFragmentWithBackStack(this,new HomeFragment());
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case 1:
            //    setFragment(new TextFragment(),AppConstants.TAB_TEXT);
                UtilsMethods.replaceFragmentWithBackStack(this,new TextFragment());
                break;
            case 2:
               // setFragment(new WorkbookFragment(),AppConstants.TAB_WORKBOOK);
               UtilsMethods.replaceFragmentWithBackStack(this,new WorkbookPartFragment());

               // UtilsMethods.replaceFragmentWithBackStack(this,new AudioAffirmationFragment());

                break;
            case 3:
             //   setFragment(new ManualFragment(),AppConstants.TAB_MANUAL);
                UtilsMethods.replaceFragmentWithBackStack(this,new ManualFragment());
                break;
            case 4:
             //   setFragment(new SettingsFragment(),AppConstants.TAB_SETTINGS);
                SettingsFragment settingsFragment= new SettingsFragment();
                if(isNotification){
                    Bundle bundle=new Bundle();
                    bundle.putBoolean(Keys.FROM_NOTIFICATION,true);
                    settingsFragment.setArguments(bundle);
                    isNotification=false;
                }
                UtilsMethods.replaceFragmentWithBackStack(this,settingsFragment);
                break;

        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {


        int position = tab.getPosition();
        switch (position) {
            case 0:

                //setFragment(new HomeFragment(),AppConstants.TAB_HOME);

                // UtilsMethods.replaceFragmentWithBackStack(this,new HomeFragment());
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case 1:
                //    setFragment(new TextFragment(),AppConstants.TAB_TEXT);
                UtilsMethods.replaceFragmentWithBackStack(this,new TextFragment());
                break;
            case 2:
                // setFragment(new WorkbookFragment(),AppConstants.TAB_WORKBOOK);
                UtilsMethods.replaceFragmentWithBackStack(this,new WorkbookPartFragment());

                // UtilsMethods.replaceFragmentWithBackStack(this,new AudioAffirmationFragment());

                break;
            case 3:
                //   setFragment(new ManualFragment(),AppConstants.TAB_MANUAL);
                UtilsMethods.replaceFragmentWithBackStack(this,new ManualFragment());
                break;
            case 4:
                //   setFragment(new SettingsFragment(),AppConstants.TAB_SETTINGS);
                UtilsMethods.replaceFragmentWithBackStack(this,new SettingsFragment());
                break;

        }


    }


    public void setFragment(Fragment fragment, String tab_type){
        AppConstants.currentSelectedTabTag=tab_type;
        allSelectdTabs=new AllSelectdTabs();
        allSelectdTabs.setSelectedTabs(AppConstants.currentSelectedTabTag);
        AppConstants.allSelectedTabs.add(allSelectdTabs);
        currentTabFragments= fragmentsStack.get(tab_type);
        if(currentTabFragments!=null && currentTabFragments.size()>1){
            int size = currentTabFragments.size();
            Fragment fragment1 = currentTabFragments.get(size - 1);
            showFragment(fragment1);

        }else {
            tabFragments = new ArrayList<>();
            tabFragments.add(fragment);
            fragmentsStack.put(tab_type, tabFragments);
            showFragment(fragment);

        }
    }
    public void showFragment(Fragment nextFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction = transaction.replace(R.id.frame_container, nextFragment);
        transaction.commit();
        //addFragmentToStack(nextFragment);
    }


   @Override
    public void onBackPressed() {
       UtilsMethods.hideSoftKeyboard(this);
       FragmentManager fragmentManager = getSupportFragmentManager();
       int count=fragmentManager.getBackStackEntryCount();
       Log.e("count",count+"");
       if(count>0){
           if(count==1){
               fragmentManager.popBackStack();
               TabLayout.Tab tab = tabLayout.getTabAt(0);
               tab.select();
           }else {
               fragmentManager.popBackStack();
           }
       }else{
           finish();
       }
       // handleBack();
    }

    public  void handleBack(){
        if(AppConstants.allSelectedTabs.size()>1){
            int position=AppConstants.allSelectedTabs.size()-2;
            String currentSelectedTab=AppConstants.allSelectedTabs.get(AppConstants.allSelectedTabs.size() -1).getSelectedTabs();
            String prevSelectedTab=AppConstants.allSelectedTabs.get(position).getSelectedTabs();
            AppConstants.allSelectedTabs.remove(AppConstants.allSelectedTabs.size()-1);
            List<Fragment> currentTabFragments = MainActivity.fragmentsStack.get(currentSelectedTab);
            if (currentTabFragments.size() > 1) {
                // if it is not first screen then
                // current screen is closed and removed from Back Stack and shown the previous one
                int size = currentTabFragments.size();
                Fragment fragment = currentTabFragments.get(size - 2);
                currentTabFragments.remove(size - 1);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.commit();


            } else{
               // UtilsMethods.showSimpleMessageBottom(MainActivity.this,"Press again to close ACIM.");
                super.onBackPressed();
            }/*else{
                if(prevSelectedTab==AppConstants.TAB_HOME){
                    AppConstants.allSelectedTabs.remove(AppConstants.allSelectedTabs.size() - 1);
                    MainActivity.tabLayout.getTabAt(0).select();

                }else if(prevSelectedTab==AppConstants.TAB_TEXT){
                    AppConstants.allSelectedTabs.remove(AppConstants.allSelectedTabs.size() - 1);
                    MainActivity.tabLayout.getTabAt(1).select();
                }else if(prevSelectedTab==AppConstants.TAB_WORKBOOK){

                    AppConstants.allSelectedTabs.remove(AppConstants.allSelectedTabs.size() - 1);
                    MainActivity.tabLayout.getTabAt(2).select();
                }else if(prevSelectedTab==AppConstants.TAB_MANUAL) {
                    AppConstants.allSelectedTabs.remove(AppConstants.allSelectedTabs.size() - 1);
                    MainActivity.tabLayout.getTabAt(3).select();

                }else if(prevSelectedTab==AppConstants.TAB_SETTINGS) {
                    AppConstants.allSelectedTabs.remove(AppConstants.allSelectedTabs.size() - 1);
                    MainActivity.tabLayout.getTabAt(4).select();

                }
            }*/
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onHomeMenuItemClick(int pos) {
        TabLayout.Tab tab = tabLayout.getTabAt(pos);
        tab.select();
        HomeFragment homeFragment=new HomeFragment();


    }
    private void copyDatabase(){


        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + getPackageName() + "/databases/acim";
                String backupDBPath = "acim.db";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {

        }
    }

}
