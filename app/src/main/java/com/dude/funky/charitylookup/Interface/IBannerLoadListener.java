package com.dude.funky.charitylookup.Interface;

import com.dude.funky.charitylookup.Model.Banner;

import java.util.List;

public interface IBannerLoadListener {

    void onBannerLoadSuccess (List<Banner> banners);

    void onBannerLoadFailed (String message);


}
