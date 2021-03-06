package com.tulip.springbootelasticsearchdemo.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class ElasticsearchConfig {

    @Value("${elasticsearch.cluster.name}")
    private String clusterName;

    @Value("${elasticsearch.host}")
    private String host;


    @Bean
    public TransportClient transportClient() throws UnknownHostException{
        //set  cluster name
        Settings settings = Settings.builder().put("cluster.name",clusterName)
                .build();
        TransportClient transportClient2 = new PreBuiltTransportClient(settings);
        //set nodes
        String[] nodes = host.split(",");
        for (String node:nodes
             ) {
            if(node.length()>0){
                String[] hostPort = node.split(":");
                transportClient2.addTransportAddress(
                        new InetSocketTransportAddress(
                                InetAddress.getByName(hostPort[0]),
                                Integer.parseInt(hostPort[1])
                        )
                );
            }
        }
        return transportClient2;
    }
}
