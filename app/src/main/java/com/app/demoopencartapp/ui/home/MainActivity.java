package com.app.demoopencartapp.ui.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.local_models.BrandedOffersBean;
import com.app.demoopencartapp.data.local_models.HomeOffersListBean;
import com.app.demoopencartapp.data.local_models.ProductListBean;
import com.app.demoopencartapp.data.network.models.AllCategoriesResponse;
import com.app.demoopencartapp.data.network.models.HomeProductsResponse;
import com.app.demoopencartapp.shared.base.BaseActivity;
import com.app.demoopencartapp.ui.BrandedOffersAdapter;
import com.app.demoopencartapp.ui.CartActivity;
import com.app.demoopencartapp.ui.DoubleDrawerView;
import com.app.demoopencartapp.ui.HomeOffersListAdapter;
import com.app.demoopencartapp.ui.login.LoginActivity;
import com.app.demoopencartapp.ui.myaccount.MyAccountActivity;
import com.app.demoopencartapp.ui.productDetails.ProductDetailsActivity;
import com.app.demoopencartapp.ui.productList.ProductListActivity;
import com.app.demoopencartapp.utils.ChildAnimationExample;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,MainMvpView {

    @BindView(R.id.main_navigation_view)
    NavigationView main_navigation_view;

    @BindView(R.id.categories_navigation_view)
    NavigationView categories_navigation_view;

    @BindView(R.id.sub_navigationview)
    NavigationView sub_navigationview;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R.id.double_drawer_view)
    DoubleDrawerView doubleDrawerView;

    @BindView(R.id.slider)
    SliderLayout slider;

    @BindView(R.id.rv_deals)
    RecyclerView rv_deals;

    @BindView(R.id.rv_best_sellers)
    RecyclerView rv_best_sellers;

    @BindView(R.id.rv_testing)
    RecyclerView rv_testing;

    @BindView(R.id.rv_angle)
    RecyclerView rv_angle;

    @BindView(R.id.rv_offers)
    RecyclerView rv_offers;

    @BindView(R.id.rv_offers1)
    RecyclerView rv_offers1;

    @BindView(R.id.rv_offers2)
    RecyclerView rv_offers2;

    @BindView(R.id.rv_branded)
    RecyclerView rv_branded;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_branded_left)
    ImageView iv_branded_left;

    @BindView(R.id.iv_branded_right)
    ImageView iv_branded_right;

    LinearLayoutManager brandedLayoutManager;




    @Inject
    DealsProductListAdapter dealsAdapter;

    @Inject
    BestSellerListAdapter bestsellingAdapter;

    @Inject
    TestingInstrumentAdapter testingInstrumentsAdapter;

    @Inject
    AngleAdapter angleAdapter;

    @Inject
    HomeOffersListAdapter homeOffersListAdapter1;

    @Inject
    HomeOffersListAdapter homeOffersListAdapter2;

    @Inject
    HomeOffersListAdapter homeOffersListAdapter3;

    @Inject
    BrandedOffersAdapter brandedOffersAdapter;

    @Inject
    MainPresenter<MainMvpView> mainPresenter;


    int mPaddingSeparator;
    Menu categoriesMenu;
    Menu categoriessubmenu;
    Menu subcategoriesMenu;
    Menu subcategoriesSubMenu;

    Menu mainMenu;

    ArrayList<ProductListBean> productListBeanArrayList;
    ArrayList<HomeOffersListBean> homeOffersListBeanArrayList;
    ArrayList<BrandedOffersBean> brandedOffersBeanArrayList;

    @Inject
    DataManager dataManager;



    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mContext = this;

        setUnBinder(ButterKnife.bind(this));
        getActivityComponent().inject(this);
        main_navigation_view.setNavigationItemSelectedListener(this);
        categories_navigation_view.setNavigationItemSelectedListener(this);
        sub_navigationview.setNavigationItemSelectedListener(this);
        setUp();

    }


    protected void setUp()

    {

        mainPresenter.onAttach(this);

        setSupportActionBar(toolbar);
        setTitle("");
        brandedLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        main_navigation_view.setItemIconTintList(null);
        categories_navigation_view.setItemIconTintList(null);
        sub_navigationview.setItemIconTintList(null);
        Resources res = this.getResources();
        mPaddingSeparator = res.getDimensionPixelOffset(R.dimen.design_navigation_separator_vertical_padding);
        mainMenu = main_navigation_view.getMenu();
        categoriesMenu = categories_navigation_view.getMenu();
        subcategoriesMenu = sub_navigationview.getMenu();
        if(dataManager.getCurrentUserId()!=null) {
            if (!dataManager.getCurrentUserId().equals("")) {
                mainMenu.findItem(R.id.login).setVisible(false);
                mainMenu.findItem(R.id.my_profile).setVisible(true);
                mainMenu.findItem(R.id.logout).setVisible(true);

            }
            else {
                mainMenu.findItem(R.id.login).setVisible(true);
                mainMenu.findItem(R.id.my_profile).setVisible(false);
                mainMenu.findItem(R.id.logout).setVisible(false);
            }
        }
        else {
            mainMenu.findItem(R.id.login).setVisible(true);
            mainMenu.findItem(R.id.my_profile).setVisible(false);
            mainMenu.findItem(R.id.logout).setVisible(false);
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                //getActionBar().setTitle(mTitle);
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                System.out.println("draweropened");


                // getActionBar().setTitle(mDrawerTitle);
            }
        };
        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        mainPresenter.onGetHomeProducts();






    }


    public void setSliderOne(List<HomeProductsResponse.TopBannerBean> topBanner, List<HomeProductsResponse.SliderBean> slider, List<HomeProductsResponse.BrandBean> brand, List<HomeProductsResponse.DealsOfTheDayBean> dealsOfTheDay, List<HomeProductsResponse.BestSellerBean> bestSeller, List<HomeProductsResponse.TestingProductBean> testingProduct, List<HomeProductsResponse.AngelGrindesBean> angelGrindes) {


//        HashMap<String,String> url_maps = new HashMap<String, String>();
//        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
//        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        if(topBanner.size()>0) {



            for (int i = 0; i < topBanner.get(0).getTopbannerImage().size(); i++) {
                DefaultSliderView textSliderView = new DefaultSliderView(getApplicationContext());
                // initialize a SliderLayout
                textSliderView
                        .description("")
                        .image(topBanner.get(0).getTopbannerImage().get(i).getImage())
                        .setScaleType(BaseSliderView.ScaleType.Fit);
                // .setOnSliderClickListener(mContext);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", slider.get(0).getId());

                this.slider.addSlider(textSliderView);
            }
            this.slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            this.slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            this.slider.getPagerIndicator().setDefaultIndicatorColor(Color.parseColor("#00FDFF"), Color.parseColor("#FFFFFF"));
            this.slider.setCustomAnimation(new ChildAnimationExample());
            this.slider.setDuration(4000);



            loadProducts(slider,brand,dealsOfTheDay,bestSeller,testingProduct,angelGrindes);
        }


        //slider.addOnPageChangeListener(this);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search1, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search1);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            public boolean onQueryTextChange(String newText) {
                CharSequence cs = newText;
               /* try {
                    // This is your adapter that will be filtered
                    if (TextUtils.isEmpty(cs)) {
                        lv.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                    } else {
                        lv.setVisibility(View.VISIBLE);
                        scrollView.setVisibility(View.GONE);
                        // When user changed the Text

                        CharSequence cs1 = newText;
                        System.out.println("cssss" + cs1);
                        try {
                            SliderActivity.this.adapter.getFilter().filter(cs1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }*/

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void loadOffers(List<HomeProductsResponse.SliderBean> slider)
    {

        if(slider.size()>0) {
            if (slider.get(0).getBannerImage().size() > 0) {
                homeOffersListAdapter1.loadOffers(slider.get(0).getBannerImage());
            }
            if (slider.get(1).getBannerImage().size() > 0) {
                homeOffersListAdapter2.loadOffers(slider.get(1).getBannerImage());
            }
            if (slider.get(2).getBannerImage().size() > 0) {
                homeOffersListAdapter3.loadOffers(slider.get(2).getBannerImage());
            }
        }



    }

    public void loadBrands(List<HomeProductsResponse.BrandBean> brand)
    {

        if(brand.size()>0)
        {
            brandedOffersAdapter.loadBrands(brand);
        }
        mainPresenter.onGetAllCategories();

    }

    public void loadProducts(List<HomeProductsResponse.SliderBean> slider, List<HomeProductsResponse.BrandBean> brand, List<HomeProductsResponse.DealsOfTheDayBean> dealsOfTheDay, List<HomeProductsResponse.BestSellerBean> bestSeller, List<HomeProductsResponse.TestingProductBean> testingProduct, List<HomeProductsResponse.AngelGrindesBean> angelGrindes)

    {
        rv_deals.setHasFixedSize(true);
        rv_best_sellers.setHasFixedSize(true);
        rv_testing.setHasFixedSize(true);
        rv_angle.setHasFixedSize(true);
        rv_deals.setNestedScrollingEnabled(false);
        rv_deals.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_deals.setItemAnimator(new DefaultItemAnimator());

        rv_best_sellers.setNestedScrollingEnabled(false);
        rv_best_sellers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_best_sellers.setItemAnimator(new DefaultItemAnimator());

        rv_testing.setNestedScrollingEnabled(false);
        rv_testing.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_testing.setItemAnimator(new DefaultItemAnimator());

        rv_angle.setNestedScrollingEnabled(false);
        rv_angle.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_angle.setItemAnimator(new DefaultItemAnimator());

        rv_offers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_offers.setItemAnimator(new DefaultItemAnimator());
        rv_offers.setNestedScrollingEnabled(false);

        rv_offers1.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_offers1.setItemAnimator(new DefaultItemAnimator());
        rv_offers1.setNestedScrollingEnabled(false);

        rv_offers2.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_offers2.setItemAnimator(new DefaultItemAnimator());
        rv_offers2.setNestedScrollingEnabled(false);

        rv_branded.setLayoutManager(brandedLayoutManager);
        rv_branded.setItemAnimator(new DefaultItemAnimator());
        rv_branded.setNestedScrollingEnabled(false);

        rv_deals.setAdapter(dealsAdapter);
        rv_best_sellers.setAdapter(bestsellingAdapter);
        rv_testing.setAdapter(testingInstrumentsAdapter);
        rv_angle.setAdapter(angleAdapter);
        rv_branded.setAdapter(brandedOffersAdapter);

        rv_offers.setAdapter(homeOffersListAdapter1);
        rv_offers1.setAdapter(homeOffersListAdapter2);
        rv_offers2.setAdapter(homeOffersListAdapter3);

        if(dealsOfTheDay.size()>0) {
            dealsAdapter.loadProducts(dealsOfTheDay);
        }

        if(bestSeller.size()>0) {
            bestsellingAdapter.loadProducts(bestSeller);
        }
        if(testingProduct.size()>0) {
            testingInstrumentsAdapter.loadProducts(testingProduct);
        }
        if(angelGrindes.size()>0) {
            angleAdapter.loadProducts(angelGrindes);
        }

        dealsAdapter.setAdapterListener(new DealsProductListAdapter.DealsProductListListener() {
            @Override
            public void onItemClick(HomeProductsResponse.DealsOfTheDayBean item, int position) {

                mainPresenter.onOpenProductDetails(item.getProduct_id());
            }
        });

         bestsellingAdapter.setAdapterListener(new BestSellerListAdapter.BestSellingProductListListener() {
            @Override
            public void onItemClick(HomeProductsResponse.BestSellerBean item, int position) {
                mainPresenter.onOpenProductDetails(item.getProduct_id());
            }
        });
        testingInstrumentsAdapter.setAdapterListener(new TestingInstrumentAdapter.TestingInstrumentListener() {
            @Override
            public void onItemClick(HomeProductsResponse.TestingProductBean item, int position) {
                mainPresenter.onOpenProductDetails(item.getProduct_id());
            }
        });
        angleAdapter.setAdapterListener(new AngleAdapter.AngleProductListener() {
            @Override
            public void onItemClick(HomeProductsResponse.AngelGrindesBean item, int position) {
                mainPresenter.onOpenProductDetails(item.getProduct_id());
            }
        });

        loadOffers(slider);
        loadBrands(brand);

    }

    @Override
    protected void onDestroy() {
        mainPresenter.onDetach();
        super.onDestroy();

    }

    @OnClick({R.id.iv_branded_left,R.id.iv_branded_right,R.id.iv_cart})
    void onClickEvent(View view) {
        switch (view.getId()) {

            case R.id.iv_branded_left :
                if (brandedLayoutManager.findFirstVisibleItemPosition() > 0) {
                    rv_branded.smoothScrollToPosition(brandedLayoutManager.findFirstVisibleItemPosition() - 1);
                } else {
                    rv_branded.smoothScrollToPosition(0);
                }
                break;

            case R.id.iv_branded_right :
                rv_branded.smoothScrollToPosition(brandedLayoutManager.findLastVisibleItemPosition() + 1);

                break;

            case R.id.iv_cart :
                Intent intent = new Intent(mContext,CartActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.allcategories:
                doubleDrawerView.openInnerDrawer();

                break;

            case R.id.menu_close_subcategory:

                if(subcategoriesSubMenu!=null)
                {
                    subcategoriesSubMenu.clear();
                }
                doubleDrawerView.closeNestedDrawer();

                break;

            case R.id.menu_close_category:
                doubleDrawerView.closeInnerDrawer();
                break;

            case R.id.my_profile :

                Intent intent1 = new Intent(mContext, MyAccountActivity.class);
                startActivity(intent1);
                break;
            case R.id.login :
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                drawer_layout.closeDrawers();
                break;

            case R.id.logout :
                mainMenu.findItem(R.id.login).setVisible(true);
                mainMenu.findItem(R.id.my_profile).setVisible(false);
                mainMenu.findItem(R.id.logout).setVisible(false);
                main_navigation_view.invalidate();
                drawer_layout.closeDrawers();
                mainPresenter.onLogout();
                break;


        }
        return true;
    }

    @Override
    public void getAllCategories(final List<AllCategoriesResponse.DataBean> data) {
        categoriessubmenu = categoriesMenu.addSubMenu("All Categories");

        for (int i = 0; i < data.size(); i++)

        {


            categoriessubmenu.add(data.get(i).getName());


            SpannableString mNewTitlesub = new SpannableString(categoriessubmenu.getItem(i).getTitle());
           // mNewTitlesub.setSpan(new CustomTypefaceSpan("" , typeface), 0 , mNewTitlesub.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            categoriessubmenu.getItem(i).setTitle(mNewTitlesub);

            categoriessubmenu.getItem(i).setActionView(R.layout.drop_item);
            final int finalI = i;

            categoriessubmenu.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    subcategoriesSubMenu = subcategoriesMenu.addSubMenu(data.get(finalI).getName());

                    System.out.println("datas are "+data.get(finalI));
                    if(subcategoriesSubMenu!=null)
                    {
                        subcategoriesSubMenu.clear();
                    }
                    for (int j = 0; j<data.get(finalI).getChildCategory().size(); j++) {

                        subcategoriesSubMenu.add(data.get(finalI).getChildCategory().get(j).getName());

                    }
                    sub_navigationview.invalidate();

                    for (int k=0;k<data.get(finalI).getChildCategory().size();k++) {
                        final int finalK = k;
                        subcategoriesSubMenu.getItem(k).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {

                                drawer_layout.closeDrawers();


                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(mContext, ProductListActivity.class);
                                        intent.putExtra("sub_category_id",data.get(finalI).getChildCategory().get(finalK).getId());
                                        startActivity(intent);
                                    }
                                }, 100);


                                return true;
                            }
                        });
                    }
                    doubleDrawerView.openNestedDrawer();


                   /* Intent intent = new Intent(mContext,CartActivity.class);
                    startActivity(intent);
*/
                    return false;
                }
            });
        }
        categories_navigation_view.invalidate();






    }

    @Override
    public void logoutDone() {

    }

    @Override
    public void getHomeProducts(List<HomeProductsResponse.TopBannerBean> topBanner, List<HomeProductsResponse.SliderBean> slider, List<HomeProductsResponse.BrandBean> brand, List<HomeProductsResponse.DealsOfTheDayBean> dealsOfTheDay, List<HomeProductsResponse.BestSellerBean> bestSeller, List<HomeProductsResponse.TestingProductBean> testingProduct, List<HomeProductsResponse.AngelGrindesBean> angelGrindes) {

        setSliderOne(topBanner,slider,brand,dealsOfTheDay,bestSeller,testingProduct,angelGrindes);

    }

    @Override
    public void openProductDetails(String product_id) {
        Intent intent = new Intent(mContext,ProductDetailsActivity.class);
        intent.putExtra("product_id",product_id);
        startActivity(intent);
    }
}
