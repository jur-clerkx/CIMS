/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Situational_Awareness;
import java.util.ArrayList;
import java.util.List;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import Situational_Awareness.Domain.Information;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author rick
 */
public class TwitterSearch {
    
    String tweetString = "";
    Twitter twitter;
    
    public TwitterSearch(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
        .setOAuthConsumerKey("XQj7G52yevzl0pBkySy3UzvzR")
        .setOAuthConsumerSecret("yfsTeuPGbbX6pSvIiiOazx2E9vkuoCm1qBYT7Jvek9I5C9BPr3")
        .setOAuthAccessToken("4713593925-gOovKDZzZ5bmk6hX8CIqwmosVx6EcKQ4uxqoeVD")
        .setOAuthAccessTokenSecret("43YMSb8LRb6O7Fk0rS5vLSEzGXJ0WUCcPknpG0erSfoLb");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }
    
    
    public ArrayList<Information> twitterFeed(String twitterURL){
        ArrayList<Information> informationList = new ArrayList<>();

        try {
            Query query = new Query(twitterURL);
            QueryResult result;
            result = twitter.search(query);
            List<Status> tweets = result.getTweets();

            for (Status tweet : tweets) {
                System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                
                Date tweeted = tweet.getCreatedAt();
                
                if(tweeted.getTime() >= java.lang.System.currentTimeMillis() - 36000000){
                Information newInfo = new Information(null,tweet.getUser().getScreenName(),tweet.getText(),tweet.getUser().getLocation(), 0, 0, 0, 0, tweet.getUser().getProfileImageURL(), null);
                informationList.add(newInfo);
                }
                else{
                    System.out.println("information not relevant");
                }
                
                
            }
        }
        catch (Exception te) {
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
        return informationList;
    }
}

