package com.larkersos.exif.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <b>描述：</b>
 *
 * @author wangwenlong
 * @version Created by on 2018/7/16.
 */
@Controller
public class IndexController {

    public static void main(String[] args) {

        String imagePath = "E:\\wwl\\月华\\CF003244.jpg";
        imagePath = "E:\\wwl\\IMG_4377.jpg";
        File file = new File(imagePath);
        String lng = "";
        String lat = "";

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);

            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    String tagName = tag.getTagName();  //标签名
                    String desc = tag.getDescription(); //标签信息
                    if (tagName.equals("Image Height")) {
                        System.out.println("图片高度: "+desc);
                    } else if (tagName.equals("Image Width")) {
                        System.out.println("图片宽度: "+desc);
                    } else if (tagName.equals("Date/Time Original")) {
                        System.out.println("拍摄时间: "+desc);
                    }else if (tagName.equals("GPS Latitude")) {
                        System.err.println("纬度 : "+desc);
                        lng = pointToLatlong(desc);
                        System.err.println("纬度(度分秒格式) : "+lng);
                    } else if (tagName.equals("GPS Longitude")) {
                        System.err.println("经度: "+desc);
                        lat = pointToLatlong(desc);
                        System.err.println("经度(度分秒格式): "+lat);
                    }
                }
            }

            if (lng.trim().length()>0 && lat.trim().length()>0){
                try{
//                    URL url = new URL("http://api.map.baidu.com/geocoder?location="+ lat+","+lng+"&output=json&key="+"E4805d16520de693a3fe707cdc962045");
                    String yourak= "hHG9DtkookdDWGM2G1xaoOH4pbV501eB";
                    String yoursk = "jONkcg3sMH05kv5xC0HS48nO8CaK0ukw";
                    Map paramsMap = new LinkedHashMap<String, String>();
//                    paramsMap.put("address", "百度大厦");
                    paramsMap.put("output", "json");
                    paramsMap.put("ak", yourak);
                    paramsMap.put("location",lat + "," + lng);
//                    paramsMap.put("location","39.983424,116.322987");
//                    paramsMap.put("callback","renderReverse");
                    paramsMap.put("output","json");
                    paramsMap.put("pois","1");

                    // 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
                    String paramsStr = toQueryString(paramsMap);
                    System.out.println("paramsStr = "+paramsStr);
                    String wholeStr = new String("/geocoder/v2/?" + paramsStr + yoursk);

                    // 对上面wholeStr再作utf8编码
                    String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
                    // 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
                    String sn= MD5(tempStr);
                    System.out.println("SN = "+sn);


                    String urlStr = "http://api.map.baidu.com/geocoder/v2/?"+paramsStr+"&sn="+sn;
//                    String urlStr = "http://api.map.baidu.com/geocoder/v2/?ak="+yourak+"&coordtype=wgs84ll&callback=renderReverse&location="+ lat + "," + lng + "&output=json&pois=1";

//                    URL url = new URL("http://api.map.baidu.com/geocoder/v2/?ak=&callback=renderReverse&location="+ lat + "," + lng + "&output=json&pois=1&sn=");

                    URL url = new URL(urlStr);
                    HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
                    ucon.connect();

                    InputStream in = ucon.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
                    String str = reader.readLine();
                    System.err.println("百度地理位置请求结果：" + str);

//                    str = str.substring(str.indexOf("(") + 1, str.length()-1);
                    //outprint.print(str);

                    JSONObject jsonObject = JSON.parseObject(str);
                    if (jsonObject.containsKey("result")){
                        String address = jsonObject.getJSONObject("result").getString("formatted_address");
                        System.err.println("百度地理位置为：" + address);
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            // print(metadata, "Using ImageMetadataReader");
        } catch (ImageProcessingException e) {
            print(e);
        } catch (IOException e) {
            print(e);
        }
    }

    // 对Map内所有value作utf8编码，拼接返回结果
    public static String toQueryString(Map<?, ?> data)
            throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (Map.Entry<?, ?> pair : data.entrySet()) {
            queryString.append(pair.getKey() + "=");
            queryString.append(URLEncoder.encode((String) pair.getValue(),
                    "UTF-8") + "&");
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }


    // 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    /**
     * Write all extracted values to stdout.
     */
    private static void print(Metadata metadata, String method)
    {
        System.out.println();
        System.out.println("-------------------------------------------------");
        System.out.print(' ');
        System.out.print(method);
        System.out.println("-------------------------------------------------");
        System.out.println();

        //
        // A Metadata object contains multiple Directory objects
        //
        for (Directory directory : metadata.getDirectories()) {

            //
            // Each Directory stores values in Tag objects
            //
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }

            //
            // Each Directory may also contain error messages
            //
            for (String error : directory.getErrors()) {
                System.err.println("ERROR: " + error);
            }
        }
    }

    private static void print(Exception exception)
    {
        System.err.println("EXCEPTION: " + exception);
    }



    /**
     * 经纬度格式  转换为  度分秒格式 ,如果需要的话可以调用该方法进行转换
     * @param point 坐标点
     * @return
     */
    public static String pointToLatlong (String point ) {
        Double du = Double.parseDouble(point.substring(0, point.indexOf("°")).trim());
        Double fen = Double.parseDouble(point.substring(point.indexOf("°")+1, point.indexOf("'")).trim());
        Double miao = Double.parseDouble(point.substring(point.indexOf("'")+1, point.indexOf("\"")).trim());
        Double duStr = du + fen / 60 + miao / 60 / 60 ;
//        return duStr.toString();
        NumberFormat nbf=NumberFormat.getInstance();
        nbf.setMinimumFractionDigits(8);
        ;
        return nbf.format(duStr);
    }
}
