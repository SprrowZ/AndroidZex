package com.example.myappsecond.utils.ExtraUtil;

/**
 * Created by jinyunyang on 15/3/5.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 这些帮助函数让 Java 的某些常用功能变得更简单
 *
 * @author Jerry Li
 */
public class Lang {
    private static final String TAG = "LANG";
    public static String SEPARATOR = ",";
    /** log */
//    private static Log log = LogFactory.getLog(Lang.class);

    /**
     * 私有 构建体方法
     */
    private Lang() {
    }


    /**
     * 实例化指定名称的类，并返回实例对象。如果指定的类名不存在，或者不是指定接口的实现类，则返回null。
     * @param <T>
     *
     * @param interfaceCls 指定接口，或抽象类
     * @param clsName 需要被初始化的类的名称。
     * @return 初始化类的实例对象
     */
    public static <T> T createObject(Class<T> interfaceCls, String clsName) {
        T obj = null;
        @SuppressWarnings("unchecked")
        Class<T> cls = (Class<T>) loadClass(clsName);
        if (cls == null) {
            throw new RuntimeException("实现类：" + clsName + "不存在！");
        } else if (!interfaceCls.isAssignableFrom(cls)) {
            throw new RuntimeException("实现类：" + clsName + "不是 " + interfaceCls.getName()
                    + " 接口的实现类.");
        } else {
            try {
                obj = cls.newInstance();
            } catch (Throwable e) {
                throw new RuntimeException("实现类：" + clsName + "无法实例化！", e);
            }
        }
        return obj;
    }

