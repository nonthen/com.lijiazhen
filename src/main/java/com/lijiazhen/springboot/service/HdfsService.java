package com.lijiazhen.springboot.service;

import com.lijiazhen.springboot.vo.HdfsFile;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class HdfsService {

    private String hdfsPath = "hdfs://192.168.174.137:9000/es/";

//    public static void main(String[] args) throws IOException {
//        HdfsService hd = new HdfsService();
////        hd.copyFile("E:/friends2.txt");
//
////        hd.deleteFromHdfs(hd.hdfsPath+"friends2.txt");
////        hd.createdir("testfiles.txt");
////
//        InputStream ism = hd.getFileInputStreamForPath("hdfs://192.168.174.137:9000/es/friends.txt");
//        int len = 0;
//        byte[] buf = new byte[1024];
//        FileOutputStream fos = new FileOutputStream("E:/friends.txt");
//
//        while((len=ism.read(buf))!=-1){
//            fos.write(buf,0,len);
//        }
//        fos.close();
//    }

    /**
     * 上传文件
     * @param local 本地路径
     * @throws IOException
     */
    public void copyFile(String local) throws IOException {
        //指定当前的Hadoop的用户
        System.setProperty("HADOOP_USER_NAME", "root");

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(hdfsPath),conf);
        //remote---/用户/用户下的文件或文件夹
        fs.copyFromLocalFile(new Path(local), new Path(hdfsPath));
        System.out.println("copy from: " + local + " to " + hdfsPath);
        fs.close();
    }

    /**
     * 删除hdfs中的文件
     * @param deletePath  hdfs文件的绝对路径
     */
    public  void deleteFromHdfs(String deletePath) {
        deletePath=hdfsPath+deletePath;
        //指定当前的Hadoop的用户
        System.setProperty("HADOOP_USER_NAME", "root");
        Configuration conf = new Configuration();

        try{

            FileSystem fs = FileSystem.get(URI.create(deletePath), conf);
            fs.deleteOnExit(new Path(deletePath));
            fs.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }



    /**
     * 创建新目录
     * @param dirpath  要创建的文件夹的名称
     */
    public void createdir(String dirpath){
        try {
            //指定当前的Hadoop的用户
            System.setProperty("HADOOP_USER_NAME", "root");

            String dirname= hdfsPath+dirpath;

            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(dirname), conf);
            Path f=new Path(dirname);

            if (!fs.exists(new Path(dirname))) {
                //创建文件夹
                fs.mkdirs(f);
                System.out.println("创建成功！");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 创建新文件
     * @param filePath  要创建的文件名称
     */
    public void createFile(String filePath){
        try {
//            指定当前的Hadoop的用户
            System.setProperty("HADOOP_USER_NAME", "root");

            String dirname= hdfsPath+filePath;

            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(dirname), conf);
            Path f=new Path(dirname);

            if (!fs.exists(new Path(dirname))) {
                //创建文件
                fs.create(f);
                System.out.println("创建成功！");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 获取文件输入流
     * @param strpath 文件的hdfs绝对路径
     * @return
     * @throws IOException
     */
    public InputStream getFileInputStreamForPath(String strpath) throws IOException{
        //指定当前的Hadoop的用户
        System.setProperty("HADOOP_USER_NAME", "root");
        Configuration conf = new Configuration();
        conf.set("fs.default.name", strpath);
        FileSystem fs = FileSystem.get(conf);
        return fs.open(new Path(strpath));
    }



    /**遍历HDFS上的文件和目录*/
    public  List<HdfsFile> getDirectoryFromHdfs()  {
        //指定当前的Hadoop的用户
        System.setProperty("HADOOP_USER_NAME", "root");
        String dst = hdfsPath;
        Configuration conf = new Configuration();
        List<HdfsFile> list = new ArrayList<>();
        try{
            FileSystem fs = FileSystem.get(URI.create(dst), conf);
            FileStatus[] fileStatuses=fs.listStatus(new Path(dst));
            if(list != null)
                for (FileStatus f : fileStatuses) {
                    System.out.printf("name: %s, folder: %s, size: %d\n", f.getPath().getName(), f.isDir(), f.getLen());
                    HdfsFile hf =new HdfsFile(f.getPath().getName(),f.isDir(),f.getLen());
                    list.add(hf);
                }
            fs.close();
        }catch(Exception ex){

        }
        return  list;
    }

    /**遍历HDFS上的文件和目录*/
    public FileStatus[] getDirectoryFromHdfs(String path) {

        //指定当前的Hadoop的用户
        System.setProperty("HADOOP_USER_NAME", "root");
        Configuration conf = new Configuration();
        String dst = hdfsPath;
        FileStatus[] list=null;
        try {

            if (path.length() > 0) {
                dst = path;
            }
            FileSystem fs = FileSystem.get(URI.create(dst), conf);
            list = fs.listStatus(new Path(dst));
            if (list != null)
                for (FileStatus f : list) {
                    System.out.printf("name: %s, folder: %s, size: %d\n", f.getPath().getName(), f.isDir(), f.getLen());

                }
            fs.close();

        } catch (Exception ex) {

        }
        return list;
    }

}
