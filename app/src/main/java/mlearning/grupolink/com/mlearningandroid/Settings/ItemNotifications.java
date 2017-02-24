package mlearning.grupolink.com.mlearningandroid.Settings;

public class ItemNotifications {
    public String title;
    public int iconRes;
    public int id;


    public ItemNotifications(String title, int iconRes) {
        this.title = title;
        this.iconRes = iconRes;
    }

    public ItemNotifications(String title, int iconRes , int id ) {
        this.title = title;
        this.iconRes = iconRes;
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }


}
