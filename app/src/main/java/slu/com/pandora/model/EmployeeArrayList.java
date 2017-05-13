package slu.com.pandora.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by matt on 4/17/2017.
 */

public class EmployeeArrayList {
    @SerializedName("employeeList")
    @Expose
    private EmployeeList employeeList;

    public EmployeeList getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(EmployeeList employeeList) {
        this.employeeList = employeeList;
    }
}
