package com.myorg;
import java.util.*;

public class conf {
    
    public String s3_bucket      = "s3-bucket-java-cdk";
    public String role_name       = "iam-cdk-java-role";
    public HashMap<String,Object> iam_group1 = new HashMap<String,Object>();
    public HashMap<String,Object> iam_group2 = new HashMap<String,Object>();
    public HashMap<String,Object> iam_group3 = new HashMap<String,Object>();
    public HashMap<String,Object> iam_group4 = new HashMap<String,Object>();
    public HashMap<String,Object> iam_group5 = new HashMap<String,Object>();
    public ArrayList<HashMap<String,Object>> groups = new ArrayList<HashMap<String,Object>>();
    
    
    public ArrayList<HashMap<String,Object>> groupsList(){
        
        // group1
        this.iam_group1.put("groupname", "group1");
        this.iam_group1.put("users",List.of("user1"));
        this.iam_group1.put("actions", List.of("s3:*","sagemaker:*","ec2:*"));
        
        // group2
        this.iam_group2.put("groupname","group2");
        this.iam_group2.put("users", List.of("user2"));
        this.iam_group2.put("actions", List.of("iam:*","eks:*"));
       
        
        this.groups.add(this.iam_group1);
        this.groups.add(this.iam_group2);
        return this.groups;
    }
    
    
}
