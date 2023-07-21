package com.xz.datacenter.datacollector.pdf.vo;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: houyong
 * @Date: 2020/2/21
 */
@Data
public class PDFSessionDetail {

    boolean isHold;

    boolean isActive;

    SessionFactory sessionFactory;

    Launcher launcher;

    String browserContext;

    Session session;

    Date lastHoldDate;

    int holdTimes=0;

}
