package slu.com.pandora.model;

/**
 * Created by Pro Game on 3/3/2017.
 */

public class ItemObject {
    private String name;
    private String tableNo;
    private String prodId;
    private String qty;



    public ItemObject(String tableNo, String name, String qty) {
        this.tableNo = tableNo;
        this.name = name;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
