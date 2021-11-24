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
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class HdfsController {

    @Autowired
    private HdfsService hdfsService;

    @RequestMapping("/lists")
    public String lists(ModelMap map){
        List<HdfsFile> lists= hdfsService.getDirectoryFromHdfs();
        map.put("lists",lists);
        return "lists";
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
        return "add";
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



}
