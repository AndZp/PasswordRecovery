package ua.com.ukrelektro.passwordrec.model;

import java.util.Date;

public class Code {
    private int code;
    private int count;
    private Status status;
    private Date date;

    /**
     * Object Code
     *
     * @param code   int code
     * @param count  int count
     * @param status current status of check
     */
    public Code(int code, int count, Status status) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
