package com.feiran.zg.core.website.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import com.feiran.zg.core.base.domain.DoctorInfo;
import com.feiran.zg.core.base.domain.LoginInfo;
import com.feiran.zg.core.base.service.IDoctorInfoService;
import com.feiran.zg.core.base.utils.BidConst;
import com.feiran.zg.core.base.utils.UserContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传工具
 * 
 * @author Administrator
 * 
 */
@Component
public class UploadUtil {
//	@Autowired
//	private static IDoctorInfoService doctorInfoService;

	/**
	 * 处理文件上传
	 * 
	 * @param file
	 * @param basePath
	 *            存放文件的目录的绝对路径 servletContext.getRealPath("/upload")
	 * @return
	 */
	public static String upload(MultipartFile file, String basePath, IDoctorInfoService doctorInfoService) {
		// 获取从页面传递过来的文件的文件名
		String orgFileName = file.getOriginalFilename();
		// 拼接上传文件的名字
		String fileName = UUID.randomUUID().toString() + "."
				+ FilenameUtils.getExtension(orgFileName);
		try {
			File targetFile = new File(basePath, fileName);
			FileUtils.writeByteArrayToFile(targetFile, file.getBytes());
			
			//把文件同步到公共文件夹
			File publicFile=new File(BidConst.PUBLIC_IMG_SHARE_PATH,fileName);
            System.out.println(BidConst.PUBLIC_IMG_SHARE_PATH);
            FileUtils.writeByteArrayToFile(publicFile, file.getBytes());
            System.out.println(publicFile.getAbsolutePath());
//			fileName = publicFile.getAbsolutePath();
//			DoctorInfo currentDoctorInfo = doctorInfoService.getCurrent();
//			currentDoctorInfo.setDoctorImg(publicFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}
}
























