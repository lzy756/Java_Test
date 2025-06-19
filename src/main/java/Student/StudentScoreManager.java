package Student;

import java.util.Scanner;

public class StudentScoreManager {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入学生的人数：");

        int num = sc.nextInt();
        int[] scores = new int[num];

        for(int i=0;i<num;i++){
            System.out.println("请输入第"+(i+1)+"个学生的分数：");
            scores[i] = sc.nextInt();
            if(scores[i]<0||scores[i]>100){
                System.out.println("输入的分数不合法，请重新输入");
                i--;
            }
        }

        int sum = 0;
        int maxnum = scores[0];
        int minnum = scores[0];
        for(int i=0;i<num;i++){
            sum += scores[i];
            if(scores[i]>maxnum){
                maxnum = scores[i];
            }
            if(scores[i]<minnum){
                minnum = scores[i];
            }
        }
        double avg = (double)sum/num;

        System.out.println("总分为："+sum);
        System.out.println("最高分为："+maxnum);
        System.out.println("最低分为："+minnum);
        System.out.println("平均分为："+avg);
    }
}