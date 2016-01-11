package mn.mta.vatps.pos.client.model;

import java.io.Serializable;

/**
 * Created by nasanjargal on 1/11/16.
 */
public class PosResult implements Serializable {

    boolean success;
    String posId;
    String result;
    PosInfo posInfo;

    public PosResult() {
    }

    public PosResult(boolean success) {
        this.success = success;
    }

    public PosResult(boolean success, String posId, String result) {
        this.success = success;
        this.posId = posId;
        this.result = result;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public PosInfo getPosInfo() {
        return posInfo;
    }

    public void setPosInfo(PosInfo posInfo) {
        this.posInfo = posInfo;
    }
}
