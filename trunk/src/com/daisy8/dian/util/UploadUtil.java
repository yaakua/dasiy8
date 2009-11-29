package com.daisy8.dian.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 阳葵
 * Date: 2009-6-15
 * Time: 10:12:27
 * 文件上传工具类
 */
public class UploadUtil {
    /**
     * CSS文件类型,只能是css类型
     */
    public final static byte CSS_TYPE = 1;
    private final static String CSS_EXT = "css";
    /**
     * 图片文件类型，允许的格式为：jpg,jpeg,bmp,gif,png,包括大写
     */
    public final static byte IMG_TYPE = 2;
    //定义一个数组，用于保存可上传的文件类型
    private final static List<String> IMG_EXT = new ArrayList<String>();
    /**
     * FLASH格式，只能是swf类型
     */
    public final static byte SWF_TYPE = 3;
    private final static String SWF_EXT = "swf";
    /**
     * 图片与FLASH格式都可以
     */
    public final static byte SWF_IMG_TYPE = 4;

    public final static String PRODUCT_SHOW_XML_NAME = "indexshop.xml";


    static {
        IMG_EXT.add("jpg");
        IMG_EXT.add("jpeg");
        IMG_EXT.add("bmp");
        IMG_EXT.add("gif");
        IMG_EXT.add("png");
    }

    /**
     * 保存上传的文件，并返回该文件的相对路径
     *
     * @param toPath        要保存文件路径，
     * @param multipartFile 需要保存的文件
     * @param saveFileName  需要保存的文件名，如果此文件名已存在，则自动按日期加999内的随机数生成文件名
     * @return 文件保存后的相对路径
     * @throws Exception 异常
     */
    public static String uploadFile(String toPath, MultipartFile multipartFile, String saveFileName) throws Exception {
        String fileName = "";
        try {
            fileName = multipartFile.getOriginalFilename();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            ext = ext.toLowerCase();
            fileName = saveFileName + "." + ext;
            File f = new File(toPath, fileName);
            //如果文件名已存在，则使用日期加随机数生成文件名
            while (f.exists()) {
//                fileName = CreateCode.getCode2() + "." + ext;
                f = new File(toPath, fileName);
            }
            if (f.mkdirs()) {
                multipartFile.transferTo(f);
            }
        } catch (Exception e) {
            throw e;
        }
        return fileName;
    }

    /**
     * 保存上传的文件到目录，并返回该文件文件名
     *
     * @param toPath        要保存文件路径，
     * @param multipartFile 需要保存的文件
     * @param saveFileName  需要保存的文件名，如果此文件名已存在，则自动按日期加999内的随机数生成文件名
     * @return 文件保存后的相对路径
     * @throws Exception 异常
     */
    public static String uploadTempFile(String toPath, MultipartFile multipartFile, String saveFileName) throws Exception {
        String fileName = "";
        try {
            fileName = multipartFile.getOriginalFilename();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            ext = ext.toLowerCase();
            fileName = "tmp_" + saveFileName + "." + ext;
            File f = new File(toPath, fileName);
            //如果文件名已存在，则使用日期加随机数生成文件名
            while (f.exists()) {
//                fileName = "tmp_" + CreateCode.getCode2() + "." + ext;
                f = new File(toPath, fileName);
            }
            if (f.mkdirs()) {
                multipartFile.transferTo(f);
            }
        } catch (Exception e) {
            throw e;
        }
        return fileName;
    }

