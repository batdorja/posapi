package mn.mta.vatps.pos.client.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nasanjargal on 1/11/16.
 */
public class PosInfo implements Serializable {
    boolean success;
    String posId;
    String registerNo;
    String branchNo;
    String countLottery;
    String countBill;
    Date lastSentDate;
    String posApiPath;
    boolean sending;

    public PosInfo() {
    }

    public PosInfo(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getCountLottery() {
        return countLottery;
    }

    public void setCountLottery(String countLottery) {
        this.countLottery = countLottery;
    }

    public String getCountBill() {
        return countBill;
    }

    public void setCountBill(String countBill) {
        this.countBill = countBill;
    }

    public Date getLastSentDate() {
        return lastSentDate;
    }

    public void setLastSentDate(Date lastSentDate) {
        this.lastSentDate = lastSentDate;
    }

    public String getPosApiPath() {
        return posApiPath;
    }

    public void setPosApiPath(String posApiPath) {
        this.posApiPath = posApiPath;
    }

    public boolean isSending() {
        return sending;
    }

    public void setSending(boolean sending) {
        this.sending = sending;
    }
}
