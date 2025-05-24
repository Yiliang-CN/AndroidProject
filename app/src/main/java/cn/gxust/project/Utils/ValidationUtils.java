package cn.gxust.project.Utils;

public class ValidationUtils {

    // 验证手机号格式
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        return phone.matches("^1[3-9][0-9]{9}$");
    }

    // 验证密码格式
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        return password.length() >= 6 && password.length() <= 20;
    }
}
