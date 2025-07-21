package Enums;

public enum ServiceCategory {
    PHARMACY("בתי מרקחת"),
    LABORATORY("מעבדות"),
    VACCINATION("חיסונים");

    private final String tabText;

    ServiceCategory(String tabText) {
        this.tabText = tabText;
    }

    public String getTabText() {
        return tabText;
    }
}