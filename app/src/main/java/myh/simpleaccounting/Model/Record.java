package myh.simpleaccounting.Model;

public class Record {

    public static final String TABLE_NAME = "accounting";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MONEY = "money";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_NOTE = "note";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_MONEY +" INTEGER,"
            + COLUMN_CATEGORY + " TEXT,"
            + COLUMN_NOTE + " TEXT,"
            + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")" ;

    private int id,money;
    private String category,timestamp,note;




    public Record(){

    }

    public Record(int id, int money, String category, String note, String timestamp){
        this.id = id;
        this.money = money;
        this.category = category;
        this.note = note;
        this.timestamp = timestamp;
    }

    public int getId(){
        return id;
    }

    public int getMoney() {
        return money;
    }

    public String getCategory(){return category;}

    public String getNote() {
        return note;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
