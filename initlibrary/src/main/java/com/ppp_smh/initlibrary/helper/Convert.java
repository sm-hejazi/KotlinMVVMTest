package com.ppp_smh.initlibrary.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class Convert {

    private static final String SQLITE_TYPE_INTEGER = "INTEGER";
    private static final String SQLITE_TYPE_TEXT = "TEXT";
    private static final String SQLITE_TYPE_REAL = "REAL";
    private static final String SQLITE_TYPE_NONE = "NONE";

    private static final String TAG = "Convert";

    public static <T> String entityToPostData(T t) throws Exception {
        try {
            String postData = "";
            for (Field field : getStandardFields(t.getClass())) {
                field.setAccessible(true);
                postData += URLEncoder.encode(field.getName(), "UTF-8") + "=" +
                        URLEncoder.encode(field.get(t).toString(), "UTF-8") + "&";
            }

            if (postData.length() > 0)
                postData = postData.substring(0, postData.length() - 1);

            return postData;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            throw ex;
            //return null;
        }
    }

    public static <T> List<T> beanToList(T t) {
        List<T> beans = new ArrayList<>();
        beans.add(t);
        return beans;
    }

    public static <T> HashMap entityToHashMap(T t) throws Exception {
        HashMap params = new HashMap();
        try {
            if (t == null) return params;

            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                if (field.get(t) == null) continue;
                // Remove static final field to prevent sending "serialVersionUID" field.
                if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()))
                    continue;

                params.put(field.getName(), field.get(t));
            }

            return params;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            throw ex;
            //return params;
        }
    }


    public static <T> String entityToJsonString(List<T> lstT) throws Exception {
        try {
            Gson gson = new Gson();
            return gson.toJson(lstT);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            throw ex;
            //return null;
        }
    }

    public static <T> String entityToJsonString(T t) throws Exception {
        try {
            Gson gson = new Gson();
            return gson.toJson(t);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            throw ex;
            //return null;
        }
    }

    public static String mapToJson(Map<String, Object> keyValue) {
        Gson gson = new Gson();
        return gson.toJson(keyValue);
    }

    public static String mapToPostData(Map<String, String> keyValue) throws Exception {
        try {
            String postData = "";
            Iterator<Map.Entry<String, String>> iterator = keyValue.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> pairs = iterator.next();
                String value = pairs.getValue();
                String key = pairs.getKey();
                postData += URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8") + "&";
            }

            if (postData.length() > 0)
                postData = postData.substring(0, postData.length() - 1);

            return postData;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            throw ex;
            //return null;
        }
    }

    public static String streamToString(InputStream inputStream) throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
            }

            return sb.toString();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            throw ex;
            //return null;
        }
    }


    public static <T> List<T> jsonArrayToEntityList(JSONArray jsonArray, Class<T> typeParameterClass) throws Exception {
        return jsonArrayToEntityList(jsonArray.toString(), typeParameterClass);
    }

    private static <T> List<T> jsonArrayToEntityList(String jsonArray, Class<T> typeParameterClass) throws Exception {
        try {
            Gson gson = new Gson();

            Type listType = new TypeToken<List<Map>>() {}.getType();
            List<Map> tmpList = gson.fromJson(jsonArray, listType);
            List<T> lstT = new ArrayList<>(tmpList.size());
            for (Map map : tmpList) {
                String tmpJson = gson.toJson(map);
                lstT.add(gson.fromJson(tmpJson, typeParameterClass));
            }

            return lstT;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            throw ex;
            //return null;
        }
    }

    public static <T> T jsonObjectToEntity(JSONObject jsonObject, Class<T> typeParameterClass) throws Exception {
        return jsonObjectToEntity(jsonObject.toString(), typeParameterClass);
    }

    private static <T> T jsonObjectToEntity(String jsonObject, Class<T> typeParameterClass) throws Exception {
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonObject, typeParameterClass);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            throw ex;
            //return null;
        }
    }

    public static JSONObject stringToJSONObject(String jsonObject) {
        try {
            return new JSONObject(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> cursorToEntity(Cursor cursor, Class<T> typeParameterClass) throws Exception {
        try {
            String[] columnNames = cursor.getColumnNames();
            List<T> lstT = new ArrayList<>();
            while (cursor.moveToNext()) {
                T t = typeParameterClass.newInstance();
                for (String columnName : columnNames) {
                    try {
                        //Ignore case find field
                        Field field = null;
                        List<Field> fields = getStandardFields(t.getClass());
                        for (Field f : fields) {
                            if (f.getName().equalsIgnoreCase(columnName)) {
                                field = f;
                                break;
                            }
                        }

                        if (field == null) {
                            Log.w(TAG, "Field \"" + columnName + "\" not found in " + typeParameterClass.getName());
                            continue;
                        }

                        Object value = getCursorValue(cursor, columnName, field.getType());
                        field.setAccessible(true);
                        field.set(t, value);

                    } catch (Exception ex) {
                        Log.w(TAG, ex.toString());
                    }
                }
                lstT.add(t);
            }
            return lstT;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            throw ex;
        }
    }

    private static Object getCursorValue(Cursor cursor, String columnName, Class<?> type) throws Exception {
        Object value;
        try {
            int columnIndex = cursor.getColumnIndex(columnName);

            if (cursor.isNull(columnIndex)) {
                value = null;
            } else if (type.equals(Integer.class) || type.equals(int.class)) {
                value = cursor.getInt(columnIndex);
            } else if (type.equals(long.class) || type.equals(Long.class)) {
                value = cursor.getLong(columnIndex);
            } else if (type.equals(char.class) || type.equals(String.class) ||
                    type.equals(CharSequence.class) || type.equals(Character.class)) {
                value = cursor.getString(columnIndex);
            } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                if (cursor.getShort(columnIndex) == 1) value = true;
                else if (cursor.getShort(columnIndex) == 0) value = false;
                else value = null;
            } else if (type.equals(short.class) || type.equals(Short.class)) {
                value = cursor.getShort(columnIndex);
            } else if (type.equals(Double.class) || type.equals(double.class)) {
                value = cursor.getDouble(columnIndex);
            } else if (type.equals(Float.class) || type.equals(float.class)) {
                value = cursor.getFloat(columnIndex);
            } else if (type.equals(Byte[].class) || type.equals(byte[].class)) {
                value = cursor.getBlob(columnIndex);
            } else {
                throw new Exception("Can't find proper casting method data type for " + type.getName());
            }

            return value;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            throw ex;
        }
    }

    public static Object getPropertiesValue(Properties properties, String propertyName, Class<?> type) throws Exception {
        Object value;
        try {
            if (!properties.containsKey(propertyName)) {
                return null;
            }
            String strValue = properties.getProperty(propertyName);

            if (type.equals(Integer.class) ||
                    type.equals(int.class)) {
                value = Integer.parseInt(strValue);
            } else if (type.equals(long.class) ||
                    type.equals(Long.class)) {
                value = Long.parseLong(strValue);
            } else if (type.equals(char.class) ||
                    type.equals(String.class) ||
                    type.equals(CharSequence.class) ||
                    type.equals(Character.class)) {
                value = strValue;
            } else if (type.equals(Boolean.class) ||
                    type.equals(boolean.class) ||
                    type.equals(short.class) ||
                    type.equals(Short.class)) {
                value = Boolean.parseBoolean(strValue);
            } else if (type.equals(Double.class) ||
                    type.equals(double.class)) {
                value = Double.parseDouble(strValue);
            } else {
                throw new Exception("Can't find proper casting method data type for " + type.getName());
            }

            return value;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            throw ex;
        }
    }

    public static <T> ContentValues entityToContentValues(T t, boolean skipNull) throws Exception {
        try {
            ContentValues contentValues = new ContentValues();

            for (Field field : getStandardFields(t.getClass())) {
                field.setAccessible(true);
                String key = field.getName();
                Object value = field.get(t);

                if (skipNull && value == null) continue;

                if (value == null) {
                    contentValues.putNull(key);
                    continue;
                }

                Class<?> type = field.getType();
                if (type.equals(Integer.class) || type.equals(int.class)) {
                    contentValues.put(key, (Integer) value);
                } else if (type.equals(long.class) || type.equals(Long.class)) {
                    contentValues.put(key, (Long) value);
                } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                    contentValues.put(key, (Boolean) value);
                } else if (type.equals(short.class) || type.equals(Short.class)) {
                    contentValues.put(key, (Short) value);
                } else if (type.equals(char.class) || type.equals(String.class) ||
                        type.equals(CharSequence.class) || type.equals(Character.class)) {
                    contentValues.put(key, (String) value);
                } else if (type.equals(float.class) || type.equals(Float.class)) {
                    contentValues.put(key, (Float) value);
                } else if (type.equals(byte.class) || type.equals(Byte.class)) {
                    contentValues.put(key, (Byte) value);
                } else if (type.equals(byte[].class) || type.equals(Byte[].class)) {
                    contentValues.put(key, (byte[]) value);
                } else if (type.equals(double.class) || type.equals(Double.class)) {
                    contentValues.put(key, (Double) value);
                }

            }

            return contentValues;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            throw ex;
            //return null;
        }
    }

    private static String javaToSqliteType(Class<?> type) {
        if (type.equals(Integer.class) ||
                type.equals(int.class) ||
                type.equals(long.class) ||
                type.equals(Long.class) ||
                type.equals(Boolean.class) ||
                type.equals(boolean.class) ||
                type.equals(short.class) ||
                type.equals(Short.class)) {
            return SQLITE_TYPE_INTEGER;
        } else if (type.equals(char.class) ||
                type.equals(String.class) ||
                type.equals(CharSequence.class) ||
                type.equals(Character.class)) {
            return SQLITE_TYPE_TEXT;
        } else if (type.equals(float.class) ||
                type.equals(Float.class) ||
                type.equals(double.class) ||
                type.equals(Double.class)) {
            return SQLITE_TYPE_REAL;
        } else {
            Log.w(TAG, "Sqlite type not found for " + type.getName());
            return SQLITE_TYPE_NONE;
        }
    }

    public static boolean isSqliteType(Class<?> type) {
        return javaToSqliteType(type).equals(SQLITE_TYPE_NONE);
    }

    private static boolean isJavaType(Class<?> type) {
        return type.equals(Integer.class) ||
                type.equals(int.class) ||
                type.equals(long.class) ||
                type.equals(Long.class) ||
                type.equals(Boolean.class) ||
                type.equals(boolean.class) ||
                type.equals(short.class) ||
                type.equals(Short.class) ||
                type.equals(char.class) ||
                type.equals(String.class) ||
                type.equals(CharSequence.class) ||
                type.equals(Character.class) ||
                type.equals(float.class) ||
                type.equals(Float.class) ||
                type.equals(Double.class) ||
                type.equals(double.class) ||
                type.equals(byte.class) ||
                ((type.isEnum())) ||
                type.equals(Byte.class);
    }

    public static Object stringToJavaType(Class aClass, String value) throws Exception {
        if (Boolean.class == aClass || Boolean.TYPE == aClass) return Boolean.parseBoolean(value);
        if (Byte.class == aClass || Byte.TYPE == aClass) return Byte.parseByte(value);
        if (Short.class == aClass || Short.TYPE == aClass) return Short.parseShort(value);
        if (Integer.class == aClass || Integer.TYPE == aClass) return Integer.parseInt(value);
        if (Long.class == aClass || Long.TYPE == aClass) return Long.parseLong(value);
        if (Float.class == aClass || Float.TYPE == aClass) return Float.parseFloat(value);
        if (Double.class == aClass || Double.TYPE == aClass) return Double.parseDouble(value);
        if (String.class == aClass) return value;

        Log.e(TAG, "Unable to cast " + aClass.getName());
        throw new Exception("Unable to cast " + aClass.getName());
        //return value;
    }

    public static <T> T[] listToArray(Class<T> c, List<T> list) {
        T[] result = (T[]) Array.newInstance(c, list.size());
        for (int i = 0; i < list.size(); i++)
            result[i] = list.get(i);

        return result;
    }

    public static String listToString(Iterable<?> strings, String separator) {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (Object s : strings) {
            sb.append(sep).append(s.toString());
            sep = separator;
        }
        return sb.toString();
    }

    private static String doubleToString(double d) {
        if (d == (long) d)
            return String.format(Locale.US, "%d", (long) d);
        else
            return String.format("%s", d);
    }

    public static String doubleToString(double d, int precision) {
        String value = doubleToString(d);
        char separator = new DecimalFormatSymbols(Locale.US).getDecimalSeparator();
        int pointLocation = value.lastIndexOf(separator);
        if (pointLocation == -1) return value;
        if (pointLocation + precision >= value.length()) return value;
        return doubleToString(Double.parseDouble(value.substring(0, pointLocation + precision + 1)));
    }

    private static List<Field> getStandardFields(Class<?> typeParameterClass) {
        List<Field> lstField = new ArrayList<>();
        for (Field field : typeParameterClass.getDeclaredFields()) {
            try {
                if (!isJavaType(field.getType())) continue;
                if (Modifier.isFinal(field.getModifiers())) continue;
                if (Modifier.isPrivate(field.getModifiers())) continue;

                lstField.add(field);
            } catch (Exception ex) {
                Log.w(TAG, ex.toString());
            }
        }

        return lstField;
    }

    public static byte[] imageToByteArray(Bitmap bitmap) {

        //Uri To bitmap
//        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
//        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static String byteArrayToBase64(byte[] array) {
        return Base64.encodeToString(array, Base64.DEFAULT);
    }

    public static Bitmap base64ToBitmap(String base64Image) {
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static String dashSeparate(String paramString) {
        int i = 0;
        if ((paramString == null) || (paramString.trim().length() == 0)) {
            return "";
        }
        StringBuilder localStringBuilder = new StringBuilder(paramString);
        int j = paramString.length();
        for (int k = 0; k < j; k++) {
            if ((k % 4 == 0) && (k != 0)) {
                localStringBuilder.insert(k + i, "-");
                i++;
            }
        }
        return localStringBuilder.toString();
    }

    public static String currencySeparate(String num){
        if (num.length() > 0) {
            String str = num.replaceAll("\\D", "");
            num = NumberFormat.getNumberInstance(Locale.US).format(Double.parseDouble(str));
        }
        return num;
    }

    public static String secureCardNumber(String paramString) {
        if ((paramString == null) || (paramString.trim().length() != 16)) {
            return "";
        }
        StringBuilder localStringBuilder = new StringBuilder(paramString);


        return localStringBuilder.replace(6,12,"******").toString();
    }

    public static Bitmap viewToBitmap(View view, int totalHeight, int totalWidth) {
        int height = Math.min(3000, totalHeight);
        float percent = height / (float) totalHeight;
        Bitmap canvasBitmap = Bitmap.createBitmap((int) (totalWidth * percent), (int) (totalHeight * percent), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        canvas.save();
        canvas.scale(percent, percent);
        view.draw(canvas);
        canvas.restore();
        return canvasBitmap;
    }

}