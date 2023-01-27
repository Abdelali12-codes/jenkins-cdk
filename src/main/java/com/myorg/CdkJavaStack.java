package com.myorg;
import com.myorg.conf;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.*;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.s3.*;
import software.amazon.awscdk.services.sagemaker.*;
import software.amazon.awscdk.services.iam.*;
import java.util.*;

// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;

public class CdkJavaStack extends Stack {
    public CdkJavaStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public CdkJavaStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);
        
        conf cf = new conf();
        int count = 0;
        ArrayList<HashMap<String,Object>> groups = cf.groupsList();
        ArrayList<CfnGroup> cfngroups  = new ArrayList<CfnGroup>();
        ArrayList<CfnUser> cfnusers  = new ArrayList<CfnUser>();
        
        //System.out.print(groups);
        /*
        Bucket bucket = Bucket.Builder.create(this, "S3Bucket")
                                      .bucketName(cf.s3_bucket)
                                      .build();
        
        Role role = Role.Builder.create(this, "iamRole")
                    .roleName(cf.role_name)
                    .assumedBy(new ServicePrincipal("sagemaker.amazonaws.com"))
                    .build();
                    
        role.addToPolicy(PolicyStatement.Builder.create()
                         .effect(Effect.ALLOW)
                         .actions(List.of("s3:*"))
                         .resources(List.of(bucket.getBucketArn(),bucket.getBucketArn()+"/*"))
                         .build()
        );
        */
        
        // create groups
        for (int i = 0 ;  i < groups.size() ; i++){
            
            String groupname = (String)groups.get(i).get("groupname");
            CfnGroup cfngroup = CfnGroup.Builder.create(this,groupname)
                                           .groupName(groupname)
                                           .build();
                                           
            List<String> actions =  (List<String>)groups.get(i).get("actions");    
            
            PolicyDocument  policy = PolicyDocument.Builder.create()
                                                 .statements(List.of(PolicyStatement.Builder.create()
                                                 .actions(actions)
                                                 .resources(List.of("*"))
                                                 .build()))
                                                 .build();
                                     
            CfnGroup.PolicyProperty policyProperty = CfnGroup.PolicyProperty.builder()
                                 .policyDocument(policy)
                                 .policyName("policyName"+groupname)
                                 .build();
                                 
            HashMap<String, Object> item = groups.get(i);
            
            if(item.containsKey("managedpolicies")){
                List<String> managedpolicies = (List<String>)item.get("managedpolicies");
                cfngroup.setManagedPolicyArns(managedpolicies);
            }
                                           
            cfngroup.setPolicies(List.of(policyProperty));
            cfngroups.add(cfngroup);           
            
        }
        
        // create users
        List<String> users  = cf.users;
        for (int j = 0 ; j < users.size() ; j++){
            String user = users.get(j);
            CfnUser cfnuser = CfnUser.Builder.create(this, user)
                                                .userName(user)
                                                .build();
                                                
            cfnusers.add(cfnuser);
            
            count++;
        }
        
        // attach users and groups
        for(int g = 0 ; g < groups.size() ; g++){
            
            String groupname = (String)groups.get(g).get("groupname");
            List<String> usersgroup = (List<String>)groups.get(g).get("users");
            
            
            CfnUserToGroupAddition cfnUserToGroupAddition = CfnUserToGroupAddition.Builder.create(this, "MyCfnUserToGroupAddition"+groupname)
                                                                                         .groupName(groupname)
                                                                                         .users(usersgroup)
                                                                                      .build();
            cfnUserToGroupAddition.addDependsOn(cfngroups.get(g));
            
            for (int u = 0 ; u < usersgroup.size() ; u++){
                cfnUserToGroupAddition.addDependsOn(cfnusers.get(u));
            }
            
            
        }
        
        
    }
}
