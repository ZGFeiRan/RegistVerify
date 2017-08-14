package com.feiran.zg.core.mgrsite.web.filter;


import com.feiran.zg.core.base.utils.BidConst;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 图片请求拦截器
 * Created by zhangguangfei on 2017/2/14.
 */
public class SysncImageFilter implements Filter {
    private ServletContext sc;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.sc = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String sourcePath = request.getRequestURI();
        File sourceFile = new File(this.sc.getRealPath(sourcePath));
        if (sourceFile.exists()){
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            String sourceName = FilenameUtils.getName(sourcePath);
            File publicFile = new File(BidConst.PUBLIC_IMG_SHARE_PATH, sourceName);
            if (publicFile.exists()){
                FileUtils.copyFile(publicFile,sourceFile);
//                filterChain.doFilter(servletRequest,servletResponse);
                resp.setHeader("Cache-Control", "no-store");
                resp.setHeader("Pragma", "no-cache");
                resp.setDateHeader("Expires", 0);
                ServletOutputStream outputStream = resp.getOutputStream();
                outputStream.write(FileUtils.readFileToByteArray(publicFile));
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    @Override
    public void destroy() {

    }
}
