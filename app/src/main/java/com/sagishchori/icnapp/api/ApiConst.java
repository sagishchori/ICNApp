package com.sagishchori.icnapp.api;

public class ApiConst {

    /** Base URL **/
    public static final String BASE_URL = "https://api.icndb.com/";

    /** URL segment for getting random jokes **/
    public static final String JOKES = "jokes/random?";

    /** URL segment for sending firs and last user name **/
    public static final String USER_DETAILS_URL = "firstName={firstName}&lastName={lastName}";

    /** URL segment for limiting the jokes category **/
    public static final String URL_JOKES_LIMITATION = "&limitTo=[nerdy]";
}
