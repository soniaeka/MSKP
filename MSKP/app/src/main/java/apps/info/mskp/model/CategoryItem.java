package apps.info.mskp.model;

/**
 * Created by ACER on 12/05/2015.
 */
public class CategoryItem {
    private int tc_id;
    private String category;
    private String pict;

    public CategoryItem(){

    }
    public CategoryItem(int tc_id, String category,String pict){
        this.tc_id=tc_id;
        this.category=category;
        this.pict=pict;
    }

    public int getTc_id() {
        return tc_id;
    }

    public void setTc_id(int tc_id) {
        this.tc_id = tc_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPict() {
        return pict;
    }

    public void setPict(String pict) {
        this.pict = pict;
    }
}

