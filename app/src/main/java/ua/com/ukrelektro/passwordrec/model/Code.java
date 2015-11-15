package ua.com.ukrelektro.passwordrec.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Code {
    @SerializedName("Code")
    private int code;

    @SerializedName("Count")
    private int count;

    private State status;
    private Date date;

    /**
     * Object Code
     *
     * @param code   int code
     * @param count  int count
     * @param status current status of check
     */
    public Code(int code, int count, State status) {
        this.code = code;
        this.count = count;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public State getStatus() {
        return status;
    }

    public void setStatus(State status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object other) {

        if ((other == null) || (getClass() != other.getClass()))
            return false;

        return this.code == ((Code) other).code;
    }
}
