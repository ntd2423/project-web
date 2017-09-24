package com.ntd.common;

/**
 * Created by nongtiedan on 2016/8/2.
 */
public class Const {

    public static final String API_PREFIX = "/api/";

    public static final class DB{
        public static final String MASTER = "MASTER";
        public static final String SLAVE = "SLAVE";
    }

    public enum ErrorCode {
        SUCCEED(200),
        BAD_REQUEST(400),
        UNAUTHORIZED(401),
        FORBIDDEN(403),
        NOT_FOUND(404),
        SERVER_ERROR(500);

        private int value;

        ErrorCode(int value) {
            this.value = value;
        }
        public int intValue() {
            return this.value;
        }
    }

}
