package slu.com.pandora.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vince on 3/16/2017.
 */

public class AndroidOrder {

    @SerializedName("pl")
    @Expose
    private List<Pl> pl = null;

    public List<Pl> getPl() {
        return pl;
    }

    public void setPl(List<Pl> pl) {
        this.pl = pl;
    }


    @Override
    public String toString() {
        return "AndroidOrder{" +
                "pl=" + pl +
                '}';
    }
}
