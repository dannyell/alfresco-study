package com.study.alfresco.searchservice.model;

public class ConstantServices {
	public static final String FIND_PRODUCT = "find product";
	public static final String GOOGLE = "http://www.google.com/search?q=${description}";
	public static final String SHOPWIKI = "http://www.shopwiki.com/l/${description}?sb=1";
	public static final String SHOPZILLA = "http://www.shopzilla.com/${description}/search";
	public static final String EBAY = "http://www.ebay.com/sch/i.html?_sacat=0&LH_Complete=1&_nkw=${description}&_mPrRngCbx=1&_udlo=${productsPriceMin}&_udhi=${productsPriceMax}";

	public static final String MOVIE_INFO = "movie info";
	public static final String HOLLYWOOD = "http://www.hollywood.com/search.aspx?q=${value!}";
	public static final String ROTTENTOMATOES = "http://www.rottentomatoes.com/search/?search=${value!}";
	public static final String IMDB = "http://www.imdb.com/find?s=all&q=${value!}";

}
