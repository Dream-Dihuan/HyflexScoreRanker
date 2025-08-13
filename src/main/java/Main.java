import java.util.Scanner;

public class Main {
    static String str;
    static int VtoK_method_answer=0;
    static int KtoV_method_answer=0;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        str = scanner.nextLine();
        VtoK_method_answer = VtoK_method();
        KtoV_method_answer = KtoV_method();
        int ans = Math.max(VtoK_method_answer,KtoV_method_answer);
        System.out.println(ans);
    }

    public static int findVK () {
        int result = 0;
        int fromIndex = 0;
        while(str.indexOf("VK",fromIndex)!=-1){
            ++result;
            fromIndex = str.indexOf("VK",fromIndex)+2;
        }
        return result;
    }

    public static int VtoK_method() {
        int result = 0;
        result+=findVK();
        int VVfromIndex = 0, VVKfromIndex = 0;
        while ((VVfromIndex=str.indexOf("VV",VVfromIndex))!=-1){
            if((VVKfromIndex=str.indexOf("VVK",VVKfromIndex))!=-1)
                if(VVfromIndex+1 != VVKfromIndex){
                    result++;
                    break;
                }
        }
        return result;
    }

    public static int KtoV_method() {
        int result = 0;
        result+=findVK();
        int KKfromIndex = 0, VKKfromIndex = 0;
        while ((KKfromIndex=str.indexOf("KK",KKfromIndex))!=-1){
            if((VKKfromIndex=str.indexOf("VKK",VKKfromIndex))!=-1)
                if(KKfromIndex+1 != VKKfromIndex){
                result++;
                break;
                }
        }
        return result;
    }


}