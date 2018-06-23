package myh.simpleaccounting.Model;

import myh.simpleaccounting.R;

import java.util.ArrayList;

public class Category {

    private String categorytitle;
    private int categoryimage;
    private String initcategorytitle = expendcategory[0];

    private static String[] expendcategory =
            {"餐費", "飲品", "交通", "娛樂", "醫療", "社交", "日常雜費",
                    "治裝", "貸款卡費","電信網路", "金融保險", "居家物業" };

    private static int[] expendiocns = {R.drawable.foodicons, R.drawable.drinksicons,
            R.drawable.trafficicons, R.drawable.entertainmenticons, R.drawable.medicalicons,
            R.drawable.socialicons, R.drawable.routineicons, R.drawable.clothicons,
            R.drawable.creditcardsicons,R.drawable.interneticons, R.drawable.financialicons,
            R.drawable.homeicons};

    private static String[] incomecategory =
            { "工作", "投資", "額外收入", "其他"};

    private static int[] incomeicons = {R.drawable.jobicons, R.drawable.investmenticons,
            R.drawable.bonusicons, R.drawable.othersicons};


    public Category(){};

    public Category(String title, int image){
        this.categorytitle = title;
        this.categoryimage = image;
    }

    public String getCategorytitle() {
        return categorytitle;
    }

    public int getCategoryimage() {
        return categoryimage;
    }

    public static String[] getExpendcategory() {
        return expendcategory;
    }

    public static int[] getExpendiocns() {
        return expendiocns;
    }

    public static String[] getIncomecategory() {
        return incomecategory;
    }

    public static int[] getIncomeicons() {
        return incomeicons;
    }

    public String getInitcategorytitle() {
        return initcategorytitle;
    }

    public ArrayList<Category> getincomeset(){
        ArrayList<Category> income = new ArrayList<>();
        int i=0;

        for (i=0;i<incomecategory.length;i++){
            income.add(new Category(incomecategory[i],incomeicons[i]));
        }

        return income;
    }

    public ArrayList<Category> getexpcomeset(){
        ArrayList<Category> expend = new ArrayList<>();
        int j=0;

        for (j=0;j<expendcategory.length;j++){
            expend.add(new Category(expendcategory[j],expendiocns[j]));
        }

        return expend;
    }
}
