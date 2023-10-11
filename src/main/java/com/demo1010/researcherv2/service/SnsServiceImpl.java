package com.demo1010.researcherv2.service;


import com.amazonaws.services.sns.AmazonSNS;
import com.demo1010.researcherv2.config.AwsSnsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class SnsServiceImpl implements SnsService{


    private final AwsSnsConfig awsSnsConfig;

    private final AmazonSNS amazonSNS;

    @Autowired
    public SnsServiceImpl(AwsSnsConfig awsSnsConfig) {
        this.awsSnsConfig = new AwsSnsConfig();
        this.amazonSNS = this.awsSnsConfig.amazonSNS();
    }

//    @Override
//    public AmazonSNS amazonSNS() {
//        return AmazonSNSClientBuilder.standard()
//                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
//                .withRegion(Regions.AP_NORTHEAST_2)
//                .build();
//    }


//    public String createTopic(String topicName) {
//        AmazonSNS snsClient = amazonSNS();
//        CreateTopicRequest createTopicRequest = new CreateTopicRequest(topicName);
//        CreateTopicResult createTopicResult = snsClient.createTopic(createTopicRequest);
//        return createTopicResult.getTopicArn();
//    }
}
