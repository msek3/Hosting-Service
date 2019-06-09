package siwz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import siwz.model.AppFile;
import siwz.service.FileService;

import java.util.List;

@Controller
@RequestMapping("/downloads")
public class DownloadController {

    private FileService service;

    @Autowired
    public DownloadController(FileService service) {
        this.service = service;
    }

    @GetMapping
    public String downloads(){
        return "downloads";
    }

    @GetMapping("/files")
    public @ResponseBody List<AppFile> getTop10Files(){
        return service.getTop10Files();
    }

    @GetMapping("/files7days")
    public @ResponseBody List<Long> getFilesFrom7Days(){
        return service.getDownloadsFrom7Days();
    }
}