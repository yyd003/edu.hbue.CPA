package edu.hbue.CPA.common;

import java.io.*;

import jcifs.smb.*;

public class UploadDownloadUtil  
{  
  
    /** 
     * 从共享目录拷贝文件到本地 
     * @param remoteUrl 共享目录上的文件路径 
     * @param localDir 本地目录 
     */  
    public boolean smbGet(String remoteUrl, String localDir)  
    {  
        InputStream in = null;  
        OutputStream out = null;  
        try  
        {  
            SmbFile remoteFile = new SmbFile(remoteUrl);  
            //这一句很重要  
            remoteFile.connect();  
            if (remoteFile == null)  
            {  
                System.out.println("共享文件不存在");  
                return false;  
            }  
            String fileName = remoteFile.getName();  
            File localFile = new File(localDir + File.separator + fileName);  
            in = new BufferedInputStream(new SmbFileInputStream(remoteFile));  
            out = new BufferedOutputStream(new FileOutputStream(localFile));  
            byte[] buffer = new byte[1024];  
            while (in.read(buffer) != -1)  
            {  
                out.write(buffer);  
                buffer = new byte[1024];  
            }  
        }  
        catch (Exception e)  
        {   	
            e.printStackTrace();  
            return false;
        }  
        finally  
        {  
            try  
            {  
                out.close();  
                in.close(); 
                return true;
            }  
            catch (IOException e)  
            {  
            	e.printStackTrace();
            	return false;  
            }  
        }  
    }  
  
    /** 
     * 从本地上传文件到共享目录 
     * @Version1.0 Sep 25, 2009 3:49:00 PM 
     * @param remoteUrl 共享文件目录 
     * @param localFilePath 本地文件绝对路径 
     */  
    public boolean smbPut(String remoteUrl, String localFilePath)  
    {  
        InputStream in = null;  
        OutputStream out = null;  
        try  
        {  
            File localFile = new File(localFilePath);  
  
            String fileName = localFile.getName();  
            SmbFile remoteFile = new SmbFile(remoteUrl + "/" + fileName);  
            in = new BufferedInputStream(new FileInputStream(localFile));  
            out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));  
            byte[] buffer = new byte[1024];  
            while (in.read(buffer) != -1)  
            {  
                out.write(buffer);  
                buffer = new byte[1024];  
            }  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();
            return false;
        }  
        finally  
        {  
            try  
            {  
                out.close();  
                in.close();
                return true;
            }  
            catch (IOException e)  
            {  
                e.printStackTrace();  
                return false;
            }  
        }  
    }  
  
    public boolean smbDelete(String remoteUrl)  
    {  
        InputStream in = null;  
        OutputStream out = null;  
        try  
        {  
            SmbFile remoteFile = new SmbFile(remoteUrl);  
            //这一句很重要  
            remoteFile.connect();  
            if (remoteFile == null)  
            {  
                System.out.println("共享文件不存在");  
                return false; 
            }  
//            String fileName = remoteFile.getName();
            remoteFile.delete();
        }  
        catch (Exception e)  
        {  
            e.printStackTrace(); 
            return false;
        }
        return true;
    }  
    public static boolean makeDirs(String filePath) {
    		String folderName = filePath;
    		if (folderName == null || folderName.isEmpty())	return false;
    			File folder = new File(folderName);
    	 return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();}
    
//    public static void main(String[] args)  
//    {  
//        UploadDownloadUtil test = new UploadDownloadUtil();  
//        // smb:域名;用户名:密码@目的IP/文件夹/文件名.xxx  
//        // test.smbGet("smb://szpcg;jiang.t:xxx@192.168.193.13/Jake/test.txt",  
//        // "c://") ;  
//          
////      test.smbPut("smb://yyd:138245Aa@192.168.128.1/CloudPlatformApp/",  
////              "D://Temp/CPA.txt");  
//          
//          
//        //用户名密码不能有强字符，也就是不能有特殊字符，否则会被作为分断处理  
////        test.smbGet("smb://yyd:138245Aa@192.168.128.1/CloudPlatformApp/CPA",  
////        "D://Temp/"); 
//        test.makeDirs("D://CPA/Single File/cmd/");
//        test.smbGet("smb://yyd:138245Aa@192.168.128.1/CloudPlatformApp/Program/Single File/cmd.exe", "D://CPA/Single File/cmd/");
////      test.smbDelete("smb://yyd:138245Aa@192.168.128.1/CloudPlatformApp/CPA.txt");
//    }  
  
}  