    /**
     * 上传XML文档,上传成功返回true,失败返回false
     * wujinyuan
     *
     * @param toPath        要保存文件路径，
     * @param multipartFile 需要保存的文件
     * @param saveFileName  需要保存的文件名，如果此文件名已存在，则自动替换掉原来文件
     * @return 文件保存后的相对路径
     * @throws Exception 异常
     */
    public static boolean uploadXMLFile(String toPath, MultipartFile multipartFile, String saveFileName) throws Exception {
        String fileName = "";
        try {
            fileName = multipartFile.getOriginalFilename();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            ext = ext.toLowerCase();
            if ("xml".equals(ext)) {
                fileName = saveFileName;
            } else {
                return false;
            }
            //不存在则创建,存在则替换
            File f = new File(toPath, fileName);
            if (f.mkdirs()) {
                multipartFile.transferTo(f);
            }
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    /**
     * 从临时目录当中复制文件到指定目录
     *
     * @param filePath  要保存的文件目录
     * @param fileNames 要复制的文件名数组
     * @throws Exception 异常
     */
    public static void copyFileFromTemp(String filePath, String[] fileNames) throws Exception {
        try {
            String imgTempPath = "";
            if (filePath.endsWith("/")) {
//                imgTempPath = filePath + ConfigUtil.IMAGE_TEMP;
            } else {
//                imgTempPath = filePath + "/" + ConfigUtil.IMAGE_TEMP;
            }
            //临时目录文件夹
            File imgTempFile = new File(imgTempPath);
            File[] tmpFiles = imgTempFile.listFiles();
            if (fileNames != null && tmpFiles != null) {
                //临时目录下的文件
                for (File tmpFile : tmpFiles) {
                    String currFileName = tmpFile.getName();
                    for (String fromPath : fileNames) {
                        if (fromPath.equals(currFileName)) {
                            UploadUtil.copyPath(tmpFile, filePath);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private static void copyPath(File fromFile, String toPath) throws Exception {
        //读取文件流
        FileInputStream in = null;
        //写入文件流
        FileOutputStream out = null;
        try {
            in = new FileInputStream(fromFile);
            if (!toPath.endsWith("/")) {
                toPath += "/";
            }
            out = new FileOutputStream(toPath + fromFile.getName());
            int num;
            byte[] b = new byte[65536];
            num = in.read(b, 0, 65536);
            while (num != -1) {
                out.write(b, 0, num);
                num = in.read(b, 0, 65536);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException ee) {
                throw ee;
            }
        }
    }

    /**
     * 指定图片的高度与宽度，只能上传图片。并返回该文件的相对路径
     *
     * @param toPath        要保存图片路径
     * @param multipartFile 需要保存的图片
     * @param saveFileName  需要保存的图片名，如果此图片名已存在，则自动按日期加999内的随机数生成图片名
     * @param width         图片的宽度，如果不限定，则可以设置一大数值
     * @param height        图片的高度，如果不限定，则可以设置一大数值
     * @return 图片保存后的相对路径
     * @throws Exception Exception
     */

    public static String uploadFile(String toPath, MultipartFile multipartFile, String saveFileName, Integer width, Integer height) throws Exception {
        String fileName = "";
        try {
            fileName = multipartFile.getOriginalFilename();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            ext = ext.toLowerCase();
            if (UploadUtil.IMG_EXT.contains(ext)) {
                fileName = saveFileName + "." + ext;
                File f = new File(toPath, fileName);
                //如果文件名已存在，则使用日期加随机数生成文件名
                while (f.exists()) {
//                    fileName = CreateCode.getCode2() + "." + ext;
                    f = new File(toPath, fileName);
                }
                if (f.mkdirs()) {
//                    IMGUtil.saveImg(multipartFile, f.getPath(), width, height);
                }
            } else {
                throw new RuntimeException("不能使用此方法上传非图片格式的文件");
            }
        } catch (Exception e) {
            throw e;
        }
        return fileName;
    }


    /**
     * 生成缩略图
     *
     * @param toPath   保存路径
     * @param fromPath 源文件路径
     * @param width    宽度
     * @param height   高度
     * @return
     * @throws Exception
     */
    public static String createMinImg(String toPath, String fromPath, Integer width, Integer height) throws Exception {
        String ext = fromPath.substring(fromPath.lastIndexOf(".") + 1, fromPath.length());
        ext = ext.toLowerCase();
//        String fileName = "tmp_"+CreateCode.getCode()+"_min."+ext;
//        toPath+="/"+fileName;
        try {
//            IMGUtil.saveImg(fromPath, toPath, width, height);
        } catch (Exception e) {
            throw e;
        }
        return "";
    }

    /**
     * /
     * 删除指定的文件，返回删除是否成功
     *
     * @param filePath 要删除的文件路径，请使用绝对路径如：d:\1.jpg
     * @return Boolean 删除是否成功，如果文件不存在，或不是文件，或删除时异常，则返回为false
     */
    public static Boolean deleteFile(String filePath) {
        try {
            File f = new File(filePath);
            if (f.isFile()) {
                return f.delete();
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 更改文件名，去掉文件名中的tmp_
     *
     * @param filePath 要更改的文件路径
     * @return 是否成功
     */
    public static Boolean rename(String filePath) {
        try {
            File f = new File(filePath);
            if (f.isFile()) {
                String reFilePath = filePath.replace("tmp_", "");
                File f2 = new File(reFilePath);
                return f.renameTo(f2);
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 更改文件名，去掉文件名中的tmp_
     *
     * @param filePath 要更改的文件路径
     * @return 是否成功
     */
    public static Boolean rename(String filePath, String oldName, String newName) {
        try {
            File f = new File(filePath);
            if (f.isFile()) {
                if (filePath.indexOf(oldName) != -1) {
                    String reFilePath = filePath.replace(oldName, newName);
                    File f2 = new File(reFilePath);
                    return f.renameTo(f2);
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 删除某个文件下的所有文件，包括子文件夹
     *
     * @param dirPath 要删除的文件夹路径
     * @return 删除是否成功，如果其中有一个文件未删除成功而导致异常则会返回FALSE，但在此前的文件已被删除，无法恢复
     */
    public static Boolean deleteFileFromDir(String dirPath) {
        try {
            File f = new File(dirPath);
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                if (files.length > 0) {
                    for (int i = 0; i < files.length; i++) {
                        if (files[i].isFile()) {
                            files[i].delete();
                        } else if (files[i].isDirectory()) {
                            if (files[i].listFiles().length > 0) {
                                deleteFileFromDir(files[i].getPath());
                            }
                            //当目录下的文件全删除后，删除当前目录
                            files[i].delete();
                        }
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 检测要上传的文件类型及大小
     *
     * @param multipartFile 要上传的文件
     * @param checkType     只能上传文件的类型，请参考本类静态变量如：UploadUtil.CSS_TYPE
     * @param maxFileSize   文件的限制大小，请使用字节。
     * @return 错误描述，如果为空，则文件符合上传类型及大小
     * @throws Exception 异常
     */
    public static String checkFileExt_SIZE(MultipartFile multipartFile, byte checkType, Long maxFileSize) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            String fileName = multipartFile.getOriginalFilename();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            ext = ext.toLowerCase();
            Long fileSize = multipartFile.getSize();                        //文件大小
            if (fileSize > maxFileSize) {
                sb.append("上传的文件大小为:");
                sb.append(fileSize / 1024);
                sb.append("K,最大不能超过：");
                sb.append(maxFileSize / 1024);
                sb.append("K");
            }
            if (UploadUtil.CSS_TYPE == checkType) {
                if (!ext.equals(UploadUtil.CSS_EXT)) {
                    sb.append("上传的文件并非CSS文件！");
                }

            } else if (UploadUtil.IMG_TYPE == checkType) {
                if (!UploadUtil.IMG_EXT.contains(ext)) {
                    sb.append("上传的文件并非图片格式！");
                }
            } else if (UploadUtil.SWF_TYPE == checkType) {
                if (!UploadUtil.SWF_EXT.equals(ext)) {
                    sb.append("上传的文件并非SWF格式！");
                }
            } else if (UploadUtil.SWF_IMG_TYPE == checkType) {
                if (!UploadUtil.SWF_EXT.equals(ext) && !UploadUtil.IMG_EXT.contains(ext)) {
                    sb.append("上传的文件并非SWF格式,或图格式！");
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        //文件删除测试
        /* String filePath = "d:/temp/i/";
        try {
            if (UploadUtil.deleteFileFromDir(filePath)) {
                System.out.println("ok");
            } else {
                System.out.println("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        String filePath = "d:/temp/i/";
        String[] copyFiles = {"1.css", "2.css", "3.css", "4.css", "5.css"};
        try {
            UploadUtil.copyFileFromTemp(filePath, copyFiles);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}