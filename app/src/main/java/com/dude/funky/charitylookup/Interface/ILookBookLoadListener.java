package com.dude.funky.charitylookup.Interface;

import com.dude.funky.charitylookup.Model.Banner;

import java.util.List;

public interface ILookBookLoadListener {

    void onLookbookLoadSuccess (List<Banner> banners);

    void onLookbookLoadFailed (String message);

}
