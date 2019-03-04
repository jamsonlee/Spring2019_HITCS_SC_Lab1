package P1;

import jdk.internal.util.xml.impl.Input;
import sun.security.util.Length;

import java.io.*;
public class MagicSquare {

    public static void main(String[] args){
        System.out.println("1.txt: "+isLegalMagicSquare("src/P1/txt/1.txt"));
        System.out.println("2.txt: "+isLegalMagicSquare("src/P1/txt/2.txt"));
        System.out.println("3.txt: "+isLegalMagicSquare("src/P1/txt/3.txt"));
        System.out.println("4.txt: "+isLegalMagicSquare("src/P1/txt/4.txt"));
        System.out.println("5.txt: "+isLegalMagicSquare("src/P1/txt/5.txt"));
        if(generateMagicSquare(5)) {
            System.out.println("6.txt: " + isLegalMagicSquare("src/P1/txt/6.txt"));
        }
    }

    private static boolean  isLegalMagicSquare(String fileName){
        File file = new File(fileName);
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            if((s =br.readLine())!=null){
                final int LENGTH  = s.split("\t").length;         //获取第一行元素数量
                String[][] P= new String[LENGTH][LENGTH];

                P[0] = s.split("\t");
                for (int i =1; (s = br.readLine())!=null; i++) {
                    P[i] = s.split("\t");
                }
                br.close();
                int[] sum =new int[LENGTH+2];
                try{
                    for(int i = 0; i<LENGTH ; i++){
                        for (int j=0 ; j<LENGTH ; j++){             //get every sum
                            sum[i] += Integer.parseInt(P[i][j]);
                        }
                        sum[LENGTH]+=Integer.parseInt(P[i][i]);
                        sum[LENGTH+1]+=Integer.parseInt(P[i][LENGTH-i-1]);
                    }
                }catch (ArrayIndexOutOfBoundsException e){        //数组指数越界异常
                    e.printStackTrace();
                    return false;
                }catch (NumberFormatException e){                 //数字格式异常
                    e.printStackTrace();
                    return false;
                }
                for(int i=0;i<LENGTH+1; i++){                 //check that all rows indeed sum to the same constant
                    if(sum[i]!=sum[i+1]){
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    private static boolean generateMagicSquare(int n) {
        if(n<0||n%2==0){
            System.out.println("输入参数为负数或偶数，不符合输入条件！");
            return false;
        }
        int magic[][] = new int[n][n];
        int row = 0, col = n / 2, i, j, square = n * n;
        for (i = 1; i <= square; i++) {              //每次将n*n阵中的一个数填上
            magic[row][col] = i;
            if (i % n == 0)                      //每n次循环后，将填充上一次填充位置下方的空格（偶数是会在此处出现越界）
                row++;
            else{
                if (row == 0)                    //当已经填充到最上边一行时，这次填充最下边一行
                    row = n - 1;
                else                             //当未填充到最上边一行时，这次填充其上边一行
                    row--;
                if (col == (n - 1))              //当已经填充到最右边一列时，这次填充最左边一列
                    col = 0;
                else                             //当未填充到最右边一列时，这次填充其右边一列
                    col++;
            }
        }
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++)
                System.out.print(magic[i][j] + "\t");
            System.out.println();
        }
        File writename = new File("src/P1/txt/6.txt");
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++)
                    out.write(magic[i][j]+"\t");
                out.write("\n");
            }
            out.flush();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
