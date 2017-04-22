package slu.com.pandora.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by matt on 4/17/2017.
 */

public class EmployeeList {
    @SerializedName("list")
    @Expose
    private List<Employee> list = null;

    public List<Employee> getEmployeeList() {
        return list;
    }

    public void setEmployeeList(List<Employee> list) {
        this.list = list;
    }
}
