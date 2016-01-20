/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Situational_Awareness;
import java.util.ArrayList;
import java.util.List;
import java.lang.System.*;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
/**
 *
 * @author rick
 */
public class TwitterSearch {
    
    String tweetString = "";
    Twitter twitter;
    
    public TwitterSearch(){
        //autogenned keys
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
            //make query
            Query query = new Query(twitterURL);
            QueryResult result;
            result = twitter.search(query);
            List<Status> tweets = result.getTweets();
            //loop tweets to create information
            for (Status tweet : tweets) {
                System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());               
                if(tweet.getCreatedAt().getTime() >= System.currentTimeMillis() - 3600000) {
                    Information newInfo = new Information(1,null,tweet.getUser().getScreenName(),tweet.getText(),tweet.getUser().getLocation(), 0, 0, 0, 0, tweet.getUser().getOriginalProfileImageURL(), null, false);
                    //add info
                    informationList.add(newInfo);
                }
            }
        }
        catch (Exception te) {
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
        return informationList;
    }
}
