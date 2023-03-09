package com.andr3a.giacomini.sbproject.controller;

import com.andr3a.giacomini.sbproject.model.entity.SbUser;
import com.andr3a.giacomini.sbproject.exporter.SbUserExcelExporter;
import com.andr3a.giacomini.sbproject.service.SbUserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    private SbUserService sbUserService;

    @Autowired
    private Logger log;

    @GetMapping("users/export/excel")
    public void exportUsersAndDownloadExcelFile(HttpServletResponse response) throws IOException {

        log.trace("START exportUsersAndDownloadExcelFile()");

        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<SbUser> sbUserList = (List<SbUser>) sbUserService.getAllUsers();
        log.info("sbUserList element: " + sbUserList.size());

        SbUserExcelExporter sbUserExcelExporter = new SbUserExcelExporter(sbUserList);

        sbUserExcelExporter.export(response);

        log.trace("END exportUsersAndDownloadExcelFile()");
    }
}