    /**
     * 装载一个对象并实例化
     * @param clsName 类型
     * @return 实例化的对象
     */
    public static Object createObject(String clsName) {
        try {
            return loadClass(clsName).newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 使用当前线程的ClassLoader加载给定的类
     *
     * @param className 类的全称
     * @return 给定的类
     */
    public static Class<?> loadClass(String className) {
        if ((className == null) || className.trim().length() == 0) {
            Log.e(Lang.class.getName(),"loadClass error, className is null. ");
            return null;
        }
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (ClassNotFoundException e1) {
            try {
                return Class.forName(className);
            } catch (Exception e2) {
                return null;
            }
        }
    }

    /**
     * 反射执行特定的方法
     * @param className 实体类名称
     * @param methodName 方法名
     * @param objs 参数信息
     */
    public static void doMethod(String className, String methodName, Object... objs) {
        doClassMethod(loadClass(className), methodName, objs);
    }

    /**
     * 反射执行特定的方法
     * @param cls 实体类
     * @param mtdName 方法名
     * @param objs 参数信息
     */
    public static void doClassMethod(Class<?> cls, String mtdName, Object... objs) {
        Method method = null;
        Object newClass = null;
        try {
            if (objs.length > 0) {
                @SuppressWarnings("rawtypes")
                Class[] classes = new Class[objs.length];
                for (int i = 0; i < objs.length; i++) {
                    classes[i] = objs[i].getClass();
                }

                method = cls.getMethod(mtdName, classes);
            } else {
                method = cls.getMethod(mtdName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (method != null) {
            try {
                newClass = cls.newInstance();
                if (objs.length > 0) {
                    method.invoke(newClass, objs);
                } else {
                    method.invoke(newClass);
                }
            } catch (Exception e) {
                if (e.getCause() instanceof RuntimeException) {
                    throw (RuntimeException) (e.getCause());
                } else {
                    throw new RuntimeException("执行方法[" + cls.getName() + "." + mtdName + "]错误", e);
                }
            }
        }
    }

    /**
     * 得到随机的22位UUID
     * @return 22位UUID
     */
    public static String getUUID() {
        StringBuilder sb = new StringBuilder("0");
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        sb.append(uuid);
        uuid = hexTo64(sb.toString());
        uuid = uuid.replaceAll("_", randomAlphanumeric(2));
        uuid = uuid.replaceAll("-", randomAlphanumeric(2));
        return uuid;
    }

    /** 下面代码用于将36位的UUID字符串转为22位的字符串，提升系统运行效率 */
    private static final char[] CHAR_MAP;

    static {
        CHAR_MAP = new char[64];
        for (int i = 0; i < 10; i++) {
            CHAR_MAP[i] = (char) ('0' + i);
        }
        for (int i = 10; i < 36; i++) {
            CHAR_MAP[i] = (char) ('a' + i - 10);
        }
        for (int i = 36; i < 62; i++) {
            CHAR_MAP[i] = (char) ('A' + i - 36);
        }
        CHAR_MAP[62] = '_';
        CHAR_MAP[63] = '-';
    }

    public static String randomAlphanumeric (int count) {
        if (count == 0) {
            return "";
        } else if (count < 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        }
        int start = ' ';
        int end = 'z' + 1;

        char[] buffer = new char[count];
        int gap = end - start;

        Random random = new Random();

        while (count-- != 0) {
            char ch = (char) (random.nextInt(gap) + start);
            if (Character.isLetter(ch)
                    ||  Character.isDigit(ch)
                    )
            {
                buffer[count] = ch;
            } else {
                count++;
            }
        }
        return new String(buffer);
    }

    /**
     * 将16进制字符串转换为64进制
     * @param hex 16进制字符串
     * @return 64进制字符串
     */
    private static String hexTo64(String hex) {
        StringBuilder r = new StringBuilder();
        int index = 0;
        final int size = 3;
        int[] buff = new int[size];
        int l = hex.length();
        for (int i = 0; i < l; i++) {
            index = i % size;
            buff[index] = Integer.parseInt("" + hex.charAt(i), 16);
            if (index == 2) {
                r.append(CHAR_MAP[buff[0] << 2 | buff[1] >>> 2]);
                r.append(CHAR_MAP[(buff[1] & size) << 4 | buff[2]]);
            }
        }
        return r.toString();
    }

    /**
     * 转型为整型
     * @param obj 原始对象
     * @param def 缺省值
     *
     * @return 整型
     */
    public static int to(Object obj, int def) {
        if (obj != null) {
            if (obj instanceof Integer) {
                return (Integer) obj;
            } else if (obj instanceof Number) {
                return ((Number) obj).intValue();
            } else if (obj instanceof Boolean) {
                return (Boolean) obj ? 1 : 0;
            } else if (obj instanceof Date) {
                return (int) ((Date) obj).getTime();
            } else {
                try {
                    return Integer.parseInt(obj.toString());
                } catch (Exception e) {
                    try {
                        return (new Double(Double.parseDouble(obj.toString()))).intValue();
                    } catch (Exception e2) {
                        return def;
                    }
                }
            }
        } else {
            return def;
        }
    }

    /**
     * 转型为长整型
     * @param obj 原始对象
     * @param def 缺省值
     *
     * @return 长整型
     */
    public static long to(Object obj, long def) {
        if (obj != null) {
            if (obj instanceof Long) {
                return (Long) obj;
            } else if (obj instanceof Number) {
                return ((Number) obj).longValue();
            } else if (obj instanceof Boolean) {
                return (Boolean) obj ? 1 : 0;
            } else if (obj instanceof Date) {
                return ((Date) obj).getTime();
            } else {
                try {
                    return Long.parseLong(obj.toString());
                } catch (Exception e) {
                    try {
                        return (new Double(Double.parseDouble(obj.toString()))).longValue();
                    } catch (Exception e2) {
                        return def;
                    }
                }
            }
        } else {
            return def;
        }
    }

    /**
     * 转型为双精度浮点型
     * @param obj 原始对象
     * @param def 缺省值
     *
     * @return 双精度浮点型
     */
    public static double to(Object obj, double def) {
        if (obj != null) {
            if (obj instanceof Double) {
                return (Double) obj;
            } else if (obj instanceof Float) {
                return ((Float) obj).doubleValue();
            } else if (obj instanceof Number) {
                return ((Number) obj).doubleValue();
            } else if (obj instanceof Boolean) {
                return (Boolean) obj ? 1 : 0;
            } else if (obj instanceof Date) {
                return ((Date) obj).getTime();
            } else {
                try {
                    return Double.parseDouble(obj.toString());
                } catch (Exception e) {
                    return def;
                }
            }
        } else {
            return def;
        }
    }

    /**
     * 转型为布尔值
     * @param obj 原始对象
     * @param def 缺省值
     *
     * @return 布尔值
     */
    public static boolean to(Object obj, boolean def) {
        if (obj != null) {
            if (obj instanceof Boolean) {
                return ((Boolean) obj).booleanValue();
            } else if (obj instanceof Integer) {
                return ((Integer) obj).intValue() == 1;
            } else if (obj instanceof Long) {
                return ((Long) obj).longValue() == 1;
            } else if (obj instanceof Double) {
                return ((Double) obj).doubleValue() == 1;
            } else if (obj instanceof Date) {
                return ((Date) obj).getTime() == 1;
            } else {
                String str = obj.toString().toUpperCase();
                return str.equalsIgnoreCase("TRUE") || str.equalsIgnoreCase("YES") || str.equals("1");
            }
        } else {
            return def;
        }
    }

    /**
     * 转型为字符串
     * @param obj 原始对象
     * @param def 缺省值
     *
     * @return 字符串
     */
    public static String to(Object obj, String def) {
        if (obj != null) {
            return obj.toString();
        } else {
            return def;
        }
    }

    /**
     * 合并多个数组对象
     * @param <T> 范对象
     * @param arrays 多个数组对象
     * @return 合并后的新数组对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] arrayAppend(T[]... arrays) {
        int size = 0;
        for (T[] arr : arrays) {
            size += arr.length;
        }
        T[] copy = (T[]) new Object[size];
        int pos = 0;
        for (T[] arr : arrays) {
            System.arraycopy(arr, 0, copy, pos, arr.length);
            pos = arr.length;
        }
        return copy;
    }

    /**
     * 把一个字符串数据转换为指定分隔符的字符串，如果数组为空返回空字符串，如果长度为1返回
     * 第一个数据内容。逗号分隔。
     * @param array 字符串数组
     * @return 转换后的字符串
     */
    public static String arrayJoin(String[] array) {
        return arrayJoin(array, SEPARATOR);
    }
	public static String arrayJoin(List<String> list) {
		return arrayJoin(list, SEPARATOR);
	}

    /**
     * 把一个字符串数据转换为指定分隔符的字符串，如果数组为空返回空字符串，如果长度为1返回
     * 第一个数据内容。
     * @param array 字符串数组
     * @param sep 分隔符
     * @return 转换后的字符串
     */
    public static String arrayJoin(String[] array, String sep) {
        if (array == null || array.length == 0) {
            return "";
        } else if (array.length == 1) {
            return array[0];
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; i++) {
                if (array[i] != null) {
                    sb.append(array[i]).append(sep);
                }
            }
            sb.setLength(sb.length() - sep.length());
            return sb.toString();
        }
    }
	public static String arrayJoin(List<String> list, String sep) {
		if (list == null || list.size() == 0) {
			return "";
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < list.size(); i++) {
				String s = list.get(i);
				if (!TextUtils.isEmpty(s)) {
					sb.append(s).append(sep);
				}
			}
			sb.setLength(sb.length() - sep.length());
			return sb.toString();
		}
	}

    /**
     * 判断一个数据是否包含执行值
     * @param array 字符串数组
     * @param value 指定值
     * @return 是否包含指定值
     */
    public static boolean arrayHas(String[] array, String value) {
        boolean has = false;
        if ((array != null) && (array.length > 0) && (value != null)) {
            for (String data : array) {
                if (data.equals(value)) {
                    has = true;
                    break;
                }
            }
        }
        return has;
    }

    /**
     * 对数据进行base64编码
     * @param data 数据
     * @return 编码后的数据
     */
//    public static byte[] encodeBase64(byte[] data) {
//        return Base64.encode(data);
//    }

    /**
     * 对数据进行base64编码
     * @param data 数据
     * @return 编码后的数据
     */
//    public static String encodeBase64(String data) {
//        return new String(Base64.encode(data.getBytes()));
//    }

    /**
     * 对数据进行base64解码
     * @param data 数据
     * @return 解码后的数据
     */
//    public static byte[] decodeBase64(byte[] data) {
//        return Base64.decode(data);
//    }

    /**
     * 对数据进行base64解码
     * @param data 数据
     * @return 解码后的数据
     */
//    public static String decodeBase64(String data) {
//        try {
//            return new String(Base64.decode(data), "UTF-8");
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//
//        return data;
//    }


    /**
     * 二进制字节转16进制字符串
     * @param b 数据
     * @return 转换后数据
     */
    public static String byteTohex(byte b) {
        String stmp = (Integer.toHexString(b & 0XFF));
        if (stmp.length() == 1) {
            stmp = "0" + stmp;
        }
        return stmp;
    }

    /**
     * 二进制字节数组转16进制字符串
     * @param b 数据
     * @return 转换后数据
     */
    public static String byteTohex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < b.length; n++) {
            sb.append(byteTohex(b[n]));
        }
        return sb.toString();
    }

    /**
     * 十六进制字节数组转2进制数组
     * @param b 数据
     * @return 转换后的数据
     */
    public static byte[] hexTobyte(byte[] b) {
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     *
     * @param hexStr 16进制字符串
     * @return 解码后的字符串
     */
    public static String hexToStr(String hexStr) {
        try {
            return new String(hexTobyte(hexStr.getBytes()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(Lang.class.toString(), e.getMessage() + ": " + hexStr, e);
            return "";
        }
    }


    /**
     * 格式化数据，调整小数位数为指定的长度
     * @param inputNumber 需要被格式化的数字
     * @param pattern 要格式化的数字格式，支持#号和%号 例如输入 10000 #0.00 10000.00 # 10000 #,##0.00 10,000.00
     * @see DecimalFormat
     * @return 格式化后的结果
     */
    public static String formatNumber(String inputNumber, String pattern) {
        try {
            DecimalFormat nf = new DecimalFormat(pattern);
            return nf.format(Double.parseDouble(inputNumber));
        } catch (Exception e) {
            Log.e(Lang.class.toString(), "formatNumber error:" + inputNumber + " pattern:" + pattern, e);
            return inputNumber;
        }
    }

    /**
     * 根据指定小数位数格式化数据
     *
     * @param inputNumber 需要被格式化的数字
     * @param dec 要保留的小数位数
     *
     * @return 格式化后的结果
     */
    public static String formatNumber(String inputNumber, int dec) {
        StringBuilder pattern = new StringBuilder();
        pattern.append("0.");

        for (int i = 0; i < dec; i++) {
            pattern.append("0");
        }

        inputNumber = formatNumber(inputNumber, pattern.toString());

        if (dec == 0) {
            return inputNumber.substring(0, inputNumber.length() - 1);
        } else {
            return inputNumber;
        }
    }

    /**
     * 判断对象是否为数字
     *
     * @param inputNumber 需要被格式化的数字
     * @return 是否为数字
     */
    public static boolean isNum(Object inputNumber) {
        if (inputNumber == null) {
            return false;
        } else {
            return inputNumber instanceof Number;
        }
    }

    /**
     * 返回Object对象列表
     * @param values 对象数组，支持多个
     * @return 对象列表
     */
    public static <T> List<T> asList(T... values) {
        List<T> list = new ArrayList<T>();
        Collections.addAll(list, values);
        return list;
    }

    /**
     *
     * @param list 字符串列表
     * @param sep 分隔符
     * @return 使用指定的分隔符合并列表中的字符串
     */
    public static String listJoin(List<String> list, String sep) {
        if(list == null || list.size() == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for(String str:list) {
            result.append(sep);
            result.append(str);
        }

        if(result.length() == 0) {
            return "";
        }

        return result.substring(sep.length());
    }

    /**
     * 删除input字符串中的html格式
     *
     * @param input - source
     * @param length - length
     * @return - no html tag summary
     */
    public static String getSummary(String input, int length) {
        if (input == null || input.trim().equals("")) {
            return "";
        }
        input = input.replace("\n", "");
        input = input.replace("\t", "");
        input = input.replace(" ", "");
        // 去掉所有html元素,
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
                "<[^>]*>", "");
        str = str.replaceAll("[(/>)<]", "");
        int len = str.length();
        if (len <= length) {
            return str;
        } else {
            str = str.substring(0, length);
            str += "......";
        }
        return str;
    }


    /**
     * subString
     * @param text - text
     * @param preTag - prefix str
     * @param postTag - post str
     * @return target content
     */
    public static String subString(String text, String preTag, String postTag) {
        String result = "";
        String pn = preTag + "(.+?)" + postTag;
        Pattern pattern = Pattern.compile(pn);
        Matcher mt = pattern.matcher(text);
        while (mt.find()) {
            result = mt.group(1);
        }
        return result;
    }


    /**
     * get file md5 check sum
     *
     * @param is - input stream
     * @return md5 string
     * @throws NoSuchAlgorithmException - md5 checksum exception
     * @throws IOException - io exception
     */
    public static String getMd5checksum(InputStream is) throws NoSuchAlgorithmException, IOException {
        byte[] digest = createChecksum(is);
        String result = "";
        for (int i = 0; i < digest.length; i++) {
            result += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    /**
     * create md5 checksum
     *
     * @param fis - inputStream
     * @return checksum string
     * @throws NoSuchAlgorithmException - md5 checksum exception
     * @throws IOException - io exception
     */
    private static byte[] createChecksum(InputStream fis) throws NoSuchAlgorithmException, IOException {
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        return complete.digest();
    }


    public static String fileToBase64(File file) {
        InputStream is = null;
        String base64 = null;
        try {
            is = new FileInputStream(file);
            byte[] data = IOUtils.toByteArray(is);
            base64 = Base64.encodeToString(data, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
	    /*File ff = new File(StorageHelper.getInstance().getImagePath(), "1.txt");
	    try {
		    FileWriter writer = new FileWriter(ff);
		    writer.write(base64);
		    writer.close();
	    } catch (Exception e) {
		    e.printStackTrace();
	    }*/

        return base64;
    }

	public static String imageToBase64(File image) {
		String base64 = "";
		if (image != null && image.exists()) {
			InputStream inputStream = null;//You can get an inputStream using any IO API
			Base64OutputStream output64 = null;
			try {
				inputStream = new FileInputStream(image.getAbsolutePath());
				byte[] buffer = new byte[8192];
				int bytesRead;
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				output64 = new Base64OutputStream(output, Base64.DEFAULT);
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					output64.write(buffer, 0, bytesRead);
				}
				output.close();
				base64 = output.toString();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(output64);
				IOUtils.closeQuietly(inputStream);
			}
		}
		return base64;
	}

    public static boolean base64ToFile(String base64Str, File file) {
         boolean success = false;
        OutputStream out = null;
        try {
            byte[] bytes = Base64.decode(base64Str, Base64.DEFAULT);
            out = new FileOutputStream(file);
            out.write(bytes);
            out.flush();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }
        return success;
    }

    /**
     * bitmap转为base64
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        try {
            byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (Exception e) {
            //EMLog.d(TAG, "Error Base64 Image.");
        }
        return null;
    }

//	public static String getContentFromRawFile(int resId) {
//		InputStream is = EChatApp.getInstance().getBaseContext()
//				.getResources().openRawResource(resId);
//		Writer writer = new StringWriter();
//		char[] buffer = new char[1024];
//		try {
//			Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//			int n;
//			while ((n = reader.read(buffer)) != -1) {
//				writer.write(buffer, 0, n);
//			}
//		} catch (Exception e) {
//			EMLog.e(TAG, e.getMessage(), e);
//		} finally {
//			IOUtils.closeQuietly(is);
//		}
//
//		return writer.toString();
//	}

//    public static String getContentFromLocalFile(File file) {
//        if (file == null) {
//            return "";
//        }
//
//        InputStream is = null;
//        Writer writer = new StringWriter();
//        char[] buffer = new char[1024];
//        try {
//            is = new FileInputStream(file);
//            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            int n;
//            while ((n = reader.read(buffer)) != -1) {
//                writer.write(buffer, 0, n);
//            }
//        } catch (Exception e) {
//            EMLog.e(TAG, e.getMessage(), e);
//        } finally {
//            IOUtils.closeQuietly(is);
//        }
//
//        return writer.toString();
//    }

//	public static int getDrawableFromFileName(String name) {
//		Context context = EChatApp.getInstance().getBaseContext();
//		return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
//	}

    /**
     * 指定的APP是否安装
     */
//    public static boolean isAppInstalled(String uri) {
//        PackageManager pm = EChatApp.getInstance().getPackageManager();
//        boolean installed;
//        try {
//            pm.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
//            installed =true;
//        } catch(PackageManager.NameNotFoundException e) {
//            installed =false;
//        }
//        return installed;
//    }

}
