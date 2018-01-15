package com.kute.util.zip;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;


/**
 * ѹ��������
 * @author bl
 *
 */
public class ZipUtil {
    
    /** 
     * ִ��ѹ������ 
     * @param srcPathName ��Ҫ��ѹ�����ļ�/�ļ��� 
     */  
    public static void zip(String srcPathName, String targetZipPath) { 
        
        File zipFile = new File(targetZipPath);
        
        File srcdir = new File(srcPathName);    
        if (!srcdir.exists()){  
            throw new RuntimeException(srcPathName + "�����ڣ�");    
        }   
            
        Project prj = new Project();    
        Zip zip = new Zip();    
        zip.setProject(prj);    
        zip.setDestFile(zipFile);    
        FileSet fileSet = new FileSet();    
        fileSet.setProject(prj);    
        fileSet.setDir(srcdir);    
        //fileSet.setIncludes("**/*.java"); //������Щ�ļ����ļ��� eg:zip.setIncludes("*.java");    
        //fileSet.setExcludes(...); //�ų���Щ�ļ����ļ���    
        zip.addFileset(fileSet);    
        zip.execute();   
    }
    
    /**
     * ɾ���ļ���
     * @param srcPathName
     */
    public static void delete(String srcPathName) {
        File file = new File(srcPathName);
        if(!file.exists()) {
            return;
        }
        Delete delete = new Delete();
        Project project = new Project();
        project.init();
        delete.setProject(project);
        delete.setTaskName("delete");
        delete.setTaskType("delete");
        delete.setDir(file);
        delete.execute();
    }
    
}
