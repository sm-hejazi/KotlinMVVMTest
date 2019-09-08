package com.ppp_smh.initlibrary.entity.ErrorStatus;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by m.hejazi on 5/7/18.
 */

public abstract class ErrorStatus {
    /*@ErrStatus
    public final String err;


    public ErrorStatus(@ErrStatus String err) {
        this.err = err;
    }*/

    @StringDef({NO_CONNECTION, BAD_RESPONSE, TIMEOUT, EMPTY_RESPONSE,NOT_DEFINED,UNAUTHORIZED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrStatus {}

    /**
     * error in connecting to repository (Server or Database)
     */
    public static final String NO_CONNECTION = "NO_CONNECTION";
    /**
     * error in getting response (Json Error, Server Error, etc)
     */
    public static final String BAD_RESPONSE = "BAD_RESPONSE";
    /**
     * Time out  error
     */
    public static final String TIMEOUT = "TIMEOUT";
    /**
     * no data available in repository
     */
    public static final String EMPTY_RESPONSE = "EMPTY_RESPONSE";
    /**
     * an unexpected error
     */
    public static final String NOT_DEFINED = "NOT_DEFINED";
    /**
     * token expired
     */
    public static final String UNAUTHORIZED = "UNAUTHORIZED";

}
