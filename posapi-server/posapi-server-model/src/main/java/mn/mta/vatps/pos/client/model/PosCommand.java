package mn.mta.vatps.pos.client.model;

import java.io.Serializable;

/**
 * Created by nasanjargal on 1/4/16.
 */
public class PosCommand implements Serializable {
    private Function function;
    private String parameter;
    private String functionName;

    public PosCommand() {
    }

    public PosCommand(Function function) {
        this.function = function;
    }

    public PosCommand(Function function, String parameter) {
        this.function = function;
        this.parameter = parameter;
    }

    public PosCommand(Function function, String parameter, String functionName) {
        this.function = function;
        this.parameter = parameter;
        this.functionName = functionName;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
