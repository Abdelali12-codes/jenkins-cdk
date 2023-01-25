package com.myorg;
import com.myorg.conf;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
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
        // create groups
        
        for ( int i=0 ; i<groups.size(); i++){
            System.out.print(groups.get(i).get("groupname"));
            Group group = Group.Builder.create(this,"awsgroup"+i)
                                       .groupName((String)groups.get(i).get("groupname"))
                                       .build();
                                       
            group.addToPolicy(PolicyStatement.Builder.create()
                              .effect(Effect.ALLOW)
                              .actions((List<String>)groups.get(i).get("actions"))
                              .resources(List.of("*"))
                              .build());
                              
            List<String> users = (List<String>)groups.get(i).get("users");                
                              
            for (int j = 0; j < users.size(); j++){
                User.Builder.create(this, "user"+count)
                                        .userName(users.get(j))
                                        .groups(List.of(group))
                                        .build()
                                        ;
            }
            
            count++;
        }
    }
}
