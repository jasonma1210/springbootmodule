package com.example.util;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 身份证的验证
 */
public class IdValidateUtils {
    /**
     * 省级、直辖市行政区号：
     * 11 : 北京      12 : 天津     13 : 河北     14 : 山西     15 : 内蒙古
     * 21 : 辽宁      22 : 吉林     23 : 黑龙江
     * 31 : 上海      32 : 江苏     33 : 浙江     34 : 安徽     35 : 福建     36 : 江西     37 : 山东
     * 41 : 河南      42 : 湖北     43 : 湖南     44 : 广东     45 : 广西     46 : 海南
     * 50 : 重庆      51 : 四川     52 : 贵州     53 : 云南     54 : 西藏
     * 61 : 陕西      62 : 甘肃     63 : 青海     64 : 宁夏     65 : 新疆
     * 71 : 台湾
     * 81 : 香港      82 : 澳门
     * 91 : 国外
     */
    private static final String[] PROVINCE_CODES = {
            "11", "12", "13", "14", "15",
            "21", "22", "23",
            "31", "32", "33", "34", "35", "36", "37",
            "41", "42", "43", "44", "45", "46",
            "50", "51", "52", "53", "54",
            "61", "62", "63", "64", "65",
            "71",
            "81", "82",
            "91"
    };
    /**
     * 15位
     */
    private static final int FIFTEEN = 15;
    /**
     * 6位出生日期格式化模板
     */
    private static final String SIX_DIGIT_BIRTH_DATE_PATTERN = "yyMMdd";
    /**
     * 18位
     */
    private static final int EIGHTEEN = 18;
    /**
     * 8位出生日期格式化模板
     */
    private static final String EIGHT_DIGIT_BIRTH_DATE_PATTERN = "yyyyMMdd";
    /**
     * 加权系数
     */
    private static final int[] WEIGHTING_COEFFICIENT = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    /**
     * 校验码映射
     */
    private static final String[] CHECK_CODE_MAPPING = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};

    private IdValidateUtils() throws Exception {
        throw new Exception("no IdValidateUtils instance should be created");
    }

    /**
     * 校验字符串不为null且不为空字符串
     *
     * @param string 待校验的字符串
     * @return true/false
     */
    private static boolean isEmpty(String string) {
        return string == null || "".equals(string);
    }

    /**
     * 校验身份证号是否符合规则
     *
     * @param idNumberString 待校验的身份证号
     * @return true/false
     */
    public static boolean isIdNumberValidated(String idNumberString) {
        if (isEmpty(idNumberString)) {
            return false;
        }
        if (idNumberString.length() == FIFTEEN) {
            return isFifteenDigitIdNumberValidated(idNumberString);
        }
        if (idNumberString.length() == EIGHTEEN) {
            return isEighteenDigitIdNumberValidated(idNumberString);
        }
        return false;
    }

    /**
     * 校验15位身份证号
     * 15位身份证号的构成如下:
     * 省级、直辖市行政区号2位
     * 地、市级行政区号2位
     * 区、县级行政区号2位
     * 出生年2位
     * 出生月2位
     * 出生日2位
     * 序列号3位,其中奇数分配给男性,偶数分配给女性
     *
     * @param idNumberString 待校验的身份证号
     * @return true/false
     */
    private static boolean isFifteenDigitIdNumberValidated(String idNumberString) {
        if (isDigital(idNumberString)) {
            if (startsWithProvinceCode(idNumberString)) {
                String birthDateString = idNumberString.substring(6, 12);
                return isBirthDateValidated(birthDateString, SIX_DIGIT_BIRTH_DATE_PATTERN);
            }
        }
        return false;
    }

    /**
     * 校验18位身份证号
     * 18位身份证号的构成如下:320124 32 江苏 01 南京  24 溧水
     * 省级、直辖市行政区号2位
     * 地、市级行政区号2位
     * 区、县级行政区号2位
     * 出生年4位
     * 出生月2位
     * 出生日2位
     * 序列号3位,其中奇数分配给男性,偶数分配给女性
     * 校验码1位,由前17位号码每位乘以其对应索引位置的加权系数后求和,再根据和除以11的余数作为索引取校验码映射中对应索引的校验码
     * 可参考类内私有常量["加权系数","校验码映射"]
     *
     * @param idNumberString 待校验的身份证号
     * @return true/false
     */
    private static boolean isEighteenDigitIdNumberValidated(String idNumberString) {
        String topSeventeenDigit = idNumberString.substring(0, 17);
        if (isDigital(topSeventeenDigit)) {
            if (startsWithProvinceCode(idNumberString)) {
                String birthDateString = idNumberString.substring(6, 14);
                if (isBirthDateValidated(birthDateString, EIGHT_DIGIT_BIRTH_DATE_PATTERN)) {
                    String checkCode = getCheckCodeByWeightingSum(getWeightingSum(topSeventeenDigit.toCharArray()));
                    return Objects.equals(idNumberString.substring(17, 18), checkCode);
                }
            }
        }
        return false;
    }

    /**
     * 校验字符串是否全部由数字构成
     *
     * @param string 待校验的字符串
     * @return true/false
     */
    private static boolean isDigital(String string) {
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 校验字符串是否以省、直辖市代码开头
     *
     * @param string 待校验的字符串
     * @return true/false
     */
    private static boolean startsWithProvinceCode(String string) {
        String provinceCode = string.substring(0, 2);
        return Arrays.asList(PROVINCE_CODES).contains(provinceCode);
    }

    /**
     * 校验字符串是否为合法的日期格式
     *
     * @param string 待校验的字符串
     * @return true/false
     */
    private static boolean isBirthDateValidated(String string, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            Date birthDate = dateFormat.parse(string);
            String temp = dateFormat.format(birthDate);
            return Objects.equals(temp, string);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 获取加权和
     *
     * @param chars 待计算的字节数组
     * @return 加权和
     */
    private static int getWeightingSum(char[] chars) {
        int weightingSum = 0;
        for (int i = 0; i < chars.length; i++) {
            weightingSum += WEIGHTING_COEFFICIENT[i] * Integer.parseInt(String.valueOf(chars[i]));
        }
        return weightingSum;
    }

    /**
     * 根据加权和获取校验码
     *
     * @param weightingSum 加权和
     * @return 校验码
     */
    private static String getCheckCodeByWeightingSum(int weightingSum) {
        return CHECK_CODE_MAPPING[weightingSum % 11];
    }

    /**
     * 将十五位身份证号码转换为十八位
     *
     * @param idNumber 十五位身份证号码
     * @return 十八位身份证号码
     */
    public static String convertFifteenDigitIdCardToEighteenDigitIdCard(String idNumber) {
        if (isEmpty(idNumber)) {
            throw new IllegalArgumentException("身份证字符不可为空");
        }
        if (idNumber.length() != FIFTEEN) {
            throw new IllegalArgumentException("身份证字符的长度错误,无法进行转换");
        }
        if (!isFifteenDigitIdNumberValidated(idNumber)) {
            throw new IllegalArgumentException("身份证校验失败,无法进行转换");
        }
        if (startsWithProvinceCode(idNumber)) {
            String birthDateString = idNumber.substring(6, 12);
            if (isBirthDateValidated(birthDateString, SIX_DIGIT_BIRTH_DATE_PATTERN)) {
                String topSeventeenDigit = idNumber.substring(0, 6) + getPossibleYear(idNumber) + idNumber.substring(6, 15);
                return topSeventeenDigit + getCheckCodeByWeightingSum(getWeightingSum(topSeventeenDigit.toCharArray()));
            }
        }
        return null;
    }

    /**
     * 根据当前日期判断可能的年限
     * 我国(中华人民共和国)自2004年1月1日起开始换发第二代身份证
     * 根据我国人民在20世纪的平均寿命判断:第一代身份证的身份证号的出生年为为00,01,02,03的居民为2000年后出生
     * 剩余一代身份证居民为1900年后出生
     *
     * @param idNumberString 身份证字符串
     * @return 可能的年限
     */
    private static String getPossibleYear(String idNumberString) {
        int year = Integer.parseInt(idNumberString.substring(7, 9));
        if (year >= 0 && year < 4) {
            return "20";
        } else {
            return "19";
        }
    }

}
