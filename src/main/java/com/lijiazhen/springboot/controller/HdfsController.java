package com.lijiazhen.springboot.controller;

import com.lijiazhen.springboot.service.HdfsService;
import com.lijiazhen.springboot.vo.HdfsFile;
import org.apache.hadoop.fs.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class HdfsController {

    @Autowired
    private HdfsService hdfsService;

    @RequestMapping("/lists")
    public String lists(ModelMap map){
        List<HdfsFile> lists= hdfsService.getDirectoryFromHdfs();
        map.put("lists",lists);
        return "fuc/lists";
    }

    @RequestMapping("/del")
    public String del(String name){
        hdfsService.deleteFromHdfs(name);
        return "redirect:/lists";
        //意思是先跳转到读取lists，即读取es文件目录下有什么文件
        // 这是重定向，先跳转到上面函数的lists，读取数据
    }

    @RequestMapping("/addForm")
    public String addForm(){
        return "fuc/add";
    }

    @RequestMapping(value = "/addFile",method= RequestMethod.POST)
    public String addFile(MultipartFile upfile) throws IOException {

        String originaFilename=upfile.getOriginalFilename();

        //创建file对象
        File file = new File("E:/upfiles/"+originaFilename);
        //上传
        upfile.transferTo(file);
        hdfsService.copyFile(file.getAbsolutePath());
        return "redirect:/lists";
    }

    @RequestMapping(value = "download")
    public void download(String name, HttpSession session, HttpServletResponse response) throws IOException {
        //name表示是要下载的文件的名字
        File file=new File(name);
        response.setContentType("txt;charset=UTF-8");//下载文本文档
        response.setHeader("Content-Disposition","attachment; filename="+file.getName()+"");
        //将当前获取的名字传入HdfsService中的download函数中，获取准确的hdfs文件的路径
        InputStream inputStream=hdfsService.download(name);
        int len=0;
        byte[] bytes=new byte[1024];
        ServletOutputStream outputStream=response.getOutputStream();
        while ((len=inputStream.read(bytes))!=-1){
            outputStream.write(bytes,0,len);
        }
        inputStream.close();
    }

    @RequestMapping(value = "/rename",method= RequestMethod.POST)
    public String rename(String name1,String name2){//第一个为原名，第二个为新名
        System.out.println("name1:"+name1);
        System.out.println("name2:"+name2);
        String[] a=name2.split(",");
        System.out.println(a[1]);
        hdfsService.rename(name1,a[1]);
        return "redirect:lists";
    }

}
