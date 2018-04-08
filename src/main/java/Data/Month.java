package Data;

public enum Month {

    JANUARY("января");
    private String month;

    Month(String month) {
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

}
