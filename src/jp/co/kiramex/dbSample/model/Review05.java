package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
     // 3. データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
         // 1. ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");

         // 2. DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "YK11411411");

         // 4. DBとやりとりする窓口（PreparedStatementオブジェクト）の作成
            // 検索用SQLおよび検索用PreparedStatementオブジェクトを取得
            String selectSql = "SELECT * FROM person where id = ?";
            pstmt = con.prepareStatement(selectSql);

         // 更新するCountryCodeを入力
            System.out.print("検索キーワードを入力してください > ");
            String str1 = keyIn();


         // PreparedStatementオブジェクトの?に値をセット
            pstmt.setInt(1,Integer.parseInt (str1));

            rs = pstmt.executeQuery();

            while (rs.next()) {
                // Name列の値を取得
                String name = rs.getString("Name");
                // Population列の値を取得
                int age = rs.getInt("age");

             // 取得した値を表示
                System.out.println(name);
                System.out.println(age);
        }

    }catch (ClassNotFoundException e) {
        System.err.println("JDBCドライバのロードに失敗しました。");
        e.printStackTrace();
    } catch (SQLException e) {
        System.err.println("データベースに異常が発生しました。");
        e.printStackTrace();
    } finally {

        // 8. 接続を閉じる
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("データベース切断時にエラーが発生しました。");
                e.printStackTrace();
            }
        }
    }
}

    /*
     * キーボードから入力された値をStringで返す 引数：なし 戻り値：入力された文字列
     */
    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        } catch (IOException e) {

        }
        return line;
    }
}